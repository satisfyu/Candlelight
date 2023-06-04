package satisfyu.candlelight.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.client.gui.handler.CookingPanScreenHandler;
import satisfyu.candlelight.client.gui.handler.LetterGuiHandler;
import satisfyu.candlelight.client.gui.handler.WineStationGuiHandler;

import java.util.function.Supplier;


public class ScreenHandlerTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registry.MENU_REGISTRY);

    public static final RegistrySupplier<MenuType<CookingPanScreenHandler>> COOKING_PAN_SCREEN_HANDLER = create("cooking_pan_gui_handler", () -> new MenuType<>(CookingPanScreenHandler::new)) ;
    //public static final RegistrySupplier<MenuType<CookingPotGuiHandler>> COOKING_POT_SCREEN_HANDLER = create("cooking_pot_gui_handler", () -> new MenuType<>(CookingPotGuiHandler::new)) ;
    public static final RegistrySupplier<MenuType<LetterGuiHandler>> LETTER_SCREEN_HANDLER = create("letter_screen", () -> new MenuType<>(LetterGuiHandler::new));
    public static final RegistrySupplier<MenuType<WineStationGuiHandler>> WINE_STATION_SCREEN_HANDLER = create("wine_station_gui_handler", () -> new MenuType<>(WineStationGuiHandler::new));


    public static void init() {
        MENU_TYPES.register();
    }

    private static <T extends MenuType<?>> RegistrySupplier<T> create(String name, Supplier<T> type) {
        return MENU_TYPES.register(name, type);
    }
}
