package satisfyu.candlelight.fabric;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import satisfyu.candlelight.Candlelight;
import net.fabricmc.api.ModInitializer;
import satisfyu.candlelight.fabric.registry.CandlelightPoiTypesFabric;
import satisfyu.candlelight.fabric.world.CandlelightBiomeModification;
import satisfyu.candlelight.util.CandlelightIdentifier;

public class CandlelightFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Candlelight.init();
        CandlelightPoiTypesFabric.loadClass();
        CandlelightBiomeModification.init();
        FabricLoader.getInstance().getModContainer(Candlelight.MOD_ID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new CandlelightIdentifier("apple_leaves"), container, ResourcePackActivationType.DEFAULT_ENABLED);
        });
    }
}
