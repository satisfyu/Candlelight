package net.satisfy.candlelight.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.client.gui.LetterGui;
import net.satisfy.candlelight.client.model.*;
import net.satisfy.candlelight.client.renderer.block.*;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.candlelight.core.registry.ScreenHandlerTypeRegistry;
import net.satisfy.candlelight.core.registry.StorageTypeRegistry;

import static net.satisfy.candlelight.core.registry.ObjectRegistry.*;

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

        RenderTypeRegistry.register(RenderType.translucent(), WINE_GLASS_BLOCK.get(), TABLE_SET.get(), GLASS_BLOCK.get());

        BlockEntityRendererRegistry.register(EntityTypeRegistry.CANDLELIGHT_BANNER.get(), CompletionistBannerRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.STORAGE_BLOCK_ENTITY.get(), context -> new StorageBlockEntityRenderer());
        BlockEntityRendererRegistry.register(EntityTypeRegistry.DINNER_BELL_BLOCK_ENTITY.get(), DinnerBellRenderer::new);
        MenuRegistry.registerScreenFactory(ScreenHandlerTypeRegistry.LETTER_SCREEN_HANDLER.get(), LetterGui::new);
        registerStorageType();
    }

    public static void registerStorageType(ResourceLocation location, StorageTypeRenderer renderer) {
        StorageBlockEntityRenderer.registerStorageType(location, renderer);
    }

    public static void registerStorageType() {
        registerStorageType(StorageTypeRegistry.SHELF, new ShelfRenderer());
        registerStorageType(StorageTypeRegistry.TABLE_SET, new TableSetRenderer());
        registerStorageType(StorageTypeRegistry.JEWELRY_BOX, new JewelryRenderer());
    }

    public static void preInitClient() {
        registerEntityModelLayers();
    }

    public static void registerEntityModelLayers() {
        EntityModelLayerRegistry.register(DinnerBellModel.LAYER_LOCATION, DinnerBellModel::getTexturedModelData);
        EntityModelLayerRegistry.register(CompletionistBannerRenderer.LAYER_LOCATION, CompletionistBannerRenderer::createBodyLayer);
        EntityModelLayerRegistry.register(FlowerCrownModel.LAYER_LOCATION, FlowerCrownModel::createBodyLayer);
        EntityModelLayerRegistry.register(CookingHatModel.LAYER_LOCATION, CookingHatModel::createBodyLayer);
        EntityModelLayerRegistry.register(CookingChestplateModel.LAYER_LOCATION, CookingChestplateModel::createBodyLayer);
        EntityModelLayerRegistry.register(CookingLeggingsModel.LAYER_LOCATION, CookingLeggingsModel::createBodyLayer);
        EntityModelLayerRegistry.register(CookingBootsModel.LAYER_LOCATION, CookingBootsModel::createBodyLayer);
    }
}
