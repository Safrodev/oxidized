package safro.oxidized.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.oxidized.mixin.MinecartEntityAccessor;
import safro.oxidized.util.MinecartHandler;

public class CopperRailBlock extends AbstractRailBlock {

    public static final EnumProperty<RailShape> SHAPE;
    public static final BooleanProperty POWERED;

    public CopperRailBlock(AbstractBlock.Settings settings) {
        super(true, settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(SHAPE, RailShape.NORTH_SOUTH)).with(POWERED, false)).with(WATERLOGGED, false));
    }

    public void onMoveOnRail(BlockPos pos, BlockState state, AbstractMinecartEntity minecart, CallbackInfo info) {

        if (!state.get(POWERED)) {
            double slowDownScaler = 0.25D;
            minecart.setVelocity(minecart.getVelocity().multiply(slowDownScaler, 1.0D, slowDownScaler));
            return;
        }

        RailShape railShape = state.get(getShapeProperty());
        Vec3d velocity = minecart.getVelocity();
        double speed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);

        if (speed > 0.01D) {
            minecart.setVelocity(velocity.normalize().multiply(((MinecartEntityAccessor) minecart).invokeGetMaxOffRailSpeed() * 10D));
        } else {
            MinecartHandler.HandleBlockHit(pos, minecart, railShape);
        }
    }

    public boolean isPoweredByOtherRails(World world, BlockPos pos, BlockState state, boolean bl, int distance) {
        if (distance >= 101) {
            return false;
        } else {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            boolean bl2 = true;
            RailShape railShape = (RailShape)state.get(SHAPE);
            switch(railShape) {
                case NORTH_SOUTH:
                    if (bl) {
                        ++k;
                    } else {
                        --k;
                    }
                    break;
                case EAST_WEST:
                    if (bl) {
                        --i;
                    } else {
                        ++i;
                    }
                    break;
                case ASCENDING_EAST:
                    if (bl) {
                        --i;
                    } else {
                        ++i;
                        ++j;
                        bl2 = false;
                    }

                    railShape = RailShape.EAST_WEST;
                    break;
                case ASCENDING_WEST:
                    if (bl) {
                        --i;
                        ++j;
                        bl2 = false;
                    } else {
                        ++i;
                    }

                    railShape = RailShape.EAST_WEST;
                    break;
                case ASCENDING_NORTH:
                    if (bl) {
                        ++k;
                    } else {
                        --k;
                        ++j;
                        bl2 = false;
                    }

                    railShape = RailShape.NORTH_SOUTH;
                    break;
                case ASCENDING_SOUTH:
                    if (bl) {
                        ++k;
                        ++j;
                        bl2 = false;
                    } else {
                        --k;
                    }

                    railShape = RailShape.NORTH_SOUTH;
            }

            if (this.isPoweredByOtherRails(world, new BlockPos(i, j, k), bl, distance, railShape)) {
                return true;
            } else {
                return bl2 && this.isPoweredByOtherRails(world, new BlockPos(i, j - 1, k), bl, distance, railShape);
            }
        }
    }

    public boolean isPoweredByOtherRails(World world, BlockPos pos, boolean bl, int distance, RailShape shape) {
        BlockState blockState = world.getBlockState(pos);
        if (!blockState.isOf(this)) {
            return false;
        } else {
            RailShape railShape = (RailShape)blockState.get(SHAPE);
            if (shape == RailShape.EAST_WEST && (railShape == RailShape.NORTH_SOUTH || railShape == RailShape.ASCENDING_NORTH || railShape == RailShape.ASCENDING_SOUTH)) {
                return false;
            } else if (shape == RailShape.NORTH_SOUTH && (railShape == RailShape.EAST_WEST || railShape == RailShape.ASCENDING_EAST || railShape == RailShape.ASCENDING_WEST)) {
                return false;
            } else if ((Boolean)blockState.get(POWERED)) {
                return world.isReceivingRedstonePower(pos) ? true : this.isPoweredByOtherRails(world, pos, blockState, bl, distance + 1);
            } else {
                return false;
            }
        }
    }

    public void updateBlockState(BlockState state, World world, BlockPos pos, Block neighbor) {
        boolean bl = (Boolean)state.get(POWERED);
        boolean bl2 = world.isReceivingRedstonePower(pos) || this.isPoweredByOtherRails(world, pos, state, true, 0) || this.isPoweredByOtherRails(world, pos, state, false, 0);
        if (bl2 != bl) {
            world.setBlockState(pos, (BlockState)state.with(POWERED, bl2), Block.NOTIFY_ALL);
            world.updateNeighborsAlways(pos.down(), this);
            if (((RailShape)state.get(SHAPE)).isAscending()) {
                world.updateNeighborsAlways(pos.up(), this);
            }
        }

    }

    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch(rotation) {
            case CLOCKWISE_180:
                switch((RailShape)state.get(SHAPE)) {
                    case ASCENDING_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    case SOUTH_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_WEST);
                    case SOUTH_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_WEST);
                }
            case COUNTERCLOCKWISE_90:
                switch((RailShape)state.get(SHAPE)) {
                    case NORTH_SOUTH:
                        return (BlockState)state.with(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_SOUTH);
                    case ASCENDING_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    case ASCENDING_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_NORTH:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_SOUTH:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_EAST);
                    case SOUTH_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_WEST);
                }
            case CLOCKWISE_90:
                switch((RailShape)state.get(SHAPE)) {
                    case NORTH_SOUTH:
                        return (BlockState)state.with(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_SOUTH);
                    case ASCENDING_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    case ASCENDING_NORTH:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_SOUTH:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_WEST);
                    case SOUTH_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_EAST);
                }
            default:
                return state;
        }
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        RailShape railShape = (RailShape)state.get(SHAPE);
        switch(mirror) {
            case LEFT_RIGHT:
                switch(railShape) {
                    case ASCENDING_NORTH:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    case SOUTH_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_EAST);
                    default:
                        return super.mirror(state, mirror);
                }
            case FRONT_BACK:
                switch(railShape) {
                    case ASCENDING_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                    case ASCENDING_SOUTH:
                    default:
                        break;
                    case SOUTH_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST:
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST:
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_WEST);
                }
        }

        return super.mirror(state, mirror);
    }

    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, POWERED, WATERLOGGED);
    }

    static {
        SHAPE = Properties.STRAIGHT_RAIL_SHAPE;
        POWERED = Properties.POWERED;
    }
}
