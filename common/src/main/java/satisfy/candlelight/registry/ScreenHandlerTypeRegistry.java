package satisfy.candlelight.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import satisfy.candlelight.Candlelight;
import satisfy.candlelight.client.gui.handler.LetterGuiHandler;

import java.util.function.Supplier;


public class ScreenHandlerTypeRegistry {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registries.MENU);
    public static final RegistrySupplier<MenuType<LetterGuiHandler>> LETTER_SCREEN_HANDLER = create(() -> new MenuType<>(LetterGuiHandler::new,  FeatureFlags.VANILLA_SET));

    public static void init() {
        MENU_TYPES.register();
    }

    private static <T extends MenuType<?>> RegistrySupplier<T> create(Supplier<T> type) {
        return MENU_TYPES.register("letter_screen", type);
    }
}
