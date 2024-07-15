package safro.oxidized.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import safro.oxidized.block.entity.CopperKilnBlockEntity;
import safro.oxidized.registry.BlockRegistry;

public class CopperKilnBlock extends AbstractFurnaceBlock {
    public static final MapCodec<CopperKilnBlock> CODEC = createCodec(CopperKilnBlock::new);

    public CopperKilnBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends CopperKilnBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CopperKilnBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(world, type, BlockRegistry.COPPER_KILN_BLOCK_ENTITY);
    }

    @Override
    public void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CopperKilnBlockEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory)blockEntity);
            player.incrementStat(Stats.INTERACT_WITH_FURNACE);
        }

    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            double d = (double)pos.getX() + 0.5D;
            double e = pos.getY();
            double f = (double)pos.getZ() + 0.5D;
            if (random.nextDouble() < 0.1D) {
                world.playSound(d, e, f, SoundEvents.BLOCK_SMOKER_SMOKE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            world.addParticle(ParticleTypes.SMOKE, d, e + 1.1D, f, 0.0D, 0.0D, 0.0D);
        }
    }
}
