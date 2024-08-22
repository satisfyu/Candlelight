package net.satisfy.candlelight.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import net.satisfy.candlelight.client.gui.LetterGui;
import net.satisfy.candlelight.client.model.DinnerBellModel;
import net.satisfy.candlelight.client.render.DinnerBellRenderer;
import net.satisfy.candlelight.registry.ArmorRegistry;
import net.satisfy.candlelight.registry.BlockEntityRegistry;
import net.satisfy.candlelight.registry.ScreenHandlerTypeRegistry;

import static net.satisfy.candlelight.registry.ObjectRegistry.*;

@Environment(EnvType.CLIENT)
public class CandlelightClient {

    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(), ROSE.get(), POTTED_ROSE.get(), GLASS_BLOCK.get(),
                OAK_CHAIR.get(), DARK_OAK_CHAIR.get(), SPRUCE_CHAIR.get(), WARPED_CHAIR.get(),
                BIRCH_CHAIR.get(), MANGROVE_CHAIR.get(), ACACIA_CHAIR.get(), CRIMSON_CHAIR.get(),
                JUNGLE_CHAIR.get(), OAK_TABLE.get(), ACACIA_TABLE.get(), DARK_OAK_TABLE.get(),
                BIRCH_TABLE.get(), SPRUCE_TABLE.get(), JUNGLE_TABLE.get(), MANGROVE_TABLE.get(),
                WARPED_TABLE.get(), CRIMSON_TABLE.get(), CHAIR.get(), TABLE.get(), BAMBOO_CHAIR.get(),
                BAMBOO_TABLE.get(), CHERRY_TABLE.get(), CHERRY_CHAIR.get(), WINE_GLASS_BLOCK.get(),
                COOKING_POT.get(), COOKING_PAN.get(), RED_NETHER_BRICKS_STOVE.get(), QUARTZ_STOVE.get(),
                MUD_STOVE.get(), END_STOVE.get(), GRANITE_STOVE.get(), DEEPSLATE_STOVE.get(), SANDSTONE_STOVE.get(),
                STONE_BRICKS_STOVE.get(), COBBLESTONE_STOVE.get(), BAMBOO_STOVE.get()
        );

        ClientStorageTypes.init();
        BlockEntityRendererRegistry.register(BlockEntityRegistry.DINNER_BELL_BLOCK_ENTITY.get(), DinnerBellRenderer::new);
        RenderTypeRegistry.register(RenderType.translucent(), WINE_GLASS_BLOCK.get(), TABLE_SET.get(), GLASS_BLOCK.get());
        registerMenu();
    }

    public static void registerMenu(){
        MenuRegistry.registerScreenFactory(ScreenHandlerTypeRegistry.LETTER_SCREEN_HANDLER.get(), LetterGui::new);
    }

    public static void preInitClient() {
        registerEntityModelLayer();
    }

    public static void registerEntityModelLayer() {
        ArmorRegistry.registerArmorModelLayers();
        EntityModelLayerRegistry.register(DinnerBellModel.LAYER_LOCATION, DinnerBellModel::getTexturedModelData);

    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
