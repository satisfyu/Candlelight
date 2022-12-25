package net.satisfy.candlelight;

import net.fabricmc.api.ModInitializer;
import net.satisfy.candlelight.registry.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Candlelight implements ModInitializer {
	public static final String MOD_ID = "candlelight_dinner";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Objects.register();
		LOGGER.info("Hello Fabric world!");
	}
}
