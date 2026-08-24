package crystal.potiongun.util;

import crystal.potiongun.register.CreatePotionGun;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class PotionGunSettings {
    public static final Item GUN = Registry.register(
            Registries.ITEM,
            crystal.potiongun.PotionGun.id("potiongun"),
            new CreatePotionGun(new Item.Settings().maxDamage(532)
                    .maxCount(1)
                    .fireproof()
            )
    );
}

