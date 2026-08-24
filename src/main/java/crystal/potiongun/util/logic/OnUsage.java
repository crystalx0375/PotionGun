package crystal.potiongun.util.logic;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import static crystal.potiongun.util.nbt.PotionGunState.getMagazine;
import static crystal.potiongun.util.nbt.PotionGunState.setAnimation;
import static crystal.potiongun.util.nbt.AddPotions.addPotionToNbt;
import static crystal.potiongun.util.nbt.AddPotions.findPotions;

public class OnUsage {
    public static void onUsage(@NotNull World world, LivingEntity user, PlayerEntity player, ItemStack stack, int remainingUseTicks) {
        final var registry = world.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        final int quickChargeLevel = EnchantmentHelper.getLevel(registry.getOrThrow(Enchantments.QUICK_CHARGE), stack);

        final int currentDel = 20 - (quickChargeLevel * 2);

        setAnimation(stack, (float) ((remainingUseTicks % currentDel) * -1) / currentDel);

        if ((remainingUseTicks - 1) % currentDel == 0) {
            final int magazine = getMagazine(stack);

            if (magazine < 4) {
                final ItemStack ammoStack = findPotions(player);
                if (player.getAbilities().creativeMode || !ammoStack.isEmpty()) {
                    final ItemStack potionToSave = ammoStack.copy();
                    potionToSave.setCount(1);

                    if (!world.isClient && !player.getAbilities().creativeMode) {
                        ammoStack.decrement(1);
                    }

                    addPotionToNbt(world, stack, potionToSave);

                    world.playSound(
                            null,
                            user.getX(), user.getY(), user.getZ(),
                            SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE,
                            SoundCategory.PLAYERS,
                            1.0f, 1.0f + (magazine * 0.1f)
                    );
                }
            }
        }
    }
}
