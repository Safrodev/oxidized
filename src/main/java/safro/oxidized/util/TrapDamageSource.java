package safro.oxidized.util;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import safro.saflib.util.DamageSourceUtil;

public class TrapDamageSource {
    public static final RegistryKey<DamageType> TRAP = DamageSourceUtil.register("oxidized", "trap");

    public static void init() {}
}
