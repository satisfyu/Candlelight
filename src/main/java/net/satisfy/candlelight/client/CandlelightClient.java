package net.satisfy.candlelight.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.satisfy.candlelight.client.gui.CookingPanGui;
import net.satisfy.candlelight.client.gui.LetterGui;
import net.satisfy.candlelight.client.model.CookingHatModel;
import net.satisfy.candlelight.registry.ObjectRegistry;
import net.satisfy.candlelight.registry.ScreenHandlerTypes;


public class CandlelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ObjectRegistry.CAKE_STAND, ObjectRegistry.CHAIR,
                ObjectRegistry.TABLE, ObjectRegistry.ROSE, ObjectRegistry.POTTED_ROSE, ObjectRegistry.STRAWBERRY_WILD_JUNGLE,
                ObjectRegistry.STRAWBERRY_WILD_TAIGA, ObjectRegistry.TOMATOES_WILD, ObjectRegistry.APPLE_TREE_SAPLING

        );

        registerModels();

        HandledScreens.register(ScreenHandlerTypes.COOKING_PAN_SCREEN_HANDLER, CookingPanGui::new);
        HandledScreens.register(ScreenHandlerTypes.LETTER_SCREEN_HANDLER, LetterGui::new);

    }



    private static void registerModels(){
        EntityModelLayerRegistry.registerModelLayer(CookingHatModel.LAYER_LOCATION, CookingHatModel::getTexturedModelData);
    }
}
