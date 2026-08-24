package crystal.potiongun.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {
    @Inject(method = "onEntityHit", at = @At("TAIL"))
    private void apply(EntityHitResult entityHitResult, CallbackInfo ci) {
        final PersistentProjectileEntity projectile = (PersistentProjectileEntity) (Object) this;

        if (entityHitResult.getEntity() instanceof LivingEntity target) {
            for (String tag : projectile.getCommandTags()) {
                if (tag.startsWith("frost_1")) {
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30, 1));
                }
                if (tag.startsWith("wither_1")) {
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 60, 0));
                }
                if (tag.startsWith("punch_")) {
                    final Vec3d velocity = projectile.getVelocity().multiply(1.0, 0.0, 1.0).normalize();
                    if (velocity.length() > 0.0) {
                        target.addVelocity(
                                velocity.x * 0.3F,
                                0.05,
                                velocity.z * 0.3F
                        );
                        target.velocityDirty = true;
                    }
                }
            }
        }
    }
}
