package safro.oxidized;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import safro.oxidized.config.OxidizedConfig;
import safro.oxidized.registry.BlockRegistry;
import safro.oxidized.registry.EntityRegistry;
import safro.oxidized.registry.ItemRegistry;
import safro.oxidized.util.TrapDamageSource;
import safro.saflib.SafLib;

import java.io.File;
import java.util.ArrayList;

public class Oxidized implements ModInitializer {
	public static RegistryKey<ItemGroup> ITEMGROUP = SafLib.createGroup("oxidized");
	public static final Logger LOGGER = LogManager.getLogger("oxidized");
	public static OxidizedConfig CONFIG;

	@Override
	public void onInitialize() {
		CONFIG = OxidizedConfig.loadConfig(new File(FabricLoader.getInstance().getConfigDir() + "/oxidized_config.json"));

		TrapDamageSource.init();
		BlockRegistry.init();
		ItemRegistry.init();
		EntityRegistry.init();

		SafLib.registerAll(ITEMGROUP, BlockRegistry.COPPER_KILN);
	}
}
