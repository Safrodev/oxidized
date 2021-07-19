package safro.oxidized.block.screen;


import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import safro.oxidized.recipe.CopperKilnRecipeBook;

public class CopperKilnScreen extends AbstractFurnaceScreen<CopperKilnScreenHandler> {

    private static final Identifier TEXTURE = new Identifier("textures/gui/container/furnace.png");

    public CopperKilnScreen(CopperKilnScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new CopperKilnRecipeBook(), inventory, title, TEXTURE);
    }
}
