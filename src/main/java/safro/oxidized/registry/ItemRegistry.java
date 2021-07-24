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

    public static final Item COPPER_PULSAR = new CopperPulsarItem(new FabricItemSettings().group(Oxidized.ITEMGROUP));
    public static final Item COPPER_NUGGET = new Item(new FabricItemSettings().group(Oxidized.ITEMGROUP));

    public static final ToolItem ROSE_GOLD_SHOVEL = new ShovelItem(RoseGoldMaterial.ROSE_GOLD, 1.5F, -3.0F, new FabricItemSettings().group(Oxidized.ITEMGROUP));
    public static final ToolItem ROSE_GOLD_SWORD = new SwordItem(RoseGoldMaterial.ROSE_GOLD, 4, -2.4F, new FabricItemSettings().group(Oxidized.ITEMGROUP));
    public static final ToolItem ROSE_GOLD_PICKAXE = new BasicPickaxeItem(RoseGoldMaterial.ROSE_GOLD, 1, -2.8F, new FabricItemSettings().group(Oxidized.ITEMGROUP));
    public static final ToolItem ROSE_GOLD_AXE = new BasicAxeItem(RoseGoldMaterial.ROSE_GOLD, 6.0F, -3.0F, new FabricItemSettings().group(Oxidized.ITEMGROUP));
    public static final ToolItem ROSE_GOLD_HOE = new BasicHoeItem(RoseGoldMaterial.ROSE_GOLD, 0, -3.0F, new FabricItemSettings().group(Oxidized.ITEMGROUP));

    public static void init() {

        Registry.register(Registry.ITEM, new Identifier("oxidized", "copper_pulsar"), COPPER_PULSAR);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "copper_nugget"), COPPER_NUGGET);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "rose_gold_axe"), ROSE_GOLD_AXE);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "rose_gold_hoe"), ROSE_GOLD_HOE);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "rose_gold_pickaxe"), ROSE_GOLD_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "rose_gold_shovel"), ROSE_GOLD_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier("oxidized", "rose_gold_sword"), ROSE_GOLD_SWORD);
    }
}
