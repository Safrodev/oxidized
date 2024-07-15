package safro.oxidized.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import safro.oxidized.entity.CopperGolemEntity;

public class EntityRegistry {
    public static final EntityType<CopperGolemEntity> COPPER_GOLEM = EntityType.Builder.create(CopperGolemEntity::new, SpawnGroup.MISC)
            .dimensions(0.5F, 0.9F).makeFireImmune().maxTrackingRange(10).build();

    public static void init() {
        Registry.register(Registries.ENTITY_TYPE, Identifier.of("oxidized", "copper_golem"), COPPER_GOLEM);

        // Attributes
        FabricDefaultAttributeRegistry.register(COPPER_GOLEM, CopperGolemEntity.createCopperGolemAttributes());
    }
}
