package safro.oxidized.block;

import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import safro.oxidized.Oxidized;

public class CopperPanBlock extends Block implements Waterloggable {
    public static final IntProperty PANNED;
    public static final BooleanProperty WATERLOGGED;
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);

    public CopperPanBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PANNED, 0).with(WATERLOGGED, false));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(PANNED);
		float range = 0.55F;
        if (state.getFluidState().isIn(FluidTags.WATER) && isSpecialBlockBelow(world, pos)) {
            if (i < 2) {
                world.setBlockState(pos, state.with(PANNED, i + 1), 2);
            } else {
                if (Oxidized.CONFIG.enable_copper_pan_sound) world.playSound(null, pos, SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, SoundCategory.BLOCKS, 0.4F, 0.9F + random.nextFloat() * 0.2F);
                double d = (double)(world.random.nextFloat() * range) + (1-range)/2;
                double e = 0.66000000238418579D;
                double g = (double)(world.random.nextFloat() * range) + (1-range)/2;
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + g, new ItemStack(getPannedItem(random)));
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
    }

    public Item getPannedItem(Random random) {
        if (random.nextFloat() <= Oxidized.CONFIG.iron_nugget_chance) {
            return Items.IRON_NUGGET;
        } else if (random.nextFloat() <= Oxidized.CONFIG.gold_nugget_chance) {
            return Items.GOLD_NUGGET;
        } else if (random.nextFloat() <= Oxidized.CONFIG.sand_chance) {
            return Items.SAND;
        } else if (random.nextFloat() <= Oxidized.CONFIG.emerald_chance) {
            return Items.EMERALD;
        } else if (random.nextFloat() <= Oxidized.CONFIG.gravel_chance) {
            return Items.GRAVEL;
        }
        else
            return Items.CLAY_BALL;
    }

    public static boolean isSpecialBlockBelow(BlockView world, BlockPos pos) {
        return isBlock(world, pos.down());
    }

    public static boolean isBlock(BlockView world, BlockPos pos) {
        return world.getBlockState(pos).isIn(BlockTags.SAND) || world.getBlockState(pos).isOf(Blocks.GRAVEL);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PANNED);
        builder.add(WATERLOGGED);
        super.appendProperties(builder);
    }

    static {
        PANNED = Properties.HATCH;
        WATERLOGGED = Properties.WATERLOGGED;
    }

}
