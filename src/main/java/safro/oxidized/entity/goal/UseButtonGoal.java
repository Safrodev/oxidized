package safro.oxidized.entity.goal;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import safro.oxidized.Oxidized;
import safro.oxidized.block.CopperButtonBlock;
import safro.oxidized.entity.CopperGolemEntity;
import safro.oxidized.registry.BlockRegistry;

public class UseButtonGoal extends MoveToTargetPosGoal {
    private final CopperGolemEntity golem;
    private BlockState button;
    private BlockPos buttonPos;

    public UseButtonGoal(CopperGolemEntity entity, double speed) {
        super(entity, speed, 8);
        this.golem = entity;
    }

    public boolean canStart() {
        return super.canStart();
    }

    public void start() {
        super.start();
    }

    public void stop() {
        super.stop();
        this.golem.setPressingButtons(false);
        this.button = null;
        this.buttonPos = null;
    }

    public void tick() {
        if (this.hasReached()) {
        //    if (this.hasButton(targetPos) && button != null && buttonPos != null) {
                this.golem.setPressingButtons(true);
                if (MathHelper.nextInt(golem.getRandom(), 1, Oxidized.CONFIG.copper_golem_button_chance) == 2) {
                    BlockState state = this.golem.getWorld().getBlockState(this.targetPos);
                    ((CopperButtonBlock) state.getBlock()).onUse(state, this.mob.getWorld(), this.targetPos, null, null, null);
                }
        //    }
        }
        super.tick();
    }

    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.isOf(BlockRegistry.COPPER_BUTTON);
    }

    protected BlockPos getTargetPos() {
        return this.targetPos;
    }

    private BlockPos getStandablePos(BlockPos pos) {
        World world = this.golem.getWorld();
        if (isStandable(world, pos.west())) {
            return pos.west();
        } else if (isStandable(world, pos.north())) {
            return pos.north();
        } else if (isStandable(world, pos.south())) {
            return pos.south();
        } else if (isStandable(world, pos.up())) {
            return pos.up();
        } else if (isStandable(world, pos.down())) {
            return pos.down();
        } else {
            return pos.east();
        }
    }

    private boolean isStandable(World world, BlockPos pos) {
        return world.isAir(pos) && world.getBlockState(pos.down()).hasSolidTopSurface(world, pos, this.golem);
    }

    private boolean hasButton(BlockPos pos) {
        if (this.golem.getWorld().getBlockState(pos).isOf(BlockRegistry.COPPER_BUTTON)) {
            button = this.golem.getWorld().getBlockState(pos);
            buttonPos = pos;
            return true;
        } else if (this.golem.getWorld().getBlockState(pos.down()).isOf(BlockRegistry.COPPER_BUTTON)) {
            button = this.golem.getWorld().getBlockState(pos.down());
            buttonPos = pos.down();
            return true;
        } else if (this.golem.getWorld().getBlockState(pos.up()).isOf(BlockRegistry.COPPER_BUTTON)) {
            button = this.golem.getWorld().getBlockState(pos.up());
            buttonPos = pos.up();
            return true;
        } else if (this.golem.getWorld().getBlockState(pos.east()).isOf(BlockRegistry.COPPER_BUTTON)) {
            button = this.golem.getWorld().getBlockState(pos.east());
            buttonPos = pos.east();
            return true;
        } else if (this.golem.getWorld().getBlockState(pos.west()).isOf(BlockRegistry.COPPER_BUTTON)) {
            button = this.golem.getWorld().getBlockState(pos.west());
            buttonPos = pos.west();
            return true;
        } else if (this.golem.getWorld().getBlockState(pos.north()).isOf(BlockRegistry.COPPER_BUTTON)) {
            button = this.golem.getWorld().getBlockState(pos.north());
            buttonPos = pos.north();
            return true;
        } else if (this.golem.getWorld().getBlockState(pos.south()).isOf(BlockRegistry.COPPER_BUTTON)) {
            button = this.golem.getWorld().getBlockState(pos.south());
            buttonPos = pos.south();
            return true;
        } else
            button = null;
            buttonPos = null;
            return false;
    }

    public double getDesiredDistanceToTarget() {
        return 0.75D;
    }
}
