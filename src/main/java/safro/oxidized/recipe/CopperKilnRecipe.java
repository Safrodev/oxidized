package safro.oxidized.recipe;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import safro.oxidized.registry.BlockRegistry;

public class CopperKilnRecipe extends AbstractCookingRecipe {

    public CopperKilnRecipe(Identifier id, String group, Ingredient input, ItemStack output, float experience, int cookTime) {
        super(BlockRegistry.COPPER_KILN_RECIPE_TYPE, id, group, input, output, experience, cookTime);
    }

    public ItemStack createIcon() {
        return new ItemStack(BlockRegistry.COPPER_KILN);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BlockRegistry.COPPER_KILN_RECIPE_SERIALIZER;
    }
}
