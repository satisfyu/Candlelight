package net.satisfy.candlelight.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.CandlelightClient;

@Mod.EventBusSubscriber(modid = Candlelight.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CandlelightClientForge {
    @SubscribeEvent
    public static void beforeClientSetup(RegisterEvent event) {
        CandlelightClient.preInitClient();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        CandlelightClient.initClient();
    }
}
