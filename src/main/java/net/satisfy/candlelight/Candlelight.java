package net.satisfy.candlelight;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;

import net.satisfy.candlelight.networking.CandlelightMessages;
import net.satisfy.candlelight.registry.*;
import net.satisfy.candlelight.villager.memory.ModMemoryModuleType;
import net.satisfy.candlelight.villager.poi.ModPointOfInterestTypes;
import net.satisfy.candlelight.villager.task.ModTaskListProvider;
import net.satisfy.candlelight.registry.ObjectRegistry;
import net.satisfy.candlelight.registry.RecipeTypes;
import net.satisfy.candlelight.registry.ScreenHandlerTypes;
import net.satisfy.candlelight.registry.StorageTypes;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import net.satisfy.candlelight.world.feature.ConfiguredFeatures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.util.tab.GUIIcon;
import satisfyu.vinery.util.tab.Tab;
import satisfyu.vinery.util.tab.TabbedItemGroup;

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
                        ObjectRegistry.BROCCOLI_SEEDS.asItem(),
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
                        ObjectRegistry.CABINET.asItem(),
                        ObjectRegistry.FLOORBOARD.asItem(),
                        ObjectRegistry.WINE_STATION.asItem(),
                        ObjectRegistry.CAKE_STAND.asItem(),
                        ObjectRegistry.COOKING_POT.asItem(),
                        ObjectRegistry.COOKING_PAN.asItem(),
                        ObjectRegistry.TRAY.asItem(),
                        ObjectRegistry.TABLE_SET.asItem(),
                        ObjectRegistry.BUTTER.asItem(),
                        ObjectRegistry.MOZZARELLA.asItem(),
                        ObjectRegistry.TOMATO_SOUP.asItem(),
                        ObjectRegistry.MUSHROOM_SOUP.asItem(),
                        ObjectRegistry.BEETROOT_SALAD.asItem(),
                        ObjectRegistry.PASTA.asItem(),
                        ObjectRegistry.COOKED_BEEF.asItem(),
                        ObjectRegistry.BEEF_TARTARE.asItem(),
                        ObjectRegistry.BROCCOLI_BEEF.asItem(),
                        ObjectRegistry.BROCCOLI_TOMATO.asItem(),
                        ObjectRegistry.SALMON_WINE.asItem(),
                        ObjectRegistry.BOLOGNESE.asItem(),
                        ObjectRegistry.VEGGIE_PLATE.asItem(),
                        ObjectRegistry.CHICKEN.asItem(),
                        ObjectRegistry.PORK_RIBS.asItem(),
                        ObjectRegistry.TOMATO_MOZZARELLA_SALAD.asItem(),
                        ObjectRegistry.LASAGNA.asItem(),
                        ObjectRegistry.BEEF_WELLINGTON.asItem(),
                        ObjectRegistry.ROASTBEEF_CARROTS.asItem(),
                        ObjectRegistry.MASHED_POTATOES.asItem(),
                        ObjectRegistry.FRICASSE.asItem(),
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
                        ObjectRegistry.CHOCOLATE.asItem(),
                        ObjectRegistry.TABLE_SIGN.asItem(),
                        ObjectRegistry.STREET_SIGN.asItem(),
                        ObjectRegistry.PAINTING.asItem(),
                        ObjectRegistry.HEARTH.asItem(),
                        ObjectRegistry.ROSE.asItem(),
                        ObjectRegistry.APPLE_TREE_SAPLING.asItem(),
                        ObjectRegistry.GOLD_RING.asItem(),
                        ObjectRegistry.JEWELRY_BOX.asItem(),
                        ObjectRegistry.CHOCOLATE_BOX.asItem(),
                        ObjectRegistry.COOKING_HAT.asItem(),
                        ObjectRegistry.CHEFS_JACKET.asItem(),
                        ObjectRegistry.CHEFS_PANTS.asItem(),
                        ObjectRegistry.CHEFS_BOOTS.asItem(),
                        ObjectRegistry.GLASS.asItem(),
                        ObjectRegistry.NAPKIN.asItem(),
                        ObjectRegistry.NOTE_PAPER.asItem(),
                        ObjectRegistry.NOTE_PAPER_WRITEABLE.asItem(),
                        ObjectRegistry.TYPEWRITER_IRON.asItem(),
                        ObjectRegistry.TYPEWRITER_COPPER.asItem(),
                        ObjectRegistry.LETTER_OPEN.asItem(),
                        ObjectRegistry.LOVE_LETTER_OPEN.asItem(),

                        ObjectRegistry.OAK_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.OAK_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.OAK_DRAWER.asItem(),
                        ObjectRegistry.OAK_CABINET.asItem(),
                        ObjectRegistry.OAK_TABLE.asItem(),
                        ObjectRegistry.OAK_CHAIR.asItem(),
                        ObjectRegistry.COBBLESTONE_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.COBBLESTONE_STOVE.asItem(),
                        ObjectRegistry.COBBLESTONE_KITCHEN_SINK.asItem(),
                        ObjectRegistry.BIRCH_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.BIRCH_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.BIRCH_DRAWER.asItem(),
                        ObjectRegistry.BIRCH_CABINET.asItem(),
                        ObjectRegistry.BIRCH_TABLE.asItem(),
                        ObjectRegistry.BIRCH_CHAIR.asItem(),
                        ObjectRegistry.SANDSTONE_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.SANDSTONE_STOVE.asItem(),
                        ObjectRegistry.SANDSTONE_KITCHEN_SINK.asItem(),
                        ObjectRegistry.SPRUCE_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.SPRUCE_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.SPRUCE_DRAWER.asItem(),
                        ObjectRegistry.SPRUCE_CABINET.asItem(),
                        ObjectRegistry.SPRUCE_TABLE.asItem(),
                        ObjectRegistry.SPRUCE_CHAIR.asItem(),
                        ObjectRegistry.STONE_BRICKS_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.STONE_BRICKS_STOVE.asItem(),
                        ObjectRegistry.STONE_BRICKS_KITCHEN_SINK.asItem(),
                        ObjectRegistry.DARK_OAK_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.DARK_OAK_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.DARK_OAK_DRAWER.asItem(),
                        ObjectRegistry.DARK_OAK_CABINET.asItem(),
                        ObjectRegistry.DARK_OAK_TABLE.asItem(),
                        ObjectRegistry.DARK_OAK_CHAIR.asItem(),
                        ObjectRegistry.DEEPSLATE_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.DEEPSLATE_STOVE.asItem(),
                        ObjectRegistry.DEEPSLATE_KITCHEN_SINK.asItem(),
                        ObjectRegistry.ACACIA_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.ACACIA_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.ACACIA_DRAWER.asItem(),
                        ObjectRegistry.ACACIA_CABINET.asItem(),
                        ObjectRegistry.ACACIA_TABLE.asItem(),
                        ObjectRegistry.ACACIA_CHAIR.asItem(),
                        ObjectRegistry.GRANITE_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.GRANITE_STOVE.asItem(),
                        ObjectRegistry.GRANITE_KITCHEN_SINK.asItem(),
                        ObjectRegistry.JUNGLE_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.JUNGLE_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.JUNGLE_DRAWER.asItem(),
                        ObjectRegistry.JUNGLE_CABINET.asItem(),
                        ObjectRegistry.JUNGLE_TABLE.asItem(),
                        ObjectRegistry.JUNGLE_CHAIR.asItem(),
                        ObjectRegistry.END_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.END_STOVE.asItem(),
                        ObjectRegistry.END_KITCHEN_SINK.asItem(),
                        ObjectRegistry.MANGROVE_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.MANGROVE_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.MANGROVE_DRAWER.asItem(),
                        ObjectRegistry.MANGROVE_CABINET.asItem(),
                        ObjectRegistry.MANGROVE_TABLE.asItem(),
                        ObjectRegistry.MANGROVE_CHAIR.asItem(),
                        ObjectRegistry.MUD_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.MUD_STOVE.asItem(),
                        ObjectRegistry.MUD_KITCHEN_SINK.asItem(),

                        ObjectRegistry.CRIMSON_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.CRIMSON_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.CRIMSON_DRAWER.asItem(),
                        ObjectRegistry.CRIMSON_CABINET.asItem(),
                        ObjectRegistry.CRIMSON_TABLE.asItem(),
                        ObjectRegistry.CRIMSON_CHAIR.asItem(),
                        ObjectRegistry.RED_NETHER_BRICKS_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.RED_NETHER_BRICKS_STOVE.asItem(),
                        ObjectRegistry.RED_NETHER_BRICKS_KITCHEN_SINK.asItem(),

                        ObjectRegistry.WARPED_WINE_RACK_BIG.asItem(),
                        ObjectRegistry.WARPED_WINE_RACK_SMALL.asItem(),
                        ObjectRegistry.WARPED_DRAWER.asItem(),
                        ObjectRegistry.WARPED_CABINET.asItem(),
                        ObjectRegistry.WARPED_CHAIR.asItem(),
                        ObjectRegistry.WARPED_TABLE.asItem(),
                        ObjectRegistry.QUARTZ_WOOD_FIRED_OVEN.asItem(),
                        ObjectRegistry.QUARTZ_STOVE.asItem(),
                        ObjectRegistry.QUARTZ_KITCHEN_SINK.asItem()


                )).build("candlelight", GUIIcon.of(() -> new ItemStack(ObjectRegistry.HEARTH)));
        ((TabbedItemGroup) Vinery.CREATIVE_TAB).getTabs().add(tab);
        tab.addToGroup(((TabbedItemGroup) Vinery.CREATIVE_TAB));

        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new CandlelightIdentifier("apple_leaves"), container, ResourcePackActivationType.DEFAULT_ENABLED);
        });
        ScreenHandlerTypes.init();
        ConfiguredFeatures.init();
        CandlelightEntityTypes.init();
        ModTaskListProvider.init();
        ModMemoryModuleType.init();
        ModPointOfInterestTypes.init();
        ModTaskListProvider.init();

        CandlelightMessages.registerC2SPackets();
    }
}