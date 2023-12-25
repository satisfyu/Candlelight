package satisfy.candlelight.fabric;

import net.fabricmc.api.ModInitializer;
import satisfy.candlelight.Candlelight;
import satisfy.candlelight.fabric.registry.VillagersFabric;
import satisfy.candlelight.fabric.world.CandlelightBiomeModification;
import satisfy.candlelight.registry.CompostableRegistry;

public class CandlelightFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Candlelight.init();
        CompostableRegistry.init();
        CandlelightBiomeModification.init();
        VillagersFabric.init();
    }
}
