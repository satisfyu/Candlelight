package satisfyu.candlelight.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import satisfyu.candlelight.client.gui.CookingPanGui;
import satisfyu.candlelight.client.gui.CookingPotGui;
import satisfyu.candlelight.client.gui.LetterGui;
import satisfyu.candlelight.registry.ArmorRegistry;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.registry.ScreenHandlerTypeRegistry;

@Environment(EnvType.CLIENT)
public class CandlelightClient {

    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(), ObjectRegistry.ROSE.get(), ObjectRegistry.POTTED_ROSE.get(),
                ObjectRegistry.WILD_TOMATOES.get(), ObjectRegistry.LETTUCE_CROP.get(), ObjectRegistry.WILD_LETTUCE.get(),
                ObjectRegistry.TOMATO_CROP.get(), ObjectRegistry.OAK_CHAIR.get(), ObjectRegistry.DARK_OAK_CHAIR.get(), ObjectRegistry.SPRUCE_CHAIR.get(), ObjectRegistry.WARPED_CHAIR.get(),
                ObjectRegistry.BIRCH_CHAIR.get(), ObjectRegistry.MANGROVE_CHAIR.get(), ObjectRegistry.ACACIA_CHAIR.get(), ObjectRegistry.CRIMSON_CHAIR.get(),
                ObjectRegistry.JUNGLE_CHAIR.get(), ObjectRegistry.OAK_TABLE.get(), ObjectRegistry.ACACIA_TABLE.get(), ObjectRegistry.DARK_OAK_TABLE.get(),
                ObjectRegistry.BIRCH_TABLE.get(), ObjectRegistry.SPRUCE_TABLE.get(), ObjectRegistry.JUNGLE_TABLE.get(), ObjectRegistry.MANGROVE_TABLE.get(),
                ObjectRegistry.WARPED_TABLE.get(), ObjectRegistry.CRIMSON_TABLE.get(), ObjectRegistry.CHAIR.get(), ObjectRegistry.TABLE.get(), ObjectRegistry.BAMBOO_CHAIR.get(),
                ObjectRegistry.BAMBOO_TABLE.get(), ObjectRegistry.BAMBOO_STOVE.get(), ObjectRegistry.CHERRY_TABLE.get(), ObjectRegistry.CHERRY_CHAIR.get()
        );

        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> {
                    if (world == null || pos == null) {
                        return -1;
                    }
                    return BiomeColors.getAverageWaterColor(world, pos);
                }, ObjectRegistry.MUD_KITCHEN_SINK, ObjectRegistry.SANDSTONE_KITCHEN_SINK, ObjectRegistry.DEEPSLATE_KITCHEN_SINK, ObjectRegistry.END_KITCHEN_SINK,
                ObjectRegistry.STONE_BRICKS_KITCHEN_SINK, ObjectRegistry.COBBLESTONE_KITCHEN_SINK, ObjectRegistry.GRANITE_KITCHEN_SINK,
                ObjectRegistry.QUARTZ_KITCHEN_SINK, ObjectRegistry.RED_NETHER_BRICKS_KITCHEN_SINK, ObjectRegistry.BASALT_KITCHEN_SINK,
                ObjectRegistry.BAMBOO_KITCHEN_SINK);

        ClientStorageTypes.init();
        RenderTypeRegistry.register(RenderType.translucent(), ObjectRegistry.TABLE_SET.get());



        MenuRegistry.registerScreenFactory(ScreenHandlerTypeRegistry.COOKING_PAN_SCREEN_HANDLER.get(), CookingPanGui::new);
        MenuRegistry.registerScreenFactory(ScreenHandlerTypeRegistry.COOKING_POT_SCREEN_HANDLER.get(), CookingPotGui::new);
        MenuRegistry.registerScreenFactory(ScreenHandlerTypeRegistry.LETTER_SCREEN_HANDLER.get(), LetterGui::new);
    }

    public static void preInitClient() {
        registerEntityRenderers();
        registerEntityModelLayer();
    }

    public static void registerEntityRenderers() {
    }

    public static void registerEntityModelLayer() {
        ArmorRegistry.registerArmorModelLayers();
    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
