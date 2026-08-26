package crystal.potiongun.register.enchantment;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static crystal.potiongun.register.CustomTag.BOW;
import static crystal.potiongun.register.CustomTag.CROSSBOWS;


public class EnchantmentGenerator extends FabricDynamicRegistryProvider {

    public EnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries e) {
        final var i = registries.getWrapperOrThrow(RegistryKeys.ITEM);
        RegisterEnchantments.catalyst(i, e);
        RegisterEnchantments.shrapnel(i, e);
        final var enchRegistry = registries.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        RegisterEnchantments.quickShot(i, enchRegistry, e);
        RegisterEnchantments.magazineExpansion(i, enchRegistry, e);

        modify(e, Enchantments.POWER, Enchantment.definition(
                i.getOrThrow(BOW),
                1,
                5,
                Enchantment.constantCost(20),
                Enchantment.constantCost(50),
                4,
                AttributeModifierSlot.MAINHAND
        ));
        modify(e, Enchantments.QUICK_CHARGE, Enchantment.definition(
                i.getOrThrow(CROSSBOWS),
                1,
                3,
                Enchantment.constantCost(20),
                Enchantment.constantCost(50),
                4,
                AttributeModifierSlot.MAINHAND
        ));
    }

    private void modify(Entries entries, RegistryKey<Enchantment> key, Enchantment.Definition definition) {
        entries.add(key, Enchantment.builder(definition).build(key.getValue()));
    }

    @Override
    public String getName() {
        return "Enchantments";
    }
}