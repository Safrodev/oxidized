package safro.oxidized.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import safro.oxidized.Oxidized;
import safro.oxidized.item.CopperPulsarItem;
import safro.oxidized.item.rosegold.BasicAxeItem;
import safro.oxidized.item.rosegold.BasicHoeItem;
import safro.oxidized.item.rosegold.BasicPickaxeItem;
import safro.oxidized.item.rosegold.RoseGoldMaterial;

public class ItemRegistry {
    public static final Item COPPER_NUGGET = new Item(new FabricItemSettings().group(Oxidized.ITEMGROUP));

    public static void init() {
        // With the way registries changed in 1.18.2, this is the best (as of now) way to register configurable items
        if (Oxidized.CONFIG.enable_copper_pulsar) {
            Registry.register(Registry.ITEM, new Identifier("oxidized", "copper_pulsar"), new CopperPulsarItem(basic()));
        }

        Registry.register(Registry.ITEM, new Identifier("oxidized", "copper_nugget"), COPPER_NUGGET);

        if (Oxidized.CONFIG.enable_rose_gold_tools) {
            Registry.register(Registry.ITEM, new Identifier("oxidized", "rose_gold_axe"), new BasicAxeItem(RoseGoldMaterial.ROSE_GOLD, 6.0F, -3.0F, basic()));
            Registry.register(Registry.ITEM, new Identifier("oxidized", "rose_gold_hoe"), new BasicHoeItem(RoseGoldMaterial.ROSE_GOLD, 0, -3.0F, basic()));
            Registry.register(Registry.ITEM, new Identifier("oxidized", "rose_gold_pickaxe"), new BasicPickaxeItem(RoseGoldMaterial.ROSE_GOLD, 1, -2.8F, basic()));
            Registry.register(Registry.ITEM, new Identifier("oxidized", "rose_gold_shovel"), new ShovelItem(RoseGoldMaterial.ROSE_GOLD, 1.5F, -3.0F, basic()));
            Registry.register(Registry.ITEM, new Identifier("oxidized", "rose_gold_sword"), new SwordItem(RoseGoldMaterial.ROSE_GOLD, 4, -2.4F, basic()));
        }

        if (Oxidized.CONFIG.enable_copper_golem) {
            Registry.register(Registry.ITEM, new Identifier("oxidized", "copper_golem_spawn_egg"), new SpawnEggItem(EntityRegistry.COPPER_GOLEM, 0x8A4129, 0xC15B36, basic()));
        }
    }

    public static Item.Settings basic() {
        return new Item.Settings().group(Oxidized.ITEMGROUP);
    }
}
