package safro.oxidized;

import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import safro.oxidized.config.OxidizedConfig;
import safro.oxidized.registry.BlockRegistry;
import safro.oxidized.registry.EntityRegistry;
import safro.oxidized.registry.ItemRegistry;

public class Oxidized implements ModInitializer {

	public static ItemGroup ITEMGROUP = FabricItemGroupBuilder.build(
			new Identifier("oxidized", "item_group"),
			() -> new ItemStack(ItemRegistry.COPPER_PULSAR));

	public static final OxidizedConfig CONFIG = OmegaConfig.register(OxidizedConfig.class);
	public static final Logger LOGGER = LogManager.getLogger("oxidized");

	@Override
	public void onInitialize() {
		System.out.println("Oxidizing your Copper!");

		BlockRegistry.init();
		ItemRegistry.init();
		EntityRegistry.init();
	}
}
