package satisfy.candlelight;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfy.candlelight.config.CandlelightConfig;
import satisfy.candlelight.event.CommonEvents;
import satisfy.candlelight.networking.CandlelightMessages;
import satisfy.candlelight.registry.*;

public class Candlelight {
    public static final String MOD_ID = "candlelight";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static void init() {
        CandlelightConfig.getActiveInstance().getConfig();
        DataFixerRegistry.init();
        ObjectRegistry.init();
        ScreenHandlerTypeRegistry.init();
        BlockEntityRegistry.init();
        CommonEvents.init();
        TabRegistry.init();
        CandlelightMessages.registerC2SPackets();
    }

    public static void commonInit() {
        FlammableBlockRegistry.init();
    }
}
