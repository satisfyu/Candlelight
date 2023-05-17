package satisfyu.candlelight.client;

import net.fabricmc.api.ClientModInitializer;

public class CandlelightClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CandlelightClient.preInitClient();
        CandlelightClient.initClient();
    }
}
