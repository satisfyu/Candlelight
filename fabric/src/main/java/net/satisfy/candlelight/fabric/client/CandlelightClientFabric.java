package net.satisfy.candlelight.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.satisfy.candlelight.client.CandlelightClient;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import net.satisfy.candlelight.fabric.client.renderer.*;

public class CandlelightClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CandlelightClient.preInitClient();
        CandlelightClient.initClient();

        ArmorRenderer.register(new CandlelightHatRenderer(), ObjectRegistry.COOKING_HAT.get(), ObjectRegistry.FLOWER_CROWN.get(), ObjectRegistry.NECKTIE.get());
        ArmorRenderer.register(new CandlelightChestplateRenderer(), ObjectRegistry.CHEFS_JACKET.get(), ObjectRegistry.FORMAL_SHIRT.get(), ObjectRegistry.SHIRT.get());
        ArmorRenderer.register(new CandlelightLeggingsRenderer(), ObjectRegistry.CHEFS_PANTS.get());
        ArmorRenderer.register(new CandlelightBootsRenderer(), ObjectRegistry.CHEFS_BOOTS.get());
        ArmorRenderer.register(new DyeableCandlelightChestplateRenderer(), ObjectRegistry.DRESS.get());
        ArmorRenderer.register(new DyeableCandlelightLeggingsRenderer(), ObjectRegistry.TROUSERS_AND_VEST.get());
    }
}
