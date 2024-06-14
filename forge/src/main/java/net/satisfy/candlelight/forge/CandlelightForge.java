package net.satisfy.candlelight.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.gui.ClothConfigScreen;
import net.satisfy.candlelight.forge.registry.CandlelightForgeVillagers;
import net.satisfy.candlelight.registry.CompostableRegistry;

@Mod(Candlelight.MOD_ID)
public class CandlelightForge {
    public CandlelightForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Candlelight.MOD_ID, modEventBus);
        Candlelight.init();
        CandlelightForgeVillagers.register(modEventBus);
        modEventBus.addListener(this::commonSetup);

        modEventBus.addListener(this::commonSetup);
        if (isClothConfigLoaded())
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> ClothConfigScreen.create(parent)));
    }

    public static boolean isClothConfigLoaded() {
        return ModList.get().isLoaded("cloth_config");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::init);
        Candlelight.commonInit();
    }
}
