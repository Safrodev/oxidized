package safro.oxidized.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.util.Identifier;
import safro.oxidized.registry.BlockRegistry;

public class CopperKilnRecipe extends AbstractCookingRecipe {

    public CopperKilnRecipe(String group, CookingRecipeCategory category, Ingredient input, ItemStack output, float experience, int cookTime) {
        super(BlockRegistry.COPPER_KILN_RECIPE_TYPE, group, category,  input, output, experience, cookTime);
    }

    public ItemStack createIcon() {
        return new ItemStack(BlockRegistry.COPPER_KILN);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BlockRegistry.COPPER_KILN_RECIPE_SERIALIZER;
    }
}
