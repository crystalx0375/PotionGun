package crystal.potiongun.register.enchantment;

import crystal.potiongun.register.CustomTag;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.registry.tag.TagKey;

public class RegisterEnchantments {
    private RegisterEnchantments() {}

    protected static void catalyst(RegistryWrapper<Item> itemRegistry, FabricDynamicRegistryProvider.Entries entries) {
        if (EnchantmentKeys.ENCHANTMENT_KEYS.containsKey(EnchantmentKeys.CATALYST)) {
            final TagKey<Item> itemTagKey = CustomTag.POTIONGUN_COMPATIBLE;
            entries.add(EnchantmentKeys.CATALYST, Enchantment.builder(
                    Enchantment.definition(itemRegistry.getOrThrow(itemTagKey), itemRegistry.getOrThrow(itemTagKey), 2, 4, Enchantment.leveledCost(10, 8), Enchantment.leveledCost(20, 8), 3, AttributeModifierSlot.MAINHAND))
                    .build(EnchantmentKeys.CATALYST.getValue()));
        }
    }

    protected static void shrapnel(RegistryWrapper<Item> itemRegistry, FabricDynamicRegistryProvider.Entries entries) {
        if (EnchantmentKeys.ENCHANTMENT_KEYS.containsKey(EnchantmentKeys.SHRAPNEL)) {
            final TagKey<Item> itemTagKey = CustomTag.POTIONGUN_COMPATIBLE;
            entries.add(EnchantmentKeys.SHRAPNEL, Enchantment.builder(
                    Enchantment.definition(itemRegistry.getOrThrow(itemTagKey), itemRegistry.getOrThrow(itemTagKey), 2, 3, Enchantment.leveledCost(10, 8), Enchantment.leveledCost(20, 8), 2, AttributeModifierSlot.MAINHAND))
                    .build(EnchantmentKeys.SHRAPNEL.getValue()));
        }
    }

    protected static void quickShot(RegistryWrapper<Item> itemRegistry, RegistryWrapper<Enchantment> enchRegistry, FabricDynamicRegistryProvider.Entries entries) {
        if (EnchantmentKeys.ENCHANTMENT_KEYS.containsKey(EnchantmentKeys.QUICK_SHOT)) {
            final TagKey<Item> itemTagKey = CustomTag.POTIONGUN_COMPATIBLE;
            entries.add(EnchantmentKeys.QUICK_SHOT, Enchantment.builder(
                    Enchantment.definition(itemRegistry.getOrThrow(itemTagKey), 5, 5, Enchantment.leveledCost(10, 8), Enchantment.leveledCost(20, 8), 4, AttributeModifierSlot.MAINHAND))
                    .exclusiveSet(enchRegistry.getOrThrow(TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("potiongun", "incompatible_with_others"))))
                    .build(EnchantmentKeys.QUICK_SHOT.getValue()));
        }
    }

    protected static void magazineExpansion(RegistryWrapper<Item> itemRegistry, RegistryWrapper<Enchantment> enchRegistry, FabricDynamicRegistryProvider.Entries entries) {
        if (EnchantmentKeys.ENCHANTMENT_KEYS.containsKey(EnchantmentKeys.MAGAZINE_EXPANSION)) {
            final TagKey<Item> itemTagKey = CustomTag.POTIONGUN_COMPATIBLE;
            entries.add(EnchantmentKeys.MAGAZINE_EXPANSION, Enchantment.builder(
                    Enchantment.definition(itemRegistry.getOrThrow(itemTagKey), 2, 2, Enchantment.leveledCost(10, 8), Enchantment.leveledCost(20, 8), 4, AttributeModifierSlot.MAINHAND))
                    .exclusiveSet(enchRegistry.getOrThrow(TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("potiongun", "incompatible_with_others"))))
                    .build(EnchantmentKeys.MAGAZINE_EXPANSION.getValue()));
    
        }
    }
}
