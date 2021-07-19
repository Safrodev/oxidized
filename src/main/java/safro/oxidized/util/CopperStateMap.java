package safro.oxidized.util;

import net.minecraft.block.Block;
import org.jetbrains.annotations.Nullable;
import safro.oxidized.registry.BlockRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CopperStateMap {

    private static final Map<Block, Block> INCREASES = new HashMap<>();

    private static final Map<Block, Block> DECREASES = new HashMap<>();

    private static final Map<Block, Block> WAXED = new HashMap<>();

    private static final Map<Block, Block> UNWAXED = new HashMap<>();

    public static void put(Block block, @Nullable Block oxidized, Block waxed) {
        if (oxidized != null) {
            INCREASES.put(block, oxidized);
            DECREASES.put(oxidized, block);
        }
        WAXED.put(block, waxed);
        UNWAXED.put(waxed, block);
    }

    public static Optional<Block> getIncrease(Block block) {
        return Optional.ofNullable(INCREASES.get(block));
    }

    public static Optional<Block> getDecrease(Block block) {
        return Optional.ofNullable(DECREASES.get(block));
    }

    public static Optional<Block> getWaxed(Block block) {
        return Optional.ofNullable(WAXED.get(block));
    }

    public static Optional<Block> getUnwaxed(Block block) {
        return Optional.ofNullable(UNWAXED.get(block));
    }

    public static Block getOriginalStage(Block block) {
        var result = block;
        for (var b = getDecrease(block); b.isPresent(); b = getDecrease(b.get())) {
            result = b.get();
        }
        return result;
    }

    static {
        put(BlockRegistry.VERTICAL_CUT_COPPER, BlockRegistry.VERTICAL_EXPOSED_CUT_COPPER, BlockRegistry.WAXED_VERTICAL_CUT_COPPER);
        put(BlockRegistry.VERTICAL_EXPOSED_CUT_COPPER, BlockRegistry.VERTICAL_WEATHERED_CUT_COPPER, BlockRegistry.WAXED_VERTICAL_EXPOSED_CUT_COPPER);
        put(BlockRegistry.VERTICAL_WEATHERED_CUT_COPPER, BlockRegistry.VERTICAL_OXIDIZED_CUT_COPPER, BlockRegistry.WAXED_VERTICAL_WEATHERED_CUT_COPPER);
        put(BlockRegistry.VERTICAL_OXIDIZED_CUT_COPPER, null, BlockRegistry.WAXED_VERTICAL_OXIDIZED_CUT_COPPER);
    }
}
