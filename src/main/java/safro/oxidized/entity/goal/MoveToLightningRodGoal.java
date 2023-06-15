package safro.oxidized.entity.goal;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import safro.oxidized.entity.CopperGolemEntity;

public class MoveToLightningRodGoal extends MoveToTargetPosGoal {
    private final CopperGolemEntity golem;

    public MoveToLightningRodGoal(CopperGolemEntity entity, double speed) {
        super(entity, speed, 10);
        this.golem = entity;
    }

    public boolean canStart() {
        return this.golem.getWorld().isThundering() && super.canStart();
    }

    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.isOf(Blocks.LIGHTNING_ROD);
    }
}
