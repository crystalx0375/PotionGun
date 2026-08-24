package crystal.potiongun.client.mixin;

import crystal.potiongun.register.CreatePotionGun;
import crystal.potiongun.util.nbt.PotionGunState;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin<T extends LivingEntity> {
    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
    private void force3Pos(T entity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (entity instanceof PlayerEntity player) {
            final ItemStack stack = player.getMainHandStack();
            if (stack.getItem() instanceof CreatePotionGun) {

                if (player.isUsingItem() && player.getActiveItem() == stack) {
                    if (player.getMainArm() == Arm.RIGHT) {
                        ((BipedEntityModel<?>)(Object)this).rightArmPose = BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
                    } else {
                        ((BipedEntityModel<?>)(Object)this).leftArmPose = BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
                    }
                }

                if (PotionGunState.getMagazine(stack) > 0) {
                    if (player.getMainArm() == Arm.RIGHT) {
                        ((BipedEntityModel<?>)(Object)this).rightArmPose = BipedEntityModel.ArmPose.CROSSBOW_HOLD;
                    } else {
                        ((BipedEntityModel<?>)(Object)this).leftArmPose = BipedEntityModel.ArmPose.CROSSBOW_HOLD;
                    }
                }
            }
        }
    }
}