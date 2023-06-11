package satisfyu.candlelight.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.player.Player;
import satisfyu.candlelight.client.gui.CookingPanGui;
import satisfyu.candlelight.client.gui.CookingPotGui;
import satisfyu.candlelight.client.gui.LetterGui;
import satisfyu.candlelight.client.model.CookingHatModel;
import satisfyu.candlelight.entity.chair.ChairRenderer;
import satisfyu.candlelight.registry.EntitiesRegistry;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.registry.ScreenHandlerTypeRegistry;

import static satisfyu.candlelight.registry.ObjectRegistry.*;

@Environment(EnvType.CLIENT)
public class CandlelightClient {

    public static boolean rememberedRecipeBookOpen = false;
    public static boolean rememberedCraftableToggle = true;

    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(), ObjectRegistry.ROSE.get(), ObjectRegistry.POTTED_ROSE.get(),
                ObjectRegistry.TOMATOES_WILD.get(), ObjectRegistry.BROCCOLI_CROP.get(), ObjectRegistry.WILD_BROCCOLI.get(),
                ObjectRegistry.TOMATO_CROP.get(), OAK_CHAIR.get(), DARK_OAK_CHAIR.get(), SPRUCE_CHAIR.get(), WARPED_CHAIR.get(),
                BIRCH_CHAIR.get(), MANGROVE_CHAIR.get(), ACACIA_CHAIR.get(), CRIMSON_CHAIR.get(),
                JUNGLE_CHAIR.get(), OAK_TABLE.get(), ACACIA_TABLE.get(), DARK_OAK_TABLE.get(),
                BIRCH_TABLE.get(), SPRUCE_TABLE.get(), JUNGLE_TABLE.get(), MANGROVE_TABLE.get(),
                WARPED_TABLE.get(), CRIMSON_TABLE.get()


                );

        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> {
                    if (world == null || pos == null) {
                        return -1;
                    }
                    return BiomeColors.getAverageWaterColor(world, pos);
                }, MUD_KITCHEN_SINK, SANDSTONE_KITCHEN_SINK, DEEPSLATE_KITCHEN_SINK, END_KITCHEN_SINK,
                STONE_BRICKS_KITCHEN_SINK, COBBLESTONE_KITCHEN_SINK, GRANITE_KITCHEN_SINK,
                QUARTZ_KITCHEN_SINK, RED_NETHER_BRICKS_KITCHEN_SINK);

        ClientStorageTypes.init();
        RenderTypeRegistry.register(RenderType.translucent(), ObjectRegistry.TABLE_SET.get());



        MenuRegistry.registerScreenFactory(ScreenHandlerTypeRegistry.COOKING_PAN_SCREEN_HANDLER.get(), CookingPanGui::new);
        MenuRegistry.registerScreenFactory(ScreenHandlerTypeRegistry.COOKING_POT_SCREEN_HANDLER.get(), CookingPotGui::new);
        MenuRegistry.registerScreenFactory(ScreenHandlerTypeRegistry.LETTER_SCREEN_HANDLER.get(), LetterGui::new);
    }

    public static void preInitClient(){
        registerEntityRenderers();
        EntityModelLayerRegistry.register(CookingHatModel.LAYER_LOCATION, CookingHatModel::getTexturedModelData);
    }

    public static void registerEntityRenderers(){
        EntityRendererRegistry.register(EntitiesRegistry.CHAIR, ChairRenderer::new);
    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
