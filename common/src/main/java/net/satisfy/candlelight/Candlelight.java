package net.satisfy.candlelight;

import net.satisfy.candlelight.networking.CandlelightMessages;
import net.satisfy.candlelight.registry.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.satisfy.candlelight.config.CandlelightConfig;
import net.satisfy.candlelight.event.CommonEvents;

public class Candlelight {
    public static final String MOD_ID = "candlelight";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


    public static void init() {
        CandlelightConfig.getActiveInstance().getConfig();
        RecipeTypeRegistry.init();
        ObjectRegistry.init();
        ScreenHandlerTypeRegistry.init();
        BlockEntityRegistry.init();
        CommonEvents.init();
        TabRegistry.init();
        CandlelightMessages.registerC2SPackets();
    }

    public static void commonInit(){
        FlammableBlockRegistry.init();
    }
}
