package crystal.potiongun.util.logic;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ShrapnelPotionEntity extends PotionEntity {

    private final int shrapnelLevel;

    public ShrapnelPotionEntity(
            EntityType<? extends PotionEntity> entityType,
            World world,
            int shrapnelLevel
    ) {
        super(entityType, world);
        this.shrapnelLevel = shrapnelLevel;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (getWorld().isClient || shrapnelLevel <= 0) {
            return;
        }

        float damage = shrapnelLevel + 1.0F;

        double radius = 2;

        for (LivingEntity entity : getWorld().getEntitiesByClass(
                LivingEntity.class,
                getBoundingBox().expand(radius),
                entity -> entity != getOwner()
        )) {
            entity.damage(
                    getWorld().getDamageSources().generic(),
                    damage
            );
        }
    }
}