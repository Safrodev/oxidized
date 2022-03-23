package safro.oxidized.util;

import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.util.math.BlockPos;
import safro.oxidized.mixin.MinecartEntityAccessor;

public class MinecartHandler {

    public static void handleBlockHit(BlockPos pos, AbstractMinecartEntity minecart, RailShape railShape) {
        double x = minecart.getVelocity().x;
        double z = minecart.getVelocity().z;
        if (railShape == RailShape.EAST_WEST) {
            if (((MinecartEntityAccessor) minecart).invokeWillHitBlockAt(pos.west())) {
                x = 0.02D;
            } else if (((MinecartEntityAccessor) minecart).invokeWillHitBlockAt(pos.east())) {
                x = -0.02D;
            }
        } else if (railShape == RailShape.NORTH_SOUTH) {
            if (((MinecartEntityAccessor) minecart).invokeWillHitBlockAt(pos.north())) {
                z = 0.02D;
            } else if (((MinecartEntityAccessor) minecart).invokeWillHitBlockAt(pos.south())) {
                z = -0.02D;
            }
        }

        minecart.setVelocity(x, minecart.getVelocity().y, z);
    }
}
