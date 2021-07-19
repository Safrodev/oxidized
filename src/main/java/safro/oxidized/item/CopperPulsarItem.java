package safro.oxidized.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class CopperPulsarItem extends Item {

    public CopperPulsarItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        if(!world.isClient)
        {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;

            List<ItemEntity> entityItems = player.getServerWorld().getEntitiesByClass(ItemEntity.class, player.getBoundingBox().expand(10), EntityPredicates.VALID_ENTITY);
            for (ItemEntity entityItemNearby : entityItems)
            {
                entityItemNearby.onPlayerCollision(player);
            }

            List<ExperienceOrbEntity> entityXP = player.getServerWorld().getEntitiesByClass(ExperienceOrbEntity.class, player.getBoundingBox().expand(8), EntityPredicates.VALID_ENTITY);
            for (ExperienceOrbEntity entityXPNearby : entityXP)
            {
                entityXPNearby.onPlayerCollision(player);
            }
        }
    }
}
