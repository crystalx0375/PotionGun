package crystal.potiongun.register;

import crystal.potiongun.util.PotionGunSettings;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static crystal.potiongun.PotionGun.MOD_ID;

public class CustomTag extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> POTIONGUN_COMPATIBLE = TagKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "enchantable/potiongun_compatible"));

    public static final TagKey<Item> CROSSBOWS = TagKey.of(
            RegistryKeys.ITEM,
            Identifier.of("potiongun", "enchantable/crossbows")
    );

    public static final TagKey<Item> BOW = TagKey.of(
            RegistryKeys.ITEM,
            Identifier.of("potiongun", "enchantable/bow")
    );

    public CustomTag(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, @Nullable BlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @SuppressWarnings("java:S1192")
    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(CROSSBOWS).add(Items.CROSSBOW);
        getOrCreateTagBuilder(BOW).add(Items.BOW);

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("minecraft", "enchantable/crossbow"))).add(PotionGunSettings.GUN);
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("minecraft", "enchantable/bow"))).add(PotionGunSettings.GUN);
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("minecraft", "enchantable/durability"))).add(PotionGunSettings.GUN);

        getOrCreateTagBuilder(POTIONGUN_COMPATIBLE).add(PotionGunSettings.GUN);
    }
}
