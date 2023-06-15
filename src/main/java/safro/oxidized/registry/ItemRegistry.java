package safro.oxidized.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import safro.oxidized.Oxidized;
import safro.oxidized.item.BarometerItem;
import safro.oxidized.item.CopperPulsarItem;
import safro.oxidized.item.rosegold.BasicAxeItem;
import safro.oxidized.item.rosegold.BasicHoeItem;
import safro.oxidized.item.rosegold.BasicPickaxeItem;
import safro.oxidized.item.rosegold.RoseGoldMaterial;

public class ItemRegistry {

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, Oxidized.ITEMGROUP, FabricItemGroup.builder()
                .icon(() -> new ItemStack(BlockRegistry.COPPER_KILN))
                .displayName(Text.translatable("itemGroup.oxidized.item_group")).build());

        // With the way registries changed in 1.18.2, this is the best (as of now) way to register configurable items
        if (Oxidized.CONFIG.enable_copper_pulsar) {
            register("copper_pulsar", new CopperPulsarItem(basic()));
        }

        register("copper_barometer", new BarometerItem(basic().maxCount(1)));
        register("copper_nugget", new Item(basic()));

        if (Oxidized.CONFIG.enable_rose_gold_tools) {
            register("rose_gold_axe", new BasicAxeItem(RoseGoldMaterial.ROSE_GOLD, 6.0F, -3.0F, basic()));
            register("rose_gold_hoe", new BasicHoeItem(RoseGoldMaterial.ROSE_GOLD, 0, -3.0F, basic()));
            register("rose_gold_pickaxe", new BasicPickaxeItem(RoseGoldMaterial.ROSE_GOLD, 1, -2.8F, basic()));
            register("rose_gold_shovel", new ShovelItem(RoseGoldMaterial.ROSE_GOLD, 1.5F, -3.0F, basic()));
            register("rose_gold_sword", new SwordItem(RoseGoldMaterial.ROSE_GOLD, 4, -2.4F, basic()));
        }

        if (Oxidized.CONFIG.enable_copper_golem) {
            register("copper_golem_spawn_egg", new SpawnEggItem(EntityRegistry.COPPER_GOLEM, 0x8A4129, 0xC15B36, basic()));
        }
    }

    public static Item.Settings basic() {
        return new Item.Settings();
    }

    private static <T extends Item> Item register(String id, T item) {
        T registered = Registry.register(Registries.ITEM, new Identifier("oxidized", id), item);
        Oxidized.ITEMS.add(new ItemStack(registered));
        return registered;
    }
}
