package safro.oxidized.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import safro.oxidized.Oxidized;
import safro.oxidized.config.OxidizedConfig;
import safro.oxidized.entity.CopperGolemEntity;

public class EntityRegistry {
    public static final EntityType<CopperGolemEntity> COPPER_GOLEM = FabricEntityTypeBuilder.<CopperGolemEntity>create(SpawnGroup.MISC, CopperGolemEntity::new)
            .dimensions(EntityDimensions.fixed(0.5F, 0.9F)).fireImmune().trackRangeBlocks(8).build();

    public static void init() {
        if (Oxidized.CONFIG.enable_copper_golem) {
            Registry.register(Registry.ENTITY_TYPE, new Identifier("oxidized", "copper_golem"), COPPER_GOLEM);

            // Attributes
            FabricDefaultAttributeRegistry.register(COPPER_GOLEM, CopperGolemEntity.createCopperGolemAttributes());
        }
    }
}
