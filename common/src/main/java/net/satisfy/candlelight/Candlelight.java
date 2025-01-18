package net.satisfy.candlelight;

import net.satisfy.candlelight.core.registry.*;
import net.satisfy.candlelight.core.event.CommonEvents;
import net.satisfy.candlelight.core.networking.CandlelightMessages;

public class Candlelight {
    public static final String MOD_ID = "candlelight";

    public static void init() {
        ObjectRegistry.init();
        ScreenHandlerTypeRegistry.init();
        EntityTypeRegistry.init();
        CommonEvents.init();
        TabRegistry.init();
        CandlelightMessages.registerC2SPackets();
    }

    public static void commonInit() {
        FlammableBlockRegistry.init();
    }
}
