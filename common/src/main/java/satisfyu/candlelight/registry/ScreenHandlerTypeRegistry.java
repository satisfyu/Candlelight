package satisfyu.candlelight.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.client.gui.handler.CookingPanGuiHandler;
import satisfyu.candlelight.client.gui.handler.CookingPotGuiHandler;
import satisfyu.candlelight.client.gui.handler.LetterGuiHandler;

import java.util.function.Supplier;


public class ScreenHandlerTypeRegistry {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registries.MENU);

    public static final RegistrySupplier<MenuType<CookingPanGuiHandler>> COOKING_PAN_SCREEN_HANDLER = create("cooking_pan_gui_handler", () -> new MenuType<>(CookingPanGuiHandler::new, FeatureFlags.VANILLA_SET)) ;
    public static final RegistrySupplier<MenuType<CookingPotGuiHandler>> COOKING_POT_SCREEN_HANDLER = create("cooking_pot_gui_handler", () -> new MenuType<>(CookingPotGuiHandler::new, FeatureFlags.VANILLA_SET)) ;
    public static final RegistrySupplier<MenuType<LetterGuiHandler>> LETTER_SCREEN_HANDLER = create("letter_screen", () -> new MenuType<>(LetterGuiHandler::new,  FeatureFlags.VANILLA_SET));


    public static void init() {
        MENU_TYPES.register();
    }

    private static <T extends MenuType<?>> RegistrySupplier<T> create(String name, Supplier<T> type) {
        return MENU_TYPES.register(name, type);
    }
}
