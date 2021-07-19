package safro.oxidized;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import safro.oxidized.block.screen.CopperKilnScreen;
import safro.oxidized.registry.BlockRegistry;

public class OxidizedClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.COPPER_RAIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.COPPER_KILN, RenderLayer.getCutout());

        ScreenRegistry.register(BlockRegistry.COPPER_KILN_SCREEN_HANDLER, CopperKilnScreen::new);
    }
}
