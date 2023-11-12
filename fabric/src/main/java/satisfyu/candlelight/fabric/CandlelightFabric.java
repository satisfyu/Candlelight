package satisfyu.candlelight.fabric;

import net.fabricmc.api.ModInitializer;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.fabric.world.CandlelightBiomeModification;
import satisfyu.candlelight.registry.CompostableRegistry;
import satisfyu.candlelight.registry.ObjectRegistry;

public class CandlelightFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Candlelight.init();
        CompostableRegistry.init();
        CandlelightBiomeModification.init();
    }
}
