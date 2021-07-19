package safro.oxidized.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import safro.oxidized.Oxidized;
import safro.oxidized.item.CopperPulsarItem;

public class ItemRegistry {

    public static final Item COPPER_PULSAR = new CopperPulsarItem(new FabricItemSettings().group(Oxidized.ITEMGROUP));
    public static final Item COPPER_SHEARS = new ShearsItem(new FabricItemSettings().group(Oxidized.ITEMGROUP).maxDamage(160));

    public static void init() {

        Registry.register(Registry.ITEM, new Identifier("oxidized", "copper_pulsar"), COPPER_PULSAR);
    }
}
