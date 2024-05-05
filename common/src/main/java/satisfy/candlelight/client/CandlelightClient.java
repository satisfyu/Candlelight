package satisfy.candlelight.client;

import de.cristelknight.doapi.common.util.GeneralUtil;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import satisfy.candlelight.client.gui.CookingPanGui;
import satisfy.candlelight.client.gui.CookingPotGui;
import satisfy.candlelight.client.gui.LetterGui;
import satisfy.candlelight.registry.ArmorRegistry;
import satisfy.candlelight.registry.ObjectRegistry;
import satisfy.candlelight.registry.ScreenHandlerTypeRegistry;

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
                ObjectRegistry.BAMBOO_TABLE.get(), ObjectRegistry.BAMBOO_STOVE.get(), ObjectRegistry.CHERRY_TABLE.get(), ObjectRegistry.CHERRY_CHAIR.get(), ObjectRegistry.GLASS_BLOCK.get()
        );

        ClientStorageTypes.init();
        RenderTypeRegistry.register(RenderType.translucent(), ObjectRegistry.TABLE_BOWL.get(), ObjectRegistry.GLASS_BLOCK.get());
        RenderTypeRegistry.register(RenderType.translucent(), ObjectRegistry.TABLE_SET.get(), ObjectRegistry.GLASS_BLOCK.get());
        MenuRegistry.registerScreenFactory(ScreenHandlerTypeRegistry.COOKING_PAN_SCREEN_HANDLER.get(), CookingPanGui::new);
        MenuRegistry.registerScreenFactory(ScreenHandlerTypeRegistry.COOKING_POT_SCREEN_HANDLER.get(), CookingPotGui::new);
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
    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
