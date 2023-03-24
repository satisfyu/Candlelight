package net.satisfy.candlelight;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import net.minecraft.item.Items;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.TabbedItemGroup;
import net.moddingplayground.frame.api.util.GUIIcon;
import net.satisfy.candlelight.registry.RecipeTypes;
import net.satisfy.candlelight.registry.ObjectRegistry;
import net.satisfy.candlelight.registry.ScreenHandlerTypes;
import net.satisfy.candlelight.registry.StorageTypes;
import net.satisfy.candlelight.world.feature.ConfiguredFeatures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;

import static net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab.Predicate.ALWAYS;
import static net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab.Predicate.items;

public class Candlelight implements ModInitializer {
    public static final String MOD_ID = "candlelight";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        RecipeTypes.init();
        ObjectRegistry.init();
        StorageTypes.init();

        Tab tab = Tab.builder().predicate(
                Tab.Predicate.items(
                        ObjectRegistry.TOMATO_SEEDS.asItem(),
                        ObjectRegistry.TOMATO.asItem(),
                        ObjectRegistry.TOMATO_CRATE.asItem(),
                        ObjectRegistry.BROCCOLI.asItem(),
                        ObjectRegistry.BROCCOLI_CRATE.asItem(),
                        ObjectRegistry.STRAWBERRY_SEEDS.asItem(),
                        ObjectRegistry.STRAWBERRY.asItem(),
                        ObjectRegistry.STRAWBERRY_CRATE.asItem(),
                        ObjectRegistry.SOFA.asItem(),
                        ObjectRegistry.CHAIR.asItem(),
                        ObjectRegistry.TABLE.asItem(),
                        ObjectRegistry.LAMP.asItem(),
                        ObjectRegistry.SIDEBOARD.asItem(),
                        ObjectRegistry.DRAWER.asItem(),
                        ObjectRegistry.FLOORBOARD.asItem(),
                        ObjectRegistry.CAKE_STAND.asItem(),
                        ObjectRegistry.COOKING_POT.asItem(),
                        ObjectRegistry.COOKING_PAN.asItem(),
                        ObjectRegistry.TRAY.asItem(),
                        ObjectRegistry.TABLE_SET.asItem(),
                        ObjectRegistry.BUTTER.asItem(),
                        ObjectRegistry.MOZZARELLA.asItem(),
                        ObjectRegistry.TOMATO_SOUP.asItem(),
                        ObjectRegistry.BEETROOT_SALAD.asItem(),
                        ObjectRegistry.COOKED_BEEF.asItem(),
                        ObjectRegistry.PASTA.asItem(),
                        ObjectRegistry.BEEF_TARTARE.asItem(),
                        //ObjectRegistry.TOMATO_MOZZARELLA_SALAT.asItem(),
                        ObjectRegistry.BROCCOLI_BEEF.asItem(),
                        ObjectRegistry.BROCCOLI_TOMATO.asItem(),
                        //ObjectRegistry.ROASTBEEF_CARROTS.asItem(),
                        ObjectRegistry.SALMON.asItem(),
                        ObjectRegistry.VEGGIE_PLATE.asItem(),
                        ObjectRegistry.PORK_RIB.asItem(),
                        //ObjectRegistry.LASAGNA.asItem(),
                        //ObjectRegistry.BEEF_WELLINGTON.asItem(),
                        ObjectRegistry.MASHED_POTATOES.asItem(),
                        ObjectRegistry.FRICASSE.asItem(),
                        ObjectRegistry.FRIED_EGG.asItem(),
                        ObjectRegistry.STRAWBERRY_JAM.asItem(),
                        ObjectRegistry.VINEGAR.asItem(),
                        ObjectRegistry.RED_WINE.asItem(),
                        ObjectRegistry.PRAETORIAN_WINE.asItem(),
                        ObjectRegistry.PANCAKE.asItem(),
                        ObjectRegistry.WAFFLE.asItem(),
                        ObjectRegistry.STRAWBERRY_GLAZED_COOKIE.asItem(),
                        ObjectRegistry.SWEETBERRY_GLAZED_COOKIE.asItem(),
                        ObjectRegistry.CHOCOLATE_GLAZED_COOKIE.asItem(),
                        ObjectRegistry.CROISSANT.asItem(),
                        ObjectRegistry.BUNDT_CAKE.asItem(),
                        ObjectRegistry.SESAM_BREAD.asItem(),
                        ObjectRegistry.STRAWBERRY_CHOCOLATE.asItem(),
                        ObjectRegistry.STRAWBERRY_CAKE_SLICE.asItem(),
                        ObjectRegistry.STRAWBERRY_CAKE.asItem(),
                        ObjectRegistry.STRAWBERRY_MILKSHAKE.asItem(),
                        ObjectRegistry.STRAWBERRY_ICECREAM.asItem(),
                        ObjectRegistry.SWEETBERRY_CAKE_SLICE.asItem(),
                        ObjectRegistry.SWEETBERRY_CAKE.asItem(),
                        ObjectRegistry.SWEETBERRY_MILKSHAKE.asItem(),
                        ObjectRegistry.SWEETBERRY_ICECREAM.asItem(),
                        ObjectRegistry.CHOCOLATE_CAKE_SLICE.asItem(),
                        ObjectRegistry.CHOCOLATE_CAKE.asItem(),
                        ObjectRegistry.CHOCOLATE_MILKSHAKE.asItem(),
                        ObjectRegistry.CHOCOLATE_ICECREAM.asItem(),
                        ObjectRegistry.TABLE_SIGN.asItem(),
                        ObjectRegistry.STREET_SIGN.asItem(),
                        ObjectRegistry.PAINTING.asItem(),
                        ObjectRegistry.HEARTH.asItem(),
                        ObjectRegistry.ROSE.asItem(),
                        ObjectRegistry.CANDLE.asItem(),
                        ObjectRegistry.JEWELRY_BOX.asItem(),
                        ObjectRegistry.CHOCOLATE_BOX.asItem(),
                        ObjectRegistry.COOKING_HAT.asItem(),
                        ObjectRegistry.CHEFS_JACKET.asItem(),
                        ObjectRegistry.CHEFS_PANTS.asItem(),
                        ObjectRegistry.CHEFS_BOOTS.asItem(),
                        ObjectRegistry.GLASS.asItem(),
                        ObjectRegistry.NAPKIN.asItem(),
                        ObjectRegistry.BOOK.asItem(),
                        ObjectRegistry.NOTE_PAPER.asItem(),
                        ObjectRegistry.NOTE_PAPER_WRITEABLE.asItem(),
                        ObjectRegistry.NOTE_PAPER_WRITTEN.asItem(),
                        ObjectRegistry.TYPEWRITER_IRON.asItem(),
                        ObjectRegistry.TYPEWRITER_COPPER.asItem(),
                        ObjectRegistry.LETTER_OPEN.asItem(),
                        ObjectRegistry.LETTER_CLOSED.asItem(),
                        ObjectRegistry.LOVE_LETTER.asItem(),
                        ObjectRegistry.OAK_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.OAK_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.OAK_DRAWER.asItem(),
                        ObjectRegistry.OAK_CABINET.asItem(),
                        ObjectRegistry.COBBLESTONE_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.COBBLESTONE_STOVE.asItem(),
                        ObjectRegistry.COBBLESTONE_KITCHEN_SINK.asItem(),
                        
                        ObjectRegistry.BIRCH_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.BIRCH_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.BIRCH_DRAWER.asItem(),
                        ObjectRegistry.BIRCH_CABINET.asItem(),
                        ObjectRegistry.SANDSTONE_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.SANDSTONE_STOVE.asItem(),
                        ObjectRegistry.SANDSTONE_KITCHEN_SINK.asItem(),
                        
                        ObjectRegistry.SPRUCE_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.SPRUCE_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.SPRUCE_DRAWER.asItem(),
                        ObjectRegistry.SPRUCE_CABINET.asItem(),
                        ObjectRegistry.BRICKS_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.BRICKS_STOVE.asItem(),
                        ObjectRegistry.BRICKS_KITCHEN_SINK.asItem(),
                        
                        ObjectRegistry.DARK_OAK_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.DARK_OAK_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.DARK_OAK_DRAWER.asItem(),
                        ObjectRegistry.DARK_OAK_CABINET.asItem(),
                        ObjectRegistry.DEEPSLATE_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.DEEPSLATE_STOVE.asItem(),
                        ObjectRegistry.DEEPSLATE_KITCHEN_SINK.asItem(),
                        
                        ObjectRegistry.ACACIA_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.ACACIA_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.ACACIA_DRAWER.asItem(),
                        ObjectRegistry.ACACIA_CABINET.asItem(),
                        ObjectRegistry.GRANITE_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.GRANITE_STOVE.asItem(),
                        ObjectRegistry.GRANITE_KITCHEN_SINK.asItem(),
                        
                        ObjectRegistry.JUNGLE_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.JUNGLE_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.JUNGLE_DRAWER.asItem(),
                        ObjectRegistry.JUNGLE_CABINET.asItem(),
                        
                        ObjectRegistry.MANGROVE_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.MANGROVE_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.MANGROVE_DRAWER.asItem(),
                        ObjectRegistry.MANGROVE_CABINET.asItem(),
                        ObjectRegistry.MUD_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.MUD_STOVE.asItem(),
                        ObjectRegistry.MUD_KITCHEN_SINK.asItem(),

                        ObjectRegistry.CRIMSON_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.CRIMSON_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.CRIMSON_DRAWER.asItem(),
                        ObjectRegistry.CRIMSON_CABINET.asItem(),
                        ObjectRegistry.RED_NETHER_BRICKS_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.RED_NETHER_BRICKS_STOVE.asItem(),
                        ObjectRegistry.RED_NETHER_BRICKS_KITCHEN_SINK.asItem(),
                        
                        ObjectRegistry.WARPED_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.WARPED_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.WARPED_DRAWER.asItem(),
                        ObjectRegistry.WARPED_CABINET.asItem()

                )).build("candlelight", GUIIcon.of(() -> new ItemStack(ObjectRegistry.HEARTH)));
        ((TabbedItemGroup) Vinery.CREATIVE_TAB).getTabs().add(tab);
        tab.addToGroup(((TabbedItemGroup) Vinery.CREATIVE_TAB));

        ScreenHandlerTypes.init();
        ConfiguredFeatures.init();

    }
}
