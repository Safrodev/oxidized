package safro.oxidized.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.oxidized.block.CopperTrapBlock;
import safro.oxidized.registry.BlockRegistry;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void trapEntity(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.getBlockStateAtPos().isOf(BlockRegistry.COPPER_TRAP)) {
            if (CopperTrapBlock.isClosed(entity.getBlockStateAtPos())) {
                Vec3d v = entity.getPos();
                entity.teleport(v.getX(), v.getY(), v.getZ(), false);
            }
        }
    }
}
