package safro.oxidized.block.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import safro.oxidized.registry.BlockRegistry;

public class CopperKilnScreenHandler extends AbstractFurnaceScreenHandler {

    public CopperKilnScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(BlockRegistry.COPPER_KILN_SCREEN_HANDLER, BlockRegistry.COPPER_KILN_RECIPE_TYPE, RecipeBookCategory.FURNACE, syncId, playerInventory);
    }

    public CopperKilnScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(BlockRegistry.COPPER_KILN_SCREEN_HANDLER, BlockRegistry.COPPER_KILN_RECIPE_TYPE, RecipeBookCategory.FURNACE, syncId, playerInventory, inventory, propertyDelegate);
    }
}
