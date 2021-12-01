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

import java.util.function.ToIntFunction;

public class BlockRegistry {

    private static ToIntFunction<BlockState> createLightLevelBlockstate(int litLevel) {
        return (state) -> {
            return (Boolean)state.get(Properties.LIT) ? litLevel : 0;
        };
    }

    public static final Block COPPER_RAIL = new CopperRailBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.7F).sounds(BlockSoundGroup.METAL));
    public static final Block VERTICAL_CUT_COPPER = new CustomOxidizableBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.of(Material.METAL).mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.COPPER).requiresTool().strength(3.0F, 6.0F));
    public static final Block VERTICAL_EXPOSED_CUT_COPPER = new CustomOxidizableBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER));
    public static final Block VERTICAL_WEATHERED_CUT_COPPER = new CustomOxidizableBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER));
    public static final Block VERTICAL_OXIDIZED_CUT_COPPER = new CustomOxidizableBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER));
    public static final Block WAXED_VERTICAL_CUT_COPPER = new Block(AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER));
    public static final Block WAXED_VERTICAL_EXPOSED_CUT_COPPER = new Block(AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER));
    public static final Block WAXED_VERTICAL_WEATHERED_CUT_COPPER = new Block(AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER));
    public static final Block WAXED_VERTICAL_OXIDIZED_CUT_COPPER = new Block(AbstractBlock.Settings.copy(BlockRegistry.VERTICAL_CUT_COPPER));
    public static final LanternBlock COPPER_LANTERN = new LanternBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance((state) -> {
        return 15;
    }).nonOpaque());
    public static final Block COPPER_PAN = new CopperPanBlock(FabricBlockSettings.of(Material.METAL).mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.COPPER).requiresTool().strength(3.0F, 6.0F).ticksRandomly());
    public static final Block COPPER_BUTTON = new CopperButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER));

    public static final Block COPPER_KILN = new CopperKilnBlock(FabricBlockSettings.of(Material.METAL).mapColor(MapColor.ORANGE).requiresTool().strength(3.5F, 6.0F).luminance(createLightLevelBlockstate(13)).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static BlockEntityType<CopperKilnBlockEntity> COPPER_KILN_BLOCK_ENTITY;
    public static final ScreenHandlerType<CopperKilnScreenHandler> COPPER_KILN_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("oxidized", "copper_kiln_screen_handler"), CopperKilnScreenHandler::new);;
    public static final RecipeType<CopperKilnRecipe> COPPER_KILN_RECIPE_TYPE = RecipeType.register("oxidized:kiln_smelting");
    public static final RecipeSerializer<CopperKilnRecipe> COPPER_KILN_RECIPE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("oxidized", "kiln_smelting"), new CookingRecipeSerializer<>(CopperKilnRecipe::new, 100));


    public static void init() {

        Registry.register(Registry.BLOCK, new Identifier("oxidized", "copper_rail"), COPPER_RAIL);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "copper_rail"), new BlockItem(COPPER_RAIL, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "vertical_cut_copper"), VERTICAL_CUT_COPPER);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "vertical_cut_copper"), new BlockItem(VERTICAL_CUT_COPPER, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "vertical_exposed_cut_copper"), VERTICAL_EXPOSED_CUT_COPPER);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "vertical_exposed_cut_copper"), new BlockItem(VERTICAL_EXPOSED_CUT_COPPER, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "vertical_weathered_cut_copper"), VERTICAL_WEATHERED_CUT_COPPER);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "vertical_weathered_cut_copper"), new BlockItem(VERTICAL_WEATHERED_CUT_COPPER, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "vertical_oxidized_cut_copper"), VERTICAL_OXIDIZED_CUT_COPPER);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "vertical_oxidized_cut_copper"), new BlockItem(VERTICAL_OXIDIZED_CUT_COPPER, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "waxed_vertical_cut_copper"), WAXED_VERTICAL_CUT_COPPER);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "waxed_vertical_cut_copper"), new BlockItem(WAXED_VERTICAL_CUT_COPPER, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "waxed_vertical_exposed_cut_copper"), WAXED_VERTICAL_EXPOSED_CUT_COPPER);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "waxed_vertical_exposed_cut_copper"), new BlockItem(WAXED_VERTICAL_EXPOSED_CUT_COPPER, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "waxed_vertical_weathered_cut_copper"), WAXED_VERTICAL_WEATHERED_CUT_COPPER);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "waxed_vertical_weathered_cut_copper"), new BlockItem(WAXED_VERTICAL_WEATHERED_CUT_COPPER, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "waxed_vertical_oxidized_cut_copper"), WAXED_VERTICAL_OXIDIZED_CUT_COPPER);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "waxed_vertical_oxidized_cut_copper"), new BlockItem(WAXED_VERTICAL_OXIDIZED_CUT_COPPER, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "copper_lantern"), COPPER_LANTERN);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "copper_lantern"), new BlockItem(COPPER_LANTERN, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "copper_pan"), COPPER_PAN);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "copper_pan"), new BlockItem(COPPER_PAN, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "copper_button"), COPPER_BUTTON);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "copper_button"), new BlockItem(COPPER_BUTTON, new FabricItemSettings().group(Oxidized.ITEMGROUP)));
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "copper_kiln"), COPPER_KILN);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "copper_kiln"), new BlockItem(COPPER_KILN, new FabricItemSettings().group(Oxidized.ITEMGROUP)));

        COPPER_KILN_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "oxidized:copper_kiln_block_entity", FabricBlockEntityTypeBuilder.create(CopperKilnBlockEntity::new, COPPER_KILN).build(null));
    }
}
