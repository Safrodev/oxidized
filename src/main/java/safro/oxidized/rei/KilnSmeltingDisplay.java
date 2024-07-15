package safro.oxidized.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.plugin.common.displays.cooking.DefaultCookingDisplay;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import safro.oxidized.recipe.CopperKilnRecipe;

import java.util.List;

public class KilnSmeltingDisplay extends DefaultCookingDisplay {
    public KilnSmeltingDisplay(RecipeEntry<CopperKilnRecipe> recipe) {
        super(recipe);
    }

    public KilnSmeltingDisplay(List<EntryIngredient> input, List<EntryIngredient> output, NbtCompound tag) {
        super(input, output, tag);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return OxidizedREIPlugin.KILN_SMELTING;
    }
}
