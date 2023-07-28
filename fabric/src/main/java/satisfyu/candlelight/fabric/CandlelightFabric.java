package satisfyu.candlelight.fabric;

import net.fabricmc.api.ModInitializer;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.fabric.world.CandlelightBiomeModification;
import satisfyu.candlelight.fabric.world.CompostableFabricRegistry;

public class CandlelightFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Candlelight.init();
        CandlelightBiomeModification.init();
        CompostableFabricRegistry.init();
    }
}
