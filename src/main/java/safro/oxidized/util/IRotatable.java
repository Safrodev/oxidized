package safro.oxidized.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IRotatable {

    boolean rotate(BlockState blockState, World world, BlockPos pos, PlayerEntity player);

    boolean invRotate(BlockState blockState, World world, BlockPos pos, PlayerEntity player);
}
