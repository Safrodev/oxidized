package safro.oxidized.registry;

import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import safro.oxidized.Oxidized;
import safro.oxidized.item.BarometerItem;
import safro.oxidized.item.CopperPulsarItem;
import safro.oxidized.item.rosegold.BasicAxeItem;
import safro.oxidized.item.rosegold.BasicHoeItem;
import safro.oxidized.item.rosegold.BasicPickaxeItem;
import safro.oxidized.item.rosegold.RoseGoldMaterial;
import safro.saflib.registry.BaseBlockItemRegistry;

import java.util.List;

public class ItemRegistry extends BaseBlockItemRegistry {
    static { MODID = "oxidized"; }

    public static void init() {
        // With the way registries changed in 1.18.2, this is the best way to register configurable items
        if (Oxidized.CONFIG.enable_copper_pulsar) {
            register("copper_pulsar", new CopperPulsarItem(settings()));
        }

        register("copper_barometer", new BarometerItem(settings().maxCount(1)));
        register("copper_nugget", new Item(settings()));

        if (Oxidized.CONFIG.enable_rose_gold_tools) {
            register("rose_gold_upgrade_template", createRoseGoldTemplate());
            register("rose_gold_axe", new BasicAxeItem(RoseGoldMaterial.ROSE_GOLD, 6.0F, -3.0F, settings()));
            register("rose_gold_hoe", new BasicHoeItem(RoseGoldMaterial.ROSE_GOLD, 0, -3.0F, settings()));
            register("rose_gold_pickaxe", new BasicPickaxeItem(RoseGoldMaterial.ROSE_GOLD, 1, -2.8F, settings()));
            register("rose_gold_shovel", new ShovelItem(RoseGoldMaterial.ROSE_GOLD, 1.5F, -3.0F, settings()));
            register("rose_gold_sword", new SwordItem(RoseGoldMaterial.ROSE_GOLD, 4, -2.4F, settings()));
        }

        if (Oxidized.CONFIG.enable_copper_golem) {
            register("copper_golem_spawn_egg", new SpawnEggItem(EntityRegistry.COPPER_GOLEM, 0x8A4129, 0xC15B36, settings()));
        }
    }

    // ...
    private static SmithingTemplateItem createRoseGoldTemplate() {
        return new SmithingTemplateItem(Text.translatable("text.oxidized.rose_gold_template_applies").formatted(Formatting.BLUE),
                Text.translatable("text.oxidized.rose_gold_template_ingredient").formatted(Formatting.BLUE),
                Text.translatable("text.oxidized.rose_gold_template").formatted(Formatting.GRAY),
                Text.translatable("text.oxidized.rose_gold_template.base_desc"),
                Text.translatable("text.oxidized.rose_gold_template.ing_desc"),
                List.of(new Identifier("item/empty_slot_ingot")), List.of(new Identifier("item/empty_slot_hoe"), new Identifier("item/empty_slot_axe"), new Identifier("item/empty_slot_sword"), new Identifier("item/empty_slot_shovel"), new Identifier("item/empty_slot_pickaxe")));
    }
}
