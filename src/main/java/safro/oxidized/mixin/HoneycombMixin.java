package safro.oxidized.mixin;


import net.minecraft.block.BlockState;
import net.minecraft.item.HoneycombItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import safro.oxidized.util.CopperStateMap;

import java.util.Optional;

@Mixin(HoneycombItem.class)
public class HoneycombMixin {
    @Inject(method = "getWaxedState", at = @At("HEAD"), cancellable = true)
    private static void oxidized$customWaxedBlocks(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
        var block = CopperStateMap.getWaxed(state.getBlock());
        if (block.isPresent()) {
            cir.setReturnValue(block.map(b -> b.getStateWithProperties(state)));
        }
    }
}
