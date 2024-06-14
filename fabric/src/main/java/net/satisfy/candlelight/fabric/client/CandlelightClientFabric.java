package net.satisfy.candlelight.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.satisfy.candlelight.client.CandlelightClient;

public class CandlelightClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CandlelightClient.preInitClient();
        CandlelightClient.initClient();
    }
}
