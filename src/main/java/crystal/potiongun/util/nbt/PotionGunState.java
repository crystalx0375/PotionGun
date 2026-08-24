package crystal.potiongun.util.nbt;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;


public class PotionGunState {
    public static void setAnimation(final ItemStack stack, final float amount) {
        final NbtComponent currentComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        final NbtCompound nbt = currentComponent.copyNbt();
        nbt.putFloat("animation", amount);
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }

    public static void setMagazine(final ItemStack stack, final int ammo) {
        final NbtComponent currentComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        final NbtCompound nbt = currentComponent.copyNbt();
        nbt.putInt("magazine", ammo);
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }

    public static float getAnimation(final ItemStack stack) {
        final NbtComponent currentComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        final NbtCompound nbt = currentComponent.copyNbt();
        return nbt.getFloat("animation");
    }

    public static int getMagazine(final ItemStack stack) {
        final NbtComponent nbt = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (nbt != null) {
            return nbt.copyNbt().getInt("magazine");
        }
        return 0;
    }
}
