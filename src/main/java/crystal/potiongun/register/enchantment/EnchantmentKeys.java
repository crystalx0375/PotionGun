package crystal.potiongun.register.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static crystal.potiongun.PotionGun.MOD_ID;

public class EnchantmentKeys {
    protected static final Map<RegistryKey<Enchantment>, Boolean> ENCHANTMENT_KEYS = new LinkedHashMap<>();

    public static final RegistryKey<Enchantment> CATALYST = register("catalyst");

    private static RegistryKey<Enchantment> register(String name) {
        RegistryKey<Enchantment> key = RegistryKey.of(
                RegistryKeys.ENCHANTMENT,
                Identifier.of(MOD_ID, name)
        );
        ENCHANTMENT_KEYS.put(key, true);
        return key;
    }
}
