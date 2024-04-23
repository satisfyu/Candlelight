package satisfy.candlelight.client;

import de.cristelknight.doapi.common.util.GeneralUtil;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import satisfy.candlelight.client.gui.LetterGui;
import satisfy.candlelight.client.model.DinnerBellModel;
import satisfy.candlelight.client.render.DinnerBellRenderer;
import satisfy.candlelight.registry.ArmorRegistry;
import satisfy.candlelight.registry.BlockEntityRegistry;
import satisfy.candlelight.registry.ObjectRegistry;
import satisfy.candlelight.registry.ScreenHandlerTypeRegistry;

@Environment(EnvType.CLIENT)
public class CandlelightClient {

    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(), ObjectRegistry.ROSE.get(), ObjectRegistry.POTTED_ROSE.get(), ObjectRegistry.DRINKING_GLASS_BLOCK.get(),
                ObjectRegistry.OAK_CHAIR.get(), ObjectRegistry.DARK_OAK_CHAIR.get(), ObjectRegistry.SPRUCE_CHAIR.get(), ObjectRegistry.WARPED_CHAIR.get(),
                ObjectRegistry.BIRCH_CHAIR.get(), ObjectRegistry.MANGROVE_CHAIR.get(), ObjectRegistry.ACACIA_CHAIR.get(), ObjectRegistry.CRIMSON_CHAIR.get(),
                ObjectRegistry.JUNGLE_CHAIR.get(), ObjectRegistry.OAK_TABLE.get(), ObjectRegistry.ACACIA_TABLE.get(), ObjectRegistry.DARK_OAK_TABLE.get(),
                ObjectRegistry.BIRCH_TABLE.get(), ObjectRegistry.SPRUCE_TABLE.get(), ObjectRegistry.JUNGLE_TABLE.get(), ObjectRegistry.MANGROVE_TABLE.get(),
                ObjectRegistry.WARPED_TABLE.get(), ObjectRegistry.CRIMSON_TABLE.get(), ObjectRegistry.CHAIR.get(), ObjectRegistry.TABLE.get(), ObjectRegistry.BAMBOO_CHAIR.get(),
                ObjectRegistry.BAMBOO_TABLE.get(), ObjectRegistry.CHERRY_TABLE.get(), ObjectRegistry.CHERRY_CHAIR.get(), ObjectRegistry.GLASS_BLOCK.get()
        );

        ClientStorageTypes.init();
        BlockEntityRendererRegistry.register(BlockEntityRegistry.DINNER_BELL_BLOCK_ENTITY.get(), DinnerBellRenderer::new);
        RenderTypeRegistry.register(RenderType.translucent(), ObjectRegistry.TABLE_BOWL.get(), ObjectRegistry.GLASS_BLOCK.get(), ObjectRegistry.TABLE_SET.get(), ObjectRegistry.DRINKING_GLASS_BLOCK.get());
        MenuRegistry.registerScreenFactory(ScreenHandlerTypeRegistry.LETTER_SCREEN_HANDLER.get(), LetterGui::new);

        initColorItems();
    }

    public static void preInitClient() {
        registerEntityModelLayer();
    }

    private static void initColorItems() {
        GeneralUtil.registerColorArmor(ObjectRegistry.COOKING_HAT.get(), 0xFFFFFFFF);
        GeneralUtil.registerColorArmor(ObjectRegistry.CHEFS_JACKET.get(), 0xFFFFFFFF);
        GeneralUtil.registerColorArmor(ObjectRegistry.CHEFS_PANTS.get(), 0xFFFFFFFF);
        GeneralUtil.registerColorArmor(ObjectRegistry.CHEFS_BOOTS.get(), 0xFFFFFFFF);
    }

    public static void registerEntityModelLayer() {
        ArmorRegistry.registerArmorModelLayers();
        EntityModelLayerRegistry.register(DinnerBellModel.LAYER_LOCATION, DinnerBellModel::getTexturedModelData);

    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
