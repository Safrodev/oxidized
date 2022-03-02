package safro.oxidized.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import safro.oxidized.Oxidized;
import safro.oxidized.block.*;
import safro.oxidized.block.entity.CopperKilnBlockEntity;
import safro.oxidized.block.screen.CopperKilnScreenHandler;
import safro.oxidized.recipe.CopperKilnRecipe;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.ToIntFunction;

public class BlockRegistry {
    private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();

    private static ToIntFunction<BlockState> createLightLevelBlockstate(int litLevel) {
        return (state) -> (Boolean)state.get(Properties.LIT) ? litLevel : 0;
    }

    public static final Block COPPER_RAIL = register("copper_rail", new CopperRailBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.7F).sounds(BlockSoundGroup.METAL)), true);
    public static final Block VERTICAL_CUT_COPPER = register("vertical_cut_copper", new CustomOxidizableBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.of(Material.METAL).mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.COPPER).requiresTool().strength(3.0F, 6.0F)), true);
    public static final Block VERTICAL_EXPOSED_CUT_COPPER = register("vertical_exposed_cut_copper", new CustomOxidizableBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)), true);
    public static final Block VERTICAL_WEATHERED_CUT_COPPER = register("vertical_weathered_cut_copper", new CustomOxidizableBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)), true);
    public static final Block VERTICAL_OXIDIZED_CUT_COPPER = register("vertical_oxidized_cut_copper", new CustomOxidizableBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)), true);
    public static final Block WAXED_VERTICAL_CUT_COPPER = register("waxed_vertical_cut_copper", new Block(AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)), true);
    public static final Block WAXED_VERTICAL_EXPOSED_CUT_COPPER = register("waxed_vertical_exposed_cut_copper", new Block(AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)), true);
    public static final Block WAXED_VERTICAL_WEATHERED_CUT_COPPER = register("waxed_vertical_weathered_cut_copper", new Block(AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)), true);
    public static final Block WAXED_VERTICAL_OXIDIZED_CUT_COPPER = register("waxed_vertical_oxidized_cut_copper", new Block(AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER)), true);
    public static final LanternBlock COPPER_LANTERN = register("copper_lantern", new LanternBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance((state) -> 15).nonOpaque()), true);
    public static final Block COPPER_PAN = register("copper_pan", new CopperPanBlock(blockWithStrength(3.0F, 6.0F).ticksRandomly()), true);
    public static final Block COPPER_BUTTON = register("copper_button", new CopperButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)), true);

    public static final Block COPPER_KILN = register("copper_kiln", new CopperKilnBlock(blockWithStrength(3.5F, 6.0F).luminance(createLightLevelBlockstate(13)).nonOpaque()), true);
    public static BlockEntityType<CopperKilnBlockEntity> COPPER_KILN_BLOCK_ENTITY;
    public static final ScreenHandlerType<CopperKilnScreenHandler> COPPER_KILN_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("oxidized", "copper_kiln_screen_handler"), CopperKilnScreenHandler::new);
    public static final RecipeType<CopperKilnRecipe> COPPER_KILN_RECIPE_TYPE = RecipeType.register("oxidized:kiln_smelting");
    public static final RecipeSerializer<CopperKilnRecipe> COPPER_KILN_RECIPE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("oxidized", "kiln_smelting"), new CookingRecipeSerializer<>(CopperKilnRecipe::new, 100));

    public static final Block COPPER_TRAP = register("copper_trap", new CopperTrapBlock(blockWithStrength(2.0F, 3.0F).noCollision()), true);

    private static <T extends Block> T register(String name, T block, boolean createItem) {
        BLOCKS.put(block, new Identifier("oxidized", name));
        if (createItem) {
            Registry.register(Registry.ITEM, new Identifier("oxidized", name), new BlockItem(block, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        }
        return block;
    }

    public static void init() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
        COPPER_KILN_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "oxidized:copper_kiln_block_entity", FabricBlockEntityTypeBuilder.create(CopperKilnBlockEntity::new, COPPER_KILN).build(null));
    }

    private static FabricBlockSettings blockWithStrength(float hardness, float resistance) {
        return FabricBlockSettings.of(Material.METAL).mapColor(MapColor.ORANGE).strength(hardness, resistance).requiresTool().sounds(BlockSoundGroup.COPPER);
    }
}
