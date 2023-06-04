package satisfyu.candlelight.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import satisfyu.candlelight.client.gui.CookingPanScreen;
import satisfyu.candlelight.client.gui.LetterGui;
import satisfyu.candlelight.client.gui.WineStationGui;
import satisfyu.candlelight.client.model.CookingHatModel;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.registry.ScreenHandlerTypes;

@Environment(EnvType.CLIENT)
public class CandlelightClient {

    public static boolean rememberedRecipeBookOpen = false;
    public static boolean rememberedCraftableToggle = true;

    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(), ObjectRegistry.CAKE_STAND.get(), ObjectRegistry.ROSE.get(), ObjectRegistry.POTTED_ROSE.get(), ObjectRegistry.STRAWBERRY_WILD_JUNGLE.get(),
                ObjectRegistry.STRAWBERRY_WILD_TAIGA.get(), ObjectRegistry.TOMATOES_WILD.get(), ObjectRegistry.BROCCOLI_CROP.get(), ObjectRegistry.STRAWBERRY_CROP.get(),
                ObjectRegistry.WILD_BROCCOLI.get(), ObjectRegistry.STRAWBERRY_JAM.get(), ObjectRegistry.TOMATO_CROP.get(), ObjectRegistry.APPLE_JAM.get(),
                ObjectRegistry.SWEETBERRY_JAM.get(), ObjectRegistry.GRAPE_JAM.get(), ObjectRegistry.CHERRY_JAM.get(), ObjectRegistry.CHERRY_JAR.get()

        );

        StorageTypes.init();
        RenderTypeRegistry.register(RenderType.translucent(), ObjectRegistry.TABLE_SET.get());



        MenuRegistry.registerScreenFactory(ScreenHandlerTypes.COOKING_PAN_SCREEN_HANDLER.get(), CookingPanScreen::new);
        MenuRegistry.registerScreenFactory(ScreenHandlerTypes.LETTER_SCREEN_HANDLER.get(), LetterGui::new);
        MenuRegistry.registerScreenFactory(ScreenHandlerTypes.WINE_STATION_SCREEN_HANDLER.get(), WineStationGui::new);
    }



    public static void preInitClient(){
        EntityModelLayerRegistry.register(CookingHatModel.LAYER_LOCATION, CookingHatModel::getTexturedModelData);
    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
