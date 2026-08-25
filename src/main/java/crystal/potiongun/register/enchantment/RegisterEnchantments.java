package crystal.potiongun.register.enchantment;

import crystal.potiongun.register.CustomTag;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

public class RegisterEnchantments {
    protected static void Catalyst(RegistryWrapper<Item> itemRegistry, FabricDynamicRegistryProvider.Entries entries) {
        if (EnchantmentKeys.ENCHANTMENT_KEYS.containsKey(EnchantmentKeys.CATALYST)) {
            final TagKey<Item> itemTagKey = CustomTag.POTIONGUN_COMPATIBLE;

            entries.add(EnchantmentKeys.CATALYST, Enchantment.builder(
                            Enchantment.definition(
                                    itemRegistry.getOrThrow(itemTagKey),
                                    itemRegistry.getOrThrow(itemTagKey),
                                    2,
                                    4,
                                    Enchantment.leveledCost(10, 8),
                                    Enchantment.leveledCost(20, 8),
                                    3,
                                    AttributeModifierSlot.MAINHAND
                            )
                    )
                    .build(EnchantmentKeys.CATALYST.getValue()));
        }
    }

    protected static void Shrapnel(RegistryWrapper<Item> itemRegistry, FabricDynamicRegistryProvider.Entries entries) {
        if (EnchantmentKeys.ENCHANTMENT_KEYS.containsKey(EnchantmentKeys.SHRAPNEL)) {
            final TagKey<Item> itemTagKey = CustomTag.POTIONGUN_COMPATIBLE;

            entries.add(EnchantmentKeys.SHRAPNEL, Enchantment.builder(
                            Enchantment.definition(
                                    itemRegistry.getOrThrow(itemTagKey),
                                    itemRegistry.getOrThrow(itemTagKey),
                                    2,
                                    3,
                                    Enchantment.leveledCost(10, 8),
                                    Enchantment.leveledCost(20, 8),
                                    2,
                                    AttributeModifierSlot.MAINHAND
                            )
                    )
                    .build(EnchantmentKeys.SHRAPNEL.getValue()));
        }
    }
}
