package safro.oxidized;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import safro.oxidized.registry.BlockRegistry;
import safro.oxidized.registry.ItemRegistry;

public class Oxidized implements ModInitializer {

	public static ItemGroup ITEMGROUP = FabricItemGroupBuilder.build(
			new Identifier("oxidized", "item_group"),
			() -> new ItemStack(BlockRegistry.COPPER_RAIL));


	@Override
	public void onInitialize() {
		System.out.println("Oxidizing your Copper!");

		BlockRegistry.init();
		ItemRegistry.init();

	}
}
