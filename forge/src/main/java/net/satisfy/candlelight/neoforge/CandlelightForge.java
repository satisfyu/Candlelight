package net.satisfy.candlelight.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.gui.ClothConfigScreen;
import net.satisfy.candlelight.neoforge.registry.CandlelightForgeVillagers;
import net.satisfy.candlelight.registry.CompostableRegistry;

@Mod(Candlelight.MOD_ID)
public class CandlelightForge {
    public CandlelightForge(IEventBus bus) {
        Candlelight.init();
        CandlelightForgeVillagers.register(bus);
        bus.addListener(this::commonSetup);

        bus.addListener(this::commonSetup);
        if (isClothConfigLoaded())
            ModLoadingContext.get().registerExtensionPoint(
                    IConfigScreenFactory.class,
                    () -> (minecraft, screen) -> ClothConfigScreen.create(screen)
            );
    }

    public static boolean isClothConfigLoaded() {
        return ModList.get().isLoaded("cloth_config");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::init);
        Candlelight.commonInit();
    }
}
