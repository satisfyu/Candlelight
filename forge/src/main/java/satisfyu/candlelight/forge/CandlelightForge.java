package satisfyu.candlelight.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import satisfyu.candlelight.Candlelight;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import satisfyu.candlelight.registry.CompostableRegistry;
import satisfyu.candlelight.registry.ObjectRegistry;

@Mod(Candlelight.MOD_ID)
public class CandlelightForge {
    public CandlelightForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Candlelight.MOD_ID, modEventBus);
        Candlelight.init();
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::init);
        Candlelight.commonInit();
    }
}
