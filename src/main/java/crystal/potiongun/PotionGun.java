package crystal.potiongun;

import crystal.potiongun.util.PotionGunSettings;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PotionGun implements ModInitializer {
	public static final String MOD_ID = "potiongun";
	public static final Logger LOGGER = LoggerFactory.getLogger("Potion Gun");

	@Override
	public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(itemGroup -> itemGroup.add(PotionGunSettings.GUN));
		LOGGER.info("Potion Gun successful initialized");
    }

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
