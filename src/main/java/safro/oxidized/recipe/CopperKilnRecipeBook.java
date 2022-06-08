package safro.oxidized.recipe;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

import java.util.Set;

public class CopperKilnRecipeBook extends AbstractFurnaceRecipeBookScreen {
    private static final Text LABEL = Text.translatable("gui.recipebook.toggleRecipes.kiln");

    protected Text getToggleCraftableButtonText() {
        return LABEL;
    }

    @Override
    protected Set<Item> getAllowedFuels() {
        return AbstractFurnaceBlockEntity.createFuelTimeMap().keySet();
    }
}
