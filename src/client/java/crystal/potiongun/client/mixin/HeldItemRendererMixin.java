package crystal.potiongun.client.mixin;

import crystal.potiongun.register.CreatePotionGun;
import crystal.potiongun.util.nbt.PotionGunState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(
            method = "renderFirstPersonItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    )
    private void rotateGunOnReload(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (item.getItem() instanceof CreatePotionGun && player.isUsingItem() && player.getActiveItem() == item) {
            float animationProgress = PotionGunState.getAnimation(item);
            if (animationProgress == 0.0F) {
                animationProgress = 0.01F;
            }

            if (animationProgress > 0) {
                final int cf = hand == Hand.MAIN_HAND ? 1 : -1;
                matrices.push();

                matrices.translate(-0.3F * cf, -0.08F, 0.1F);

                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-18F));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(20F * cf));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-5F * cf));
            }
        }
    }

    @ModifyVariable(method = "renderFirstPersonItem", at = @At("HEAD"), argsOnly = true)
    private ItemStack treatGunAsCrossbow(ItemStack item) { return item; }

    @Redirect(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;isCharged(Lnet/minecraft/item/ItemStack;)Z"))
    private boolean isCharged(ItemStack stack) {
        if (stack.getItem() instanceof CreatePotionGun) {
            final int magazine = PotionGunState.getMagazine(stack);
            final var client = MinecraftClient.getInstance();
            if (client.player != null && client.player.isUsingItem() && client.player.getActiveItem() == stack) {
                return magazine >= 6;
            }
            return magazine > 0;
        }
        return CrossbowItem.isCharged(stack);
    }

    @Redirect(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean isGun(ItemStack stack, Item item) {
        if (item == Items.CROSSBOW && stack.getItem() instanceof CreatePotionGun) {
            return true;
        }
        return stack.isOf(item);
    }
}
