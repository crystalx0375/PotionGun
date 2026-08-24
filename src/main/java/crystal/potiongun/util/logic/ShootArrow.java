package crystal.potiongun.util.logic;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ShootArrow {
    public static void shoot(final World world, final LivingEntity shooter, final ItemStack crossbow, final ItemStack potionStack) {
        if (world.isClient) return;

        final PotionEntity potion = createPotion(world);
        potion.setItem(potionStack);

        final var power = world.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.POWER);
        final int level = EnchantmentHelper.getLevel(power, crossbow);

        world.playSound(
                null,
                shooter.getX(), shooter.getY(), shooter.getZ(),
                SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS,
                1.0F,
                1.0F
        );

        setVelocity(shooter, potion, level);
        crossbow.damage(1, shooter, shooter.getPreferredEquipmentSlot(crossbow));

        world.spawnEntity(potion);
    }

    private static PotionEntity createPotion(World world) {
        return new PotionEntity(
                EntityType.POTION,
                world
        );
    }

    private static void setVelocity(final LivingEntity entity, final PotionEntity potion, final int level) {
        potion.setPosition(entity.getX(), entity.getEyeY() - 0.15, entity.getZ());
        final Vec3d lookVec = entity.getRotationVector();
        potion.setVelocity(lookVec.x, lookVec.y, lookVec.z, 1.25F + (level * 0.15F), 1);
    }
}
