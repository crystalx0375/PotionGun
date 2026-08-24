package crystal.potiongun.register.enchantment;

import crystal.potiongun.register.CustomTag;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;


public class EnchantmentGenerator extends FabricDynamicRegistryProvider {

    public EnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries e) {
        final var i = registries.getWrapperOrThrow(RegistryKeys.ITEM);
        RegisterEnchantments.Catalyst(i, e);

        final var crossbowsTag = i.getOrThrow(CustomTag.CROSSBOWS);
        modify(e, Enchantments.MULTISHOT, Enchantment.definition(
                crossbowsTag,
                1,
                1,
                Enchantment.constantCost(20),
                Enchantment.constantCost(50),
                4,
                AttributeModifierSlot.MAINHAND
        ));
        final var bowTag = i.getOrThrow(CustomTag.BOW);
        modify(e, Enchantments.PUNCH, Enchantment.definition(
                bowTag,
                1,
                1,
                Enchantment.constantCost(20),
                Enchantment.constantCost(50),
                4,
                AttributeModifierSlot.MAINHAND
        ));
        modify(e, Enchantments.FLAME, Enchantment.definition(
                bowTag,
                1,
                1,
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