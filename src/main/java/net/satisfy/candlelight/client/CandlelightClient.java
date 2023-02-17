package net.satisfy.candlelight.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.satisfy.candlelight.client.model.CookingHatModel;
import net.satisfy.candlelight.client.screen.LetterScreen;
import net.satisfy.candlelight.registry.ObjectRegistry;

public class CandlelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ObjectRegistry.CAKE_STAND, ObjectRegistry.CHAIR, ObjectRegistry.TABLE
        HandledScreens.register(ObjectRegistry.LETTER_SCREEN_HANDLER, LetterScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ObjectRegistry.CAKE_STAND
        );
        registerModels();
    }

    private static void registerModels(){
        EntityModelLayerRegistry.registerModelLayer(CookingHatModel.LAYER_LOCATION, CookingHatModel::getTexturedModelData);
    }
}
