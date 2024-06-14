package net.satisfy.candlelight.fabric;

import net.fabricmc.api.ModInitializer;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.fabric.registry.VillagersFabric;
import net.satisfy.candlelight.fabric.world.CandlelightBiomeModification;
import net.satisfy.candlelight.registry.CompostableRegistry;

public class CandlelightFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Candlelight.init();
        CompostableRegistry.init();
        CandlelightBiomeModification.init();
        VillagersFabric.init();
    }
}
