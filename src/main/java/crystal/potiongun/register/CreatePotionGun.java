package crystal.potiongun.register;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

import static crystal.potiongun.util.potion_gun.GetAllPotionsInInventory.getAllPotions;
import static crystal.potiongun.util.potion_gun.OnUsage.onUsage;
import static crystal.potiongun.util.potion_gun.ShootArrow.shoot;
import static crystal.potiongun.util.nbt.AddPotions.removePotionToNbt;
import static crystal.potiongun.util.nbt.PotionGunState.*;

public class CreatePotionGun extends Item {
    public CreatePotionGun(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        final ItemStack stack = user.getStackInHand(hand);
        final int ammo = getMagazine(stack);

        if (ammo > 0) {
            final ItemStack potionToShoot = removePotionToNbt(stack, world.getRegistryManager());

            setMagazine(stack, ammo - 1);
            shoot(world, user, stack, potionToShoot);
            user.getItemCooldownManager().set(stack.getItem(), 5);

            return TypedActionResult.consume(stack);
        }
        user.setCurrentHand(hand);
        return TypedActionResult.consume(stack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;
        onUsage(world, user, player, stack, remainingUseTicks);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        setAnimation(stack, 0);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient) return null;
        world.playSound(
                null,
                user.getX(), user.getY(), user.getZ(),
                SoundEvents.BLOCK_BREWING_STAND_BREW,
                SoundCategory.PLAYERS,
                0.6f, 0.9f
        );
        setAnimation(stack, 0);

        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        final List<ItemStack> list = getAllPotions(stack, context.getRegistryLookup());
        final int magazine = getMagazine(stack);
        if (!list.isEmpty()) {
            final int magazineIndex = magazine - 1;

            if (magazineIndex >= 0 && magazineIndex < list.size()) {
                final ItemStack projectileStack = list.get(magazineIndex);

                tooltip.add(Text.translatable("item.minecraft.crossbow.projectile")
                        .append(ScreenTexts.SPACE)
                        .append(projectileStack.toHoverableText()).formatted(Formatting.GRAY)
                        .append(ScreenTexts.SPACE)
                        .append((Text.literal(magazine + "/4")).formatted(Formatting.AQUA))
                );
            }
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
