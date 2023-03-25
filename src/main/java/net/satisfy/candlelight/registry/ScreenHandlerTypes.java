package net.satisfy.candlelight.registry;

import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;
import net.satisfy.candlelight.client.gui.handler.CookingPanGuiHandler;
import net.satisfy.candlelight.client.gui.handler.LetterGuiHandler;
import net.satisfy.candlelight.client.gui.handler.WineStationGuiHandler;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import satisfyu.vinery.VineryIdentifier;


public class ScreenHandlerTypes {

    public static final ScreenHandlerType<CookingPanGuiHandler> COOKING_PAN_SCREEN_HANDLER = new ScreenHandlerType<>(CookingPanGuiHandler::new);
    public static final ScreenHandlerType<LetterGuiHandler> LETTER_SCREEN_HANDLER = new ScreenHandlerType<>(LetterGuiHandler::new);
    public static final ScreenHandlerType<WineStationGuiHandler> WINE_STATION_SCREEN_HANDLER = new ScreenHandlerType<>(WineStationGuiHandler::new);

    public static void init() {
        Registry.register(Registry.SCREEN_HANDLER, new CandlelightIdentifier("cooking_pan_gui_handler"), COOKING_PAN_SCREEN_HANDLER);
        Registry.register(Registry.SCREEN_HANDLER, new CandlelightIdentifier("letter_screen"), LETTER_SCREEN_HANDLER);
        Registry.register(Registry.SCREEN_HANDLER, new CandlelightIdentifier("wine_station_gui_handler"), WINE_STATION_SCREEN_HANDLER);
    }
}
