package safro.oxidized.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
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
        if(!world.isClient() && isActive(stack)) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            Box box = player.getBoundingBox().expand(Oxidized.CONFIG.pulsar_reach);

            List<ItemEntity> entityItems = player.getWorld().getEntitiesByClass(ItemEntity.class, box, EntityPredicates.VALID_ENTITY);
            for (ItemEntity item : entityItems) {
                item.onPlayerCollision(player);
            }

            List<ExperienceOrbEntity> entityXP = player.getWorld().getEntitiesByClass(ExperienceOrbEntity.class, box, EntityPredicates.VALID_ENTITY);
            for (ExperienceOrbEntity xp : entityXP) {
                xp.onPlayerCollision(player);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack pulsar = player.getStackInHand(hand);

        if (!world.isClient() && !player.isSneaking()) {
            this.toggle(pulsar);
            String status = this.isActive(pulsar) ? "Active" : "Inactive";
            player.sendMessage(Text.of("§6Pulsar is now: " + status), false);
        }
        return TypedActionResult.success(pulsar, world.isClient());
    }

    public boolean isActive(ItemStack stack) {
        NbtComponent data = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (data != null) {
            NbtCompound tag = data.copyNbt();
            if (tag.contains("Powered")) {
                return tag.getBoolean("Powered");
            }
        }
        return true;
    }

    public void toggle(ItemStack stack) {
        boolean active = isActive(stack);
        NbtCompound tag = new NbtCompound();
        tag.putBoolean("Powered", !active);
        NbtComponent.set(DataComponentTypes.CUSTOM_DATA, stack, tag);
    }
}
