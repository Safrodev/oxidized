package safro.oxidized.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.oxidized.Oxidized;
import safro.oxidized.entity.CopperGolemEntity;
import safro.oxidized.registry.EntityRegistry;

import java.util.Iterator;

@Mixin(LightningRodBlock.class)
public class LightningRodBlockMixin {

    @Inject(method = "onBlockAdded", at = @At("TAIL"))
    private void checkForGolem(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (!oldState.isOf(state.getBlock())) {
            if (Oxidized.CONFIG.enable_copper_golem) this.trySpawnEntity(world, pos);
        }
    }

    private void trySpawnEntity(World world, BlockPos pos) {
        Iterator var6;
        ServerPlayerEntity serverPlayerEntity2;
        if (world.getBlockState(pos.down().down()).isOf(Blocks.COPPER_BLOCK) || world.getBlockState(pos.down().down()).isOf(Blocks.CUT_COPPER) && Oxidized.CONFIG.enable_copper_golem) {
            if (world.getBlockState(pos.down()).isOf(Blocks.CARVED_PUMPKIN)) {
                CopperGolemEntity copperGolemEntity = (CopperGolemEntity) EntityRegistry.COPPER_GOLEM.create(world);
                copperGolemEntity.refreshPositionAndAngles((double) pos.getX() + 0.5D, (double) pos.getY() + 0.05D, (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
                update(world, pos);
                world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.COPPER_BLOCK.getDefaultState()), pos.getX() + ((double) world.random.nextFloat() - 0.5D) * (double) 1.4F, pos.getY() + 0.1D, pos.getZ() + ((double) world.random.nextFloat() - 0.5D) * (double) 1.4F, 4.0D * ((double) world.random.nextFloat() - 0.5D), 0.5D, ((double) world.random.nextFloat() - 0.5D) * 4.0D);
                world.spawnEntity(copperGolemEntity);
                var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, copperGolemEntity.getBoundingBox().expand(5.0D)).iterator();

                while (var6.hasNext()) {
                    serverPlayerEntity2 = (ServerPlayerEntity) var6.next();
                    Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity2, copperGolemEntity);
                }
            }
        }
    }

    private void update(World world, BlockPos pos) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
        world.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
        world.setBlockState(pos.down().down(), Blocks.AIR.getDefaultState());
        world.updateNeighbors(pos, Blocks.AIR);
        world.updateNeighbors(pos.down(), Blocks.AIR);
        world.updateNeighbors(pos.down().down(), Blocks.AIR);
    }
}
