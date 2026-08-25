package crystal.potiongun.util.nbt;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

@SuppressWarnings("java:S1192")
public class AddPotions {
    private AddPotions() {}

    public static void addPotionToNbt(final World world, final ItemStack crossbow, final ItemStack potion) {
        final var registries = world.getRegistryManager();
        if (potion.isEmpty()) return;

        crossbow.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, nbtComponent ->
                nbtComponent.apply(nbt -> {
                    final NbtList list = nbt.getList("potions", NbtElement.COMPOUND_TYPE);
                    final NbtElement potionNbt = potion.encode(registries);
                    list.addFirst(potionNbt);

                    nbt.put("potions", list);
                    nbt.putInt("magazine", list.size());
                    nbt.putBoolean("charged", true);
                })
        );
    }

    public static ItemStack removePotionToNbt(final ItemStack stack, RegistryWrapper.WrapperLookup registries) {
        final ItemStack[] result = {new ItemStack(Items.POTION)};

        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp ->
                comp.apply(nbt -> {
                    final NbtList list = nbt.getList("potions", NbtElement.COMPOUND_TYPE);
                    if (!list.isEmpty()) {
                        final NbtCompound potionNbt = list.getCompound(list.size() - 1);
                        result[0] = ItemStack.fromNbtOrEmpty(registries, potionNbt);
                        list.removeLast();

                        nbt.putInt("magazine", list.size());
                        nbt.putBoolean("charged", true);
                    }
                })
        );
        return result[0];
    }

    public static ItemStack findPotions(final PlayerEntity player) {
        if (isThrowablePotion(player.getOffHandStack())) return player.getOffHandStack();
        if (isThrowablePotion(player.getMainHandStack())) return player.getMainHandStack();

        for (int i = 0; i < player.getInventory().size(); ++i) {
            final ItemStack itemStack = player.getInventory().getStack(i);
            if (isThrowablePotion(itemStack)) return itemStack;
        }

        return ItemStack.EMPTY;
    }

    private static boolean isThrowablePotion(final ItemStack stack) {
        return stack.isOf(Items.SPLASH_POTION) || stack.isOf(Items.LINGERING_POTION);
    }
}
