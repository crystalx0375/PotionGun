package crystal.potiongun.util.potion_gun;

import crystal.potiongun.register.enchantment.EnchantmentKeys;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import static crystal.potiongun.util.potion.PotionLogic.*;

public class ShootArrow {
    private ShootArrow() {}

    public static void shoot(final World world, final LivingEntity shooter, final ItemStack potionGun, final ItemStack potionStack) {
        potionGun.damage(3, shooter, shooter.getPreferredEquipmentSlot(potionGun));

        final var registry = world.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

        final int catalystLevel = EnchantmentHelper.getLevel(registry.getOrThrow(EnchantmentKeys.CATALYST), potionGun);
        final int shrapnelLevel = EnchantmentHelper.getLevel(registry.getOrThrow(EnchantmentKeys.SHRAPNEL), potionGun);
        final int powerLevel = EnchantmentHelper.getLevel(registry.getOrThrow(Enchantments.POWER), potionGun);

        if (catalystLevel > 0) setExtraDuration(potionStack, catalystLevel);
        if (shrapnelLevel > 0) createAndSpawnPotion(world, shooter, potionStack, shrapnelLevel, powerLevel);
        else createAndSpawnPotion(world, shooter, potionStack, powerLevel);

        world.playSound(
                null,
                shooter.getX(), shooter.getY(), shooter.getZ(),
                SoundEvents.ENTITY_SPLASH_POTION_THROW,
                SoundCategory.PLAYERS,
                0.8F, 0.9F
        );
        world.playSound(
                null,
                shooter.getX(), shooter.getY(), shooter.getZ(),
                SoundEvents.ITEM_CROSSBOW_SHOOT,
                SoundCategory.PLAYERS,
                0.2F, 1F
        );
    }
}