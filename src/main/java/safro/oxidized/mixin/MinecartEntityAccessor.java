package safro.oxidized.mixin;

import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractMinecartEntity.class)
public interface MinecartEntityAccessor {
    @Invoker("willHitBlockAt")
    boolean invokeWillHitBlockAt(BlockPos pos);

    @Invoker("getMaxOffRailSpeed")
    double invokeGetMaxOffRailSpeed();

}
