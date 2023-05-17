package satisfyu.candlelight.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import satisfyu.candlelight.client.CandlelightClient;

public class CandlelightClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CandlelightClient.preInitClient();
        CandlelightClient.initClient();
    }
}
