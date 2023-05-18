package satisfyu.candlelight.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import satisfyu.candlelight.client.gui.CookingPanScreen;
import satisfyu.candlelight.client.gui.LetterGui;
import satisfyu.candlelight.client.gui.WineStationGui;
import satisfyu.candlelight.client.model.CookingHatModel;
import satisfyu.candlelight.registry.BlockEntityRegistry;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.registry.ScreenHandlerTypes;
import satisfyu.candlelight.render.WineStationBlockEntityRenderer;


public class CandlelightClient {

    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(), ObjectRegistry.CAKE_STAND.get(), ObjectRegistry.CHAIR.get(),
                ObjectRegistry.TABLE.get(), ObjectRegistry.ROSE.get(), ObjectRegistry.POTTED_ROSE.get(), ObjectRegistry.STRAWBERRY_WILD_JUNGLE.get(),
                ObjectRegistry.STRAWBERRY_WILD_TAIGA.get(), ObjectRegistry.TOMATOES_WILD.get(), ObjectRegistry.APPLE_TREE_SAPLING.get(),
                ObjectRegistry.OAK_CHAIR.get(), ObjectRegistry.DARK_OAK_CHAIR.get(), ObjectRegistry.SPRUCE_CHAIR.get(), ObjectRegistry.WARPED_CHAIR.get(),
                ObjectRegistry.BIRCH_CHAIR.get(), ObjectRegistry.MANGROVE_CHAIR.get(), ObjectRegistry.ACACIA_CHAIR.get(), ObjectRegistry.CRIMSON_CHAIR.get(),
                ObjectRegistry.JUNGLE_CHAIR.get(), ObjectRegistry.OAK_TABLE.get(), ObjectRegistry.ACACIA_TABLE.get(), ObjectRegistry.DARK_OAK_TABLE.get(),
                ObjectRegistry.BIRCH_TABLE.get(), ObjectRegistry.SPRUCE_TABLE.get(), ObjectRegistry.JUNGLE_TABLE.get(), ObjectRegistry.MANGROVE_TABLE.get(),
                ObjectRegistry.WARPED_TABLE.get(), ObjectRegistry.CRIMSON_TABLE.get(), ObjectRegistry.BROCCOLI_CROP.get(), ObjectRegistry.STRAWBERRY_CROP.get(),
                ObjectRegistry.WILD_BROCCOLI.get(), ObjectRegistry.APPLE_LEAVES.get(), ObjectRegistry.STRAWBERRY_JAM.get()
        );

        RenderTypeRegistry.register(RenderType.translucent(), ObjectRegistry.TABLE_SET.get());

        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> BiomeColors.getAverageFoliageColor(world, pos), ObjectRegistry.APPLE_LEAVES);
        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return -1;
            }
            return BiomeColors.getAverageWaterColor(world, pos);
        }, ObjectRegistry.MUD_KITCHEN_SINK, ObjectRegistry.SANDSTONE_KITCHEN_SINK, ObjectRegistry.DEEPSLATE_KITCHEN_SINK, ObjectRegistry.END_KITCHEN_SINK,
                ObjectRegistry.STONE_BRICKS_KITCHEN_SINK, ObjectRegistry.COBBLESTONE_KITCHEN_SINK, ObjectRegistry.GRANITE_KITCHEN_SINK,
                ObjectRegistry.QUARTZ_KITCHEN_SINK, ObjectRegistry.RED_NETHER_BRICKS_KITCHEN_SINK);



        MenuRegistry.registerScreenFactory(ScreenHandlerTypes.COOKING_PAN_SCREEN_HANDLER.get(), CookingPanScreen::new);
        MenuRegistry.registerScreenFactory(ScreenHandlerTypes.LETTER_SCREEN_HANDLER.get(), LetterGui::new);
        MenuRegistry.registerScreenFactory(ScreenHandlerTypes.WINE_STATION_SCREEN_HANDLER.get(), WineStationGui::new);

        BlockEntityRendererRegistry.register(BlockEntityRegistry.WINE_STATION_BLOCK_ENTITY.get(), WineStationBlockEntityRenderer::new);
    }



    public static void preInitClient(){
        EntityModelLayerRegistry.register(CookingHatModel.LAYER_LOCATION, CookingHatModel::getTexturedModelData);
    }
}
