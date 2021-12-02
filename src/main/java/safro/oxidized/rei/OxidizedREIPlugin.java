package safro.oxidized.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.client.categories.cooking.DefaultCookingCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import safro.oxidized.block.screen.CopperKilnScreen;
import safro.oxidized.recipe.CopperKilnRecipe;
import safro.oxidized.registry.BlockRegistry;

public class OxidizedREIPlugin implements REIClientPlugin {
    public static final CategoryIdentifier<KilnSmeltingDisplay> KILN_SMELTING = CategoryIdentifier.of("oxidized", "plugins/kiln_smelting");

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new DefaultCookingCategory(KILN_SMELTING, EntryStacks.of(BlockRegistry.COPPER_KILN), "category.oxidized.kiln_smelting"));
        registry.addWorkstations(KILN_SMELTING, EntryStacks.of(BlockRegistry.COPPER_KILN));
        registry.addWorkstations(CategoryIdentifier.of(new Identifier("minecraft", "plugins/fuel")), EntryStack.of(VanillaEntryTypes.ITEM, new ItemStack(BlockRegistry.COPPER_KILN)));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(CopperKilnRecipe.class, BlockRegistry.COPPER_KILN_RECIPE_TYPE, KilnSmeltingDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(78, 32, 28, 23), CopperKilnScreen.class, KILN_SMELTING);
    }
}
