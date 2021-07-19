package safro.oxidized.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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
import safro.oxidized.block.CopperKilnBlock;
import safro.oxidized.block.CopperRailBlock;
import safro.oxidized.block.CustomOxidizableBlock;
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
    public static final Block VERTICAL_CUT_COPPER = new CustomOxidizableBlock(Oxidizable.OxidizationLevel.UNAFFECTED, AbstractBlock.Settings.copy(Blocks.COPPER_BLOCK));
    public static final Block VERTICAL_EXPOSED_CUT_COPPER = new CustomOxidizableBlock(Oxidizable.OxidizationLevel.EXPOSED, AbstractBlock.Settings.copy(Blocks.EXPOSED_COPPER));
    public static final Block VERTICAL_WEATHERED_CUT_COPPER = new CustomOxidizableBlock(Oxidizable.OxidizationLevel.WEATHERED, AbstractBlock.Settings.copy(Blocks.WEATHERED_COPPER));
    public static final Block VERTICAL_OXIDIZED_CUT_COPPER = new CustomOxidizableBlock(Oxidizable.OxidizationLevel.OXIDIZED, AbstractBlock.Settings.copy(Blocks.OXIDIZED_COPPER));
    public static final Block WAXED_VERTICAL_CUT_COPPER = new Block(AbstractBlock.Settings.copy(Blocks.COPPER_BLOCK));
    public static final Block WAXED_VERTICAL_EXPOSED_CUT_COPPER = new Block(AbstractBlock.Settings.copy(Blocks.COPPER_BLOCK));
    public static final Block WAXED_VERTICAL_WEATHERED_CUT_COPPER = new Block(AbstractBlock.Settings.copy(Blocks.COPPER_BLOCK));
    public static final Block WAXED_VERTICAL_OXIDIZED_CUT_COPPER = new Block(AbstractBlock.Settings.copy(Blocks.COPPER_BLOCK));

    public static final Block COPPER_KILN = new CopperKilnBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).luminance(createLightLevelBlockstate(13)).sounds(BlockSoundGroup.COPPER).nonOpaque());
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
        Registry.register(Registry.BLOCK, new Identifier("oxidized", "copper_kiln"), COPPER_KILN);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "copper_kiln"), new BlockItem(COPPER_KILN, new FabricItemSettings().group(Oxidized.ITEMGROUP)));

        COPPER_KILN_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "oxidized:copper_kiln_block_entity", FabricBlockEntityTypeBuilder.create(CopperKilnBlockEntity::new, COPPER_KILN).build(null));
    }
}
