package crystal.potiongun.util.potion_gun;

import com.google.common.collect.Lists;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;

public class GetAllPotionsInInventory {
    private GetAllPotionsInInventory() {}

    public static List<ItemStack> getAllPotions(final ItemStack stack, final RegistryWrapper.WrapperLookup registries) {
        final List<ItemStack> list = Lists.newArrayList();
        final NbtComponent nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (nbtComponent == null) {
            return list;
        }

        final NbtCompound nbt = nbtComponent.copyNbt();
        if (nbt.contains("potions", 9)) {
            final NbtList nbtList = nbt.getList("potions", 10);
            for (int i = 0; i < nbtList.size(); ++i) {
                final NbtCompound potionNbt = nbtList.getCompound(i);
                final ItemStack potionStack = ItemStack.fromNbtOrEmpty(registries, potionNbt);

                if (!potionStack.isEmpty()) {
                    list.add(potionStack);
                }
            }
        }
        return list;
    }
}
