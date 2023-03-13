package net.satisfy.candlelight;

import net.fabricmc.api.ModInitializer;

import net.satisfy.candlelight.networking.CandlelightMessages;
import net.satisfy.candlelight.registry.*;
import net.satisfy.candlelight.villager.memory.ModMemoryModuleType;
import net.satisfy.candlelight.villager.poi.ModPointOfInterestTypes;
import net.satisfy.candlelight.villager.task.ModTaskListProvider;
import net.satisfy.candlelight.world.feature.ConfiguredFeatures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Candlelight implements ModInitializer {
	public static final String MOD_ID = "candlelight";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		RecipeTypes.init();
		ObjectRegistry.init();
		StorageTypes.init();

		ModBlockEntityTypes.init();

		ModMemoryModuleType.init();//TODO rename and make init
		ModPointOfInterestTypes.init();
		ModTaskListProvider.init();

		CandlelightTabbedItemGroup.init();

		ScreenHandlerTypes.init();
		ConfiguredFeatures.init();

		CandlelightMessages.registerC2SPackets();

	}
}
