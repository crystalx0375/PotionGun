package crystal.potiongun.util.potion;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings({"java:S110", "java:S2160"})
public class ShrapnelPotionEntity extends PotionEntity {
    private final int shrapnelLevel;

    public ShrapnelPotionEntity(EntityType<? extends PotionEntity> entityType, World world, final int shrapnelLevel) {
        super(entityType, world);
        this.shrapnelLevel = shrapnelLevel;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (getWorld().isClient) return;
        super.onCollision(hitResult);

        for (LivingEntity entity : getAllEntities()) {
            entity.damage(
                    getWorld().getDamageSources().generic(),
                    shrapnelLevel + 1F - (float) (hitResult.getPos().squaredDistanceTo(entity.getPos()) / 2)
            );
        }
    }

    private List<LivingEntity> getAllEntities() {
        return getWorld().getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(1.5), entity -> entity != getOwner());
    }
}