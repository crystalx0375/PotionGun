package crystal.potiongun.util.potion;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PotionLogic {
    public static void prepareAndSpawnPotion(final World world, final LivingEntity entity, final PotionEntity potion, final int powerLevel) {
        final Vec3d lookVec = entity.getRotationVector();

        potion.setPosition(
                entity.getX(),
                entity.getEyeY() - 0.15,
                entity.getZ()
        );
        potion.setVelocity(
                lookVec.x,
                lookVec.y,
                lookVec.z,
                1.25F + (powerLevel * 0.15F),
                1
        );

        world.spawnEntity(potion);
    }

    public static void spawnPotionAndPrepare(final World world, final LivingEntity shooter, final ItemStack potionStack, final int powerLevel) {
        final PotionEntity potion = new PotionEntity(
                EntityType.POTION,
                world
        );
        potion.setItem(potionStack);

        prepareAndSpawnPotion(world, shooter, potion, powerLevel);
    }

    public static void spawnPotionAndPrepare(final World world, final LivingEntity shooter, final ItemStack potionStack, final int shrapnelLevel, final int powerLevel) {
        final PotionEntity potion = new ShrapnelPotionEntity(
                EntityType.POTION,
                world,
                shrapnelLevel
        );
        potion.setItem(potionStack);

        prepareAndSpawnPotion(world, shooter, potion, powerLevel);
    }

    public static void setExtraDuration(final ItemStack potionStack, final int catalystLevel) {
        final PotionContentsComponent contents = potionStack.get(DataComponentTypes.POTION_CONTENTS);
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
        final PotionContentsComponent newContents = new PotionContentsComponent(Optional.empty(), contents.customColor(), effects);

        potionStack.set(DataComponentTypes.POTION_CONTENTS, newContents);
    }
}
