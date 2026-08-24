package crystal.potiongun.client;

import crystal.potiongun.PotionGun;
import crystal.potiongun.util.PotionGunSettings;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;

public class Animation {
    private static final String ANIMATION_KEY = "animation";
    private static final String MAGAZINE_KEY = "magazine";


    public static void register() {
        ModelPredicateProviderRegistry.register(PotionGunSettings.GUN, PotionGun.id(ANIMATION_KEY), (stack, world, entity, seed) -> {
            final NbtComponent nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent != null) {
                final var nbt = nbtComponent.copyNbt();
                return nbt.getFloat(ANIMATION_KEY);
            }
            return 0.0F;
        });

        ModelPredicateProviderRegistry.register(PotionGunSettings.GUN, PotionGun.id("animation_with_arrow"), (stack, world, entity, seed) -> {
            final NbtComponent nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent != null) {
                final var nbt = nbtComponent.copyNbt();
                final int a = nbt.getInt(MAGAZINE_KEY);
                if (a > 0 && a < 6) {
                    return nbt.getFloat(ANIMATION_KEY);
                }
            }
            return 0.0F;
        });

        ModelPredicateProviderRegistry.register(PotionGunSettings.GUN, PotionGun.id("ammo"), (stack, world, entity, seed) -> {
            final NbtComponent nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent != null) {
                return (float) nbtComponent.copyNbt().getInt(MAGAZINE_KEY);
            }
            return 0.0F;
        });
    }
}
