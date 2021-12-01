package safro.oxidized.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import safro.oxidized.util.CopperStateMap;

import java.util.Optional;
import java.util.Random;

public class CustomOxidizableBlock extends Block implements Oxidizable {

    private final OxidationLevel oxidizationLevel;

    public CustomOxidizableBlock(OxidationLevel oxidizationLevel, Settings settings) {
        super(settings);
        this.oxidizationLevel = oxidizationLevel;
    }

    @Override
    public Optional<BlockState> getDegradationResult(BlockState state) {
        return CopperStateMap.getIncrease(state.getBlock()).map((block) -> block.getStateWithProperties(state));
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    public boolean hasRandomTicks(BlockState state) {
        return CopperStateMap.getIncrease(state.getBlock()).isPresent();
    }

    @Override
    public OxidationLevel getDegradationLevel() {
        return this.oxidizationLevel;
    }
}
