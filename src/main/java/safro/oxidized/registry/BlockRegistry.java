package safro.oxidized.registry;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import safro.oxidized.block.*;
import safro.oxidized.block.entity.CopperKilnBlockEntity;
import safro.oxidized.block.screen.CopperKilnScreenHandler;
import safro.oxidized.recipe.CopperKilnRecipe;
import safro.saflib.registry.BaseBlockItemRegistry;

import java.util.function.ToIntFunction;

public class BlockRegistry extends BaseBlockItemRegistry {
    static { MODID = "oxidized"; }

    public static final Block COPPER_RAIL = register("copper_rail", new CopperRailBlock(AbstractBlock.Settings.create().mapColor(MapColor.CLEAR).nonOpaque().pistonBehavior(PistonBehavior.DESTROY).noCollision().strength(0.7F).sounds(BlockSoundGroup.METAL)));
    public static final Block VERTICAL_CUT_COPPER = register("vertical_cut_copper", new OxidizableBlock(Oxidizable.OxidationLevel.UNAFFECTED, AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.COPPER).requiresTool().strength(3.0F, 6.0F)));
    public static final Block VERTICAL_EXPOSED_CUT_COPPER = register("vertical_exposed_cut_copper", new OxidizableBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)));
    public static final Block VERTICAL_WEATHERED_CUT_COPPER = register("vertical_weathered_cut_copper", new OxidizableBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)));
    public static final Block VERTICAL_OXIDIZED_CUT_COPPER = register("vertical_oxidized_cut_copper", new OxidizableBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)));
    public static final Block WAXED_VERTICAL_CUT_COPPER = register("waxed_vertical_cut_copper", new Block(AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)));
    public static final Block WAXED_VERTICAL_EXPOSED_CUT_COPPER = register("waxed_vertical_exposed_cut_copper", new Block(AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)));
    public static final Block WAXED_VERTICAL_WEATHERED_CUT_COPPER = register("waxed_vertical_weathered_cut_copper", new Block(AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)));
    public static final Block WAXED_VERTICAL_OXIDIZED_CUT_COPPER = register("waxed_vertical_oxidized_cut_copper", new Block(AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)));
    public static final LanternBlock COPPER_LANTERN = register("copper_lantern", new LanternBlock(AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance((state) -> 15).nonOpaque()));
    public static final Block COPPER_PAN = register("copper_pan", new CopperPanBlock(blockWithStrength(3.0F, 6.0F).ticksRandomly()));
    public static final Block COPPER_BUTTON = register("copper_button", new CopperButtonBlock(AbstractBlock.Settings.create().mapColor(MapColor.CLEAR).nonOpaque().pistonBehavior(PistonBehavior.DESTROY).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));

    public static final Block COPPER_KILN = register("copper_kiln", new CopperKilnBlock(blockWithStrength(3.5F, 6.0F).luminance(createLightLevelBlockstate(13)).nonOpaque()));
    public static BlockEntityType<CopperKilnBlockEntity> COPPER_KILN_BLOCK_ENTITY = register("copper_kiln", CopperKilnBlockEntity::new, COPPER_KILN);
    public static final ScreenHandlerType<CopperKilnScreenHandler> COPPER_KILN_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of("oxidized", "copper_kiln_screen_handler"), new ScreenHandlerType<>(CopperKilnScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
    public static final RecipeType<CopperKilnRecipe> COPPER_KILN_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, Identifier.of("oxidized", "kiln_smelting"), new RecipeType<>() {
        public String toString() {
            return "oxidized:kiln_smelting";
        }
    });
    public static final RecipeSerializer<CopperKilnRecipe> COPPER_KILN_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of("oxidized", "kiln_smelting"), new CookingRecipeSerializer<>(CopperKilnRecipe::new, 100));

    public static final Block COPPER_TRAP = register("copper_trap", new CopperTrapBlock(blockWithStrength(2.0F, 3.0F).noCollision()));

    public static void init() {
        OxidizableBlocksRegistry.registerOxidizableBlockPair(VERTICAL_CUT_COPPER, VERTICAL_EXPOSED_CUT_COPPER);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(VERTICAL_EXPOSED_CUT_COPPER, VERTICAL_WEATHERED_CUT_COPPER);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(VERTICAL_WEATHERED_CUT_COPPER, VERTICAL_OXIDIZED_CUT_COPPER);

        OxidizableBlocksRegistry.registerWaxableBlockPair(VERTICAL_CUT_COPPER, WAXED_VERTICAL_CUT_COPPER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(VERTICAL_EXPOSED_CUT_COPPER, WAXED_VERTICAL_EXPOSED_CUT_COPPER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(VERTICAL_WEATHERED_CUT_COPPER, WAXED_VERTICAL_WEATHERED_CUT_COPPER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(VERTICAL_OXIDIZED_CUT_COPPER, WAXED_VERTICAL_OXIDIZED_CUT_COPPER);
    }

    private static AbstractBlock.Settings blockWithStrength(float hardness, float resistance) {
        return AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).strength(hardness, resistance).requiresTool().sounds(BlockSoundGroup.COPPER);
    }

    private static ToIntFunction<BlockState> createLightLevelBlockstate(int litLevel) {
        return (state) -> (Boolean)state.get(Properties.LIT) ? litLevel : 0;
    }
}
