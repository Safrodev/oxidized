package safro.oxidized.mixin;


import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.oxidized.block.CopperRailBlock;

@Mixin(AbstractMinecartEntity.class)
public abstract class MinecartRailMixin {

    @SuppressWarnings("ConstantConditions")
    @Inject(at = @At("HEAD"), method = "moveOnRail", cancellable = true)
    protected void moveOnRail(BlockPos pos, BlockState state, CallbackInfo info) {
        AbstractRailBlock railBlock = (AbstractRailBlock) state.getBlock();
        if (railBlock instanceof CopperRailBlock) {
            ((CopperRailBlock) railBlock).onMoveOnRail(pos, state, (AbstractMinecartEntity) (Object) this, info);
        }
    }
}
