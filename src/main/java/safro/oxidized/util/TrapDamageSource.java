package safro.oxidized.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class TrapDamageSource {
    public static final RegistryKey<DamageType> TRAP = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("oxidized", "trap"));

    public static DamageSource create(World world) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(TRAP));
    }
}
