package safro.oxidized.block.screen;


import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import safro.oxidized.recipe.CopperKilnRecipeBook;

public class CopperKilnScreen extends AbstractFurnaceScreen<CopperKilnScreenHandler> {
    private static final Identifier LIT_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/burn_progress");
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/gui/container/furnace.png");

    public CopperKilnScreen(CopperKilnScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new CopperKilnRecipeBook(), inventory, title, TEXTURE, LIT_PROGRESS_TEXTURE, BURN_PROGRESS_TEXTURE);
    }
}
