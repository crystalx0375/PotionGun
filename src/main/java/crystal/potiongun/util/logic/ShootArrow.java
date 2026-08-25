package crystal.potiongun.util.logic;

import crystal.potiongun.register.enchantment.EnchantmentKeys;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ShootArrow {

    public static void shoot(
            final World world,
            final LivingEntity shooter,
            final ItemStack crossbow,
            final ItemStack potionStack
    ) {
        if (world.isClient) return;

        final var enchantmentRegistry =
                world.getRegistryManager()
                        .getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

        final int catalystLevel =
                EnchantmentHelper.getLevel(
                        enchantmentRegistry.getOrThrow(EnchantmentKeys.CATALYST),
                        crossbow
                );

        final int shrapnelLevel =
                EnchantmentHelper.getLevel(
                        enchantmentRegistry.getOrThrow(EnchantmentKeys.SHRAPNEL),
                        crossbow
                );

        final ItemStack modifiedPotion = potionStack.copy();

        if (catalystLevel > 0) {
            extendPotionEffects(modifiedPotion, catalystLevel);
        }



        final PotionEntity potion = createPotion(world, shrapnelLevel);
        potion.setItem(modifiedPotion);

        final var power =
                enchantmentRegistry.getOrThrow(Enchantments.POWER);

        final int level =
                EnchantmentHelper.getLevel(power, crossbow);

        world.playSound(
                null,
                shooter.getX(),
                shooter.getY(),
                shooter.getZ(),
                SoundEvents.ENTITY_SPLASH_POTION_THROW,
                SoundCategory.PLAYERS,
                0.7F,
                0.9F
        );

        setVelocity(shooter, potion, level);

        crossbow.damage(
                1,
                shooter,
                shooter.getPreferredEquipmentSlot(crossbow)
        );

        world.spawnEntity(potion);
    }


    private static void extendPotionEffects(
            final ItemStack potionStack,
            final int catalystLevel
    ) {
        final PotionContentsComponent contents =
                potionStack.get(DataComponentTypes.POTION_CONTENTS);

        if (contents == null) return;

        final int extraDuration = catalystLevel * 20;

        final List<StatusEffectInstance> effects = new ArrayList<>();

        for (StatusEffectInstance effect : contents.getEffects()) {

            effects.add(
                    new StatusEffectInstance(
                            effect.getEffectType(),
                            effect.getDuration() + extraDuration,
                            effect.getAmplifier(),
                            effect.isAmbient(),
                            effect.shouldShowParticles(),
                            effect.shouldShowIcon()
                    )
            );
        }

        final PotionContentsComponent newContents =
                new PotionContentsComponent(
                        java.util.Optional.empty(),
                        contents.customColor(),
                        effects
                );

        potionStack.set(
                DataComponentTypes.POTION_CONTENTS,
                newContents
        );
    }


    private static PotionEntity createPotion(World world, int shrapnelLevel) {
        return new ShrapnelPotionEntity(
                EntityType.POTION,
                world,
                shrapnelLevel
        );
    }


    private static void setVelocity(
            final LivingEntity entity,
            final PotionEntity potion,
            final int level
    ) {
        potion.setPosition(
                entity.getX(),
                entity.getEyeY() - 0.15,
                entity.getZ()
        );

        final Vec3d lookVec = entity.getRotationVector();

        potion.setVelocity(
                lookVec.x,
                lookVec.y,
                lookVec.z,
                1.25F + (level * 0.15F),
                1
        );
    }
}