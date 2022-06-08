package safro.oxidized;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import safro.oxidized.block.screen.CopperKilnScreen;
import safro.oxidized.entity.CopperGolemEntityModel;
import safro.oxidized.entity.CopperGolemEntityRenderer;
import safro.oxidized.registry.BlockRegistry;
import safro.oxidized.registry.EntityRegistry;

public class OxidizedClient implements ClientModInitializer {
    public static final EntityModelLayer COPPER_GOLEM_LAYER = new EntityModelLayer(new Identifier("oxidized", "copper_golem_layer"), "copper_golem_layer");

    @Override
    public void onInitializeClient() {
        if (Oxidized.CONFIG.enable_copper_golem) {
            EntityModelLayerRegistry.registerModelLayer(COPPER_GOLEM_LAYER, CopperGolemEntityModel::getTexturedModelData);
            EntityRendererRegistry.register(EntityRegistry.COPPER_GOLEM, CopperGolemEntityRenderer::new);
        }

        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.COPPER_RAIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.COPPER_KILN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.COPPER_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.COPPER_TRAP, RenderLayer.getCutout());

        ScreenRegistry.register(BlockRegistry.COPPER_KILN_SCREEN_HANDLER, CopperKilnScreen::new);
    }
}
