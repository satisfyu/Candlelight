package satisfy.candlelight.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import satisfy.candlelight.Candlelight;
import satisfy.candlelight.client.gui.handler.CookingPanGuiHandler;
import satisfy.candlelight.client.gui.handler.CookingPotGuiHandler;
import satisfy.candlelight.client.gui.handler.LetterGuiHandler;

import java.util.function.Supplier;


public class ScreenHandlerTypeRegistry {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registry.MENU_REGISTRY);

    public static final RegistrySupplier<MenuType<CookingPanGuiHandler>> COOKING_PAN_SCREEN_HANDLER = create("cooking_pan_gui_handler", () -> new MenuType<>(CookingPanGuiHandler::new)) ;
    public static final RegistrySupplier<MenuType<CookingPotGuiHandler>> COOKING_POT_SCREEN_HANDLER = create("cooking_pot_gui_handler", () -> new MenuType<>(CookingPotGuiHandler::new)) ;
    public static final RegistrySupplier<MenuType<LetterGuiHandler>> LETTER_SCREEN_HANDLER = create("letter_screen", () -> new MenuType<>(LetterGuiHandler::new));


    public static void init() {
        MENU_TYPES.register();
    }

    private static <T extends MenuType<?>> RegistrySupplier<T> create(String name, Supplier<T> type) {
        return MENU_TYPES.register(name, type);
    }
}