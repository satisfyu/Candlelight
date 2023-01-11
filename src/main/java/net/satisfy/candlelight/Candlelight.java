package net.satisfy.candlelight;

import net.fabricmc.api.ModInitializer;
import net.satisfy.candlelight.registry.ObjectRegistry;
import net.satisfy.candlelight.registry.StorageTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Candlelight implements ModInitializer {
	public static final String MOD_ID = "candlelight";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ObjectRegistry.init();
		StorageTypes.init();
	}
}
