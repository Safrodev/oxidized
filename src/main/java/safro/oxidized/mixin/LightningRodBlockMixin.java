package safro.oxidized.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.oxidized.Oxidized;
import safro.oxidized.entity.CopperGolemEntity;
import safro.oxidized.registry.EntityRegistry;

import javax.annotation.Nullable;
import java.util.Iterator;

@Mixin(LightningRodBlock.class)
public class LightningRodBlockMixin {
    @Unique
    @Nullable
    private BlockPattern copperGolemPattern = null;

    @Inject(method = "onBlockAdded", at = @At("TAIL"))
    private void checkForGolem(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (!oldState.isOf(state.getBlock())) {
            if (Oxidized.CONFIG.enable_copper_golem) this.trySpawnCopperGolem(world, pos);
        }
    }

    @Unique
    private void trySpawnCopperGolem(World world, BlockPos pos) {
        BlockPattern.Result result = this.getCopperGolemPattern().searchAround(world, pos);
        int i;
        Iterator var6;
        ServerPlayerEntity serverPlayerEntity;
        int j;
        if (result != null) {
            for (i = 0; i < this.getCopperGolemPattern().getHeight(); ++i) {
                CachedBlockPosition cachedBlockPosition = result.translate(0, i, 0);
                world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
                world.syncWorldEvent(2001, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
            }

            CopperGolemEntity golem = EntityRegistry.COPPER_GOLEM.create(world);
            BlockPos blockPos = result.translate(0, 2, 0).getBlockPos();
            golem.refreshPositionAndAngles((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D, 0.0F, 0.0F);
            world.spawnEntity(golem);
            var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, golem.getBoundingBox().expand(5.0D)).iterator();

            while (var6.hasNext()) {
                serverPlayerEntity = (ServerPlayerEntity) var6.next();
                Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, golem);
            }

            for (j = 0; j < this.getCopperGolemPattern().getHeight(); ++j) {
                CachedBlockPosition cachedBlockPosition2 = result.translate(0, j, 0);
                world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
            }
        }
    }

    @Unique
    private BlockPattern getCopperGolemPattern() {
        if (this.copperGolemPattern == null) {
            this.copperGolemPattern = BlockPatternBuilder.start().aisle("^", "$", "#").where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.LIGHTNING_ROD))).where('$', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.CARVED_PUMPKIN))).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.COPPER_BLOCK).or(BlockStatePredicate.forBlock(Blocks.CUT_COPPER)))).build();
        }
        return this.copperGolemPattern;
    }
}
