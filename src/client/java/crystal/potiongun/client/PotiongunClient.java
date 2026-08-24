package crystal.potiongun.client;

import net.fabricmc.api.ClientModInitializer;

public class PotiongunClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        Animation.register();
	}
}