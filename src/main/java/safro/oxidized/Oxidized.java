package safro.oxidized;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import safro.oxidized.config.OxidizedConfig;
import safro.oxidized.registry.BlockRegistry;
import safro.oxidized.registry.EntityRegistry;
import safro.oxidized.registry.ItemRegistry;

import java.io.File;

public class Oxidized implements ModInitializer {

	public static ItemGroup ITEMGROUP = FabricItemGroupBuilder.build(new Identifier("oxidized", "item_group"), () -> new ItemStack(BlockRegistry.COPPER_KILN));
	public static final Logger LOGGER = LogManager.getLogger("oxidized");
	public static OxidizedConfig CONFIG;

	@Override
	public void onInitialize() {
		CONFIG = OxidizedConfig.loadConfig(new File(FabricLoader.getInstance().getConfigDir() + "/oxidized_config.json"));

		BlockRegistry.init();
		ItemRegistry.init();
		EntityRegistry.init();
	}
}
