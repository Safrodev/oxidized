package safro.oxidized.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import safro.oxidized.util.TrapDamageSource;

public class CopperTrapBlock extends Block {
    public static final BooleanProperty CLOSED;
    protected static final VoxelShape SHAPE;

    public CopperTrapBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CLOSED, false));
    }

    @SuppressWarnings("deprecation")
    // Overriding is not deprecated (see https://maven.fabricmc.net/docs/yarn-1.20.1+build.1/net/minecraft/block/AbstractBlock.html#deprecated-methods)
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isEmpty()) {
            if (!world.isClient) {
                if (state.get(CLOSED)) {
                    world.setBlockState(pos, state.with(CLOSED, false), 3);
                } else {
                    world.setBlockState(pos, state.with(CLOSED, true), 3);
                }
            }
            world.playSound(null, pos, SoundEvents.BLOCK_COPPER_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @SuppressWarnings("deprecation")
    // Overriding is not deprecated (see https://maven.fabricmc.net/docs/yarn-1.20.1+build.1/net/minecraft/block/AbstractBlock.html#deprecated-methods)
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && !entity.canAvoidTraps()) {
            if (!state.get(CLOSED)) {
                if (!world.isClient && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                    double d = Math.abs(entity.getX() - entity.lastRenderX);
                    double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                    if (d >= 0.003000000026077032D || e >= 0.003000000026077032D) {
                        entity.damage(TrapDamageSource.create(world), 5.0F);
                    }
                    this.schedule(state, world, pos);
                }
            }
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CLOSED);
        super.appendProperties(builder);
    }

    @SuppressWarnings("deprecation")
    // Overriding is not deprecated (see https://maven.fabricmc.net/docs/yarn-1.20.1+build.1/net/minecraft/block/AbstractBlock.html#deprecated-methods)
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public void schedule(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(CLOSED, true), 3);
        world.scheduleBlockTick(pos, this, 60);
        world.updateNeighborsAlways(pos, this);
    }

    @SuppressWarnings("deprecation")
    // Overriding is not deprecated (see https://maven.fabricmc.net/docs/yarn-1.20.1+build.1/net/minecraft/block/AbstractBlock.html#deprecated-methods)
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(CLOSED)) {
            world.setBlockState(pos, state.with(CLOSED, false));
            world.updateNeighborsAlways(pos, this);
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(CLOSED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @SuppressWarnings("deprecation")
    // Overriding is not deprecated (see https://maven.fabricmc.net/docs/yarn-1.20.1+build.1/net/minecraft/block/AbstractBlock.html#deprecated-methods)
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            world.setBlockState(pos, state.with(CLOSED, world.isReceivingRedstonePower(pos)));
        }
    }

    public static boolean isClosed(BlockState state) {
        return state.get(CLOSED);
    }

    static {
        CLOSED = BooleanProperty.of("closed");
        SHAPE = Block.createCuboidShape(2, 0, 1, 14, 2, 15);
    }
}
