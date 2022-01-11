package safro.oxidized.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import safro.oxidized.Oxidized;

import java.util.List;

public class CopperPulsarItem extends Item {

    static final String POWERED = "Powered";

    public CopperPulsarItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if(!world.isClient && isActive(stack))
        {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;

            List<ItemEntity> entityItems = player.world.getEntitiesByClass(ItemEntity.class, player.getBoundingBox().expand(Oxidized.CONFIG.pulsar_reach), EntityPredicates.VALID_ENTITY);
            for (ItemEntity entityItemNearby : entityItems)
            {
                entityItemNearby.onPlayerCollision(player);
            }

            List<ExperienceOrbEntity> entityXP = player.world.getEntitiesByClass(ExperienceOrbEntity.class, player.getBoundingBox().expand(Oxidized.CONFIG.pulsar_reach), EntityPredicates.VALID_ENTITY);
            for (ExperienceOrbEntity entityXPNearby : entityXP)
            {
                entityXPNearby.onPlayerCollision(player);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
    {
        ItemStack pulsar = player.getStackInHand(hand);

        if (!world.isClient && !player.isSneaking()) {
            toggleMode(pulsar);
            player.sendMessage(new LiteralText("§6Pulsar is now: " + getMagnetMode(pulsar)), false);

        }

        return new TypedActionResult<>(ActionResult.SUCCESS, pulsar);
    }

    public boolean isActive(ItemStack pulsar)
    {
        return getMagnetMode(pulsar).getBoolean();
    }

    private void setMagnetMode(ItemStack pulsar, MagnetMode mode)
    {
        checkTag(pulsar);
        pulsar.getNbt().putBoolean(POWERED, mode.getBoolean());
    }

    private MagnetMode getMagnetMode(ItemStack pulsar)
    {
        if (!pulsar.isEmpty())
        {
            checkTag(pulsar);

            return pulsar.getNbt().getBoolean(POWERED) ? MagnetMode.ACTIVE : MagnetMode.INACTIVE;
        }
        return MagnetMode.INACTIVE;
    }

    public void toggleMode(ItemStack magnet)
    {
        MagnetMode currentMode = getMagnetMode(magnet);

        if (currentMode.getBoolean())
        {
            setMagnetMode(magnet, MagnetMode.INACTIVE);

            return;
        }

        setMagnetMode(magnet, MagnetMode.ACTIVE);
    }

    private void checkTag(ItemStack pulsar)
    {
        if (!pulsar.isEmpty())
        {
            if (!pulsar.hasNbt())
            {
                pulsar.setNbt(new NbtCompound());
            }
            NbtCompound nbt = pulsar.getNbt();

            if (!nbt.contains(POWERED))
            {
                nbt.putBoolean(POWERED, false);
            }
        }
    }

    public enum MagnetMode
    {
        ACTIVE(true), INACTIVE(false);

        final boolean state;

        MagnetMode(boolean state)
        {
            this.state = state;
        }

        public boolean getBoolean()
        {
            return state;
        }
    }

}
