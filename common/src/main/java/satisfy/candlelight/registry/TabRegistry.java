package satisfy.candlelight.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import satisfy.candlelight.Candlelight;
import satisfy.candlelight.config.CandlelightConfig;

@SuppressWarnings("unused")
public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Candlelight.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> CANDLELIGHT_TAB = TABS.register("candlelight", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> new ItemStack(ObjectRegistry.COOKING_POT.get()))
            .title(Component.translatable("creativetab.candlelight.tab_main"))
            .displayItems((parameters, output) -> {
                output.accept(ObjectRegistry.COOKING_POT.get());
                output.accept(ObjectRegistry.COOKING_PAN.get());
                output.accept(ObjectRegistry.TABLE_SET.get());
                output.accept(ObjectRegistry.TABLE_BOWL.get());
                output.accept(ObjectRegistry.CLOCHE.get());
                output.accept(ObjectRegistry.DRINKING_GLASS.get());
                output.accept(ObjectRegistry.GLASS.get());
                output.accept(ObjectRegistry.NAPKIN.get());
                output.accept(ObjectRegistry.DINNER_BELL.get());
                output.accept(ObjectRegistry.TOMATO_SOUP.get());
                output.accept(ObjectRegistry.MUSHROOM_SOUP.get());
                output.accept(ObjectRegistry.BEETROOT_SALAD.get());
                output.accept(ObjectRegistry.PASTA.get());
                output.accept(ObjectRegistry.BOLOGNESE.get());
                output.accept(ObjectRegistry.CHICKEN_TERIYAKI.get());
                output.accept(ObjectRegistry.LETTUCE_SALAD.get());
                output.accept(ObjectRegistry.CHOCOLATE.get());
                output.accept(ObjectRegistry.MOZZARELLA.get());
                output.accept(ObjectRegistry.BEEF_TARTARE.get());
                output.accept(ObjectRegistry.PASTA_BOLOGNESE.get());
                output.accept(ObjectRegistry.PASTA_LETTUCE.get());
                output.accept(ObjectRegistry.PANCAKE.get());
                output.accept(ObjectRegistry.LETTUCE_TOMATO.get());
                output.accept(ObjectRegistry.VEGGIE_PLATE.get());
                output.accept(ObjectRegistry.FRICASSE.get());
                output.accept(ObjectRegistry.TOMATO_MOZZARELLA_SALAD.get());
                output.accept(ObjectRegistry.PORK_RIBS.get());
                output.accept(ObjectRegistry.ROASTBEEF_CARROTS.get());
                output.accept(ObjectRegistry.LETTUCE_BEEF.get());
                output.accept(ObjectRegistry.TROPICAL_FISH_SUPREME.get());
                output.accept(ObjectRegistry.COOKED_BEEF.get());
                output.accept(ObjectRegistry.SALMON_WINE.get());
                output.accept(ObjectRegistry.CHICKEN_ALFREDO.get());
                output.accept(ObjectRegistry.CHICKEN.get());
                output.accept(ObjectRegistry.LASAGNA.get());
                output.accept(ObjectRegistry.BEEF_WELLINGTON.get());
                output.accept(ObjectRegistry.COOKING_HAT.get());
                output.accept(ObjectRegistry.CHEFS_JACKET.get());
                output.accept(ObjectRegistry.CHEFS_PANTS.get());
                output.accept(ObjectRegistry.CHEFS_BOOTS.get());
                output.accept(ObjectRegistry.GOLD_RING.get());
                output.accept(ObjectRegistry.JEWELRY_BOX.get());
                output.accept(ObjectRegistry.PAINTING.get());
                output.accept(ObjectRegistry.HEARTH.get());
                output.accept(ObjectRegistry.ROSE.get());
                output.accept(ObjectRegistry.CHOCOLATE_BOX.get());
                output.accept(ObjectRegistry.TABLE_SIGN.get());
                output.accept(ObjectRegistry.TYPEWRITER_IRON.get());
                output.accept(ObjectRegistry.TYPEWRITER_COPPER.get());
                output.accept(ObjectRegistry.NOTE_PAPER.get());
                output.accept(ObjectRegistry.NOTE_PAPER_WRITEABLE.get());
                output.accept(ObjectRegistry.LETTER_OPEN.get());
                output.accept(ObjectRegistry.LOVE_LETTER_OPEN.get());
                output.accept(ObjectRegistry.CANDLELIGHT_STANDARD.get());
            })
            .build());

    public static final RegistrySupplier<CreativeModeTab> CANDLELIGHT_FURNITURE_TAB = TABS.register("candlelight_furniture", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1)
            .icon(() -> new ItemStack(ObjectRegistry.PAINTING.get()))
            .title(Component.translatable("creativetab.candlelight.tab_furniture"))
            .displayItems((parameters, output) -> {
                output.accept(ObjectRegistry.FLOORBOARD.get());
                output.accept(ObjectRegistry.DRAWER.get());
                output.accept(ObjectRegistry.CABINET.get());
                output.accept(ObjectRegistry.SIDEBOARD.get());
                output.accept(ObjectRegistry.CHAIR.get());
                output.accept(ObjectRegistry.SOFA.get());
                output.accept(ObjectRegistry.TABLE.get());
                output.accept(ObjectRegistry.LAMP.get());
                output.accept(ObjectRegistry.SIDE_TABLE.get());
                output.accept(ObjectRegistry.COBBLESTONE_STOVE.get());
                output.accept(ObjectRegistry.COBBLESTONE_KITCHEN_SINK.get());
                output.accept(ObjectRegistry.COBBLESTONE_COUNTER.get());
                output.accept(ObjectRegistry.OAK_CABINET.get());
                output.accept(ObjectRegistry.OAK_DRAWER.get());
                output.accept(ObjectRegistry.OAK_TABLE.get());
                output.accept(ObjectRegistry.OAK_CHAIR.get());
                output.accept(ObjectRegistry.OAK_SHELF.get());
                output.accept(ObjectRegistry.OAK_BIG_TABLE.get());
                output.accept(ObjectRegistry.SANDSTONE_STOVE.get());
                output.accept(ObjectRegistry.SANDSTONE_KITCHEN_SINK.get());
                output.accept(ObjectRegistry.SANDSTONE_COUNTER.get());
                output.accept(ObjectRegistry.BIRCH_CABINET.get());
                output.accept(ObjectRegistry.BIRCH_DRAWER.get());
                output.accept(ObjectRegistry.BIRCH_TABLE.get());
                output.accept(ObjectRegistry.BIRCH_CHAIR.get());
                output.accept(ObjectRegistry.BIRCH_SHELF.get());
                output.accept(ObjectRegistry.BIRCH_BIG_TABLE.get());
                output.accept(ObjectRegistry.STONE_BRICKS_STOVE.get());
                output.accept(ObjectRegistry.STONE_BRICKS_KITCHEN_SINK.get());
                output.accept(ObjectRegistry.STONE_BRICKS_COUNTER.get());
                output.accept(ObjectRegistry.SPRUCE_CABINET.get());
                output.accept(ObjectRegistry.SPRUCE_DRAWER.get());
                output.accept(ObjectRegistry.SPRUCE_TABLE.get());
                output.accept(ObjectRegistry.SPRUCE_CHAIR.get());
                output.accept(ObjectRegistry.SPRUCE_SHELF.get());
                output.accept(ObjectRegistry.SPRUCE_BIG_TABLE.get());
                output.accept(ObjectRegistry.DEEPSLATE_STOVE.get());
                output.accept(ObjectRegistry.DEEPSLATE_KITCHEN_SINK.get());
                output.accept(ObjectRegistry.DEEPSLATE_COUNTER.get());
                output.accept(ObjectRegistry.DARK_OAK_CABINET.get());
                output.accept(ObjectRegistry.DARK_OAK_DRAWER.get());
                output.accept(ObjectRegistry.DARK_OAK_TABLE.get());
                output.accept(ObjectRegistry.DARK_OAK_CHAIR.get());
                output.accept(ObjectRegistry.DARK_OAK_SHELF.get());
                output.accept(ObjectRegistry.DARK_OAK_BIG_TABLE.get());
                output.accept(ObjectRegistry.GRANITE_STOVE.get());
                output.accept(ObjectRegistry.GRANITE_KITCHEN_SINK.get());
                output.accept(ObjectRegistry.GRANITE_COUNTER.get());
                output.accept(ObjectRegistry.ACACIA_CABINET.get());
                output.accept(ObjectRegistry.ACACIA_DRAWER.get());
                output.accept(ObjectRegistry.ACACIA_TABLE.get());
                output.accept(ObjectRegistry.ACACIA_CHAIR.get());
                output.accept(ObjectRegistry.ACACIA_SHELF.get());
                output.accept(ObjectRegistry.ACACIA_BIG_TABLE.get());
                output.accept(ObjectRegistry.END_STOVE.get());
                output.accept(ObjectRegistry.END_KITCHEN_SINK.get());
                output.accept(ObjectRegistry.END_COUNTER.get());
                output.accept(ObjectRegistry.JUNGLE_CABINET.get());
                output.accept(ObjectRegistry.JUNGLE_DRAWER.get());
                output.accept(ObjectRegistry.JUNGLE_TABLE.get());
                output.accept(ObjectRegistry.JUNGLE_CHAIR.get());
                output.accept(ObjectRegistry.JUNGLE_SHELF.get());
                output.accept(ObjectRegistry.JUNGLE_BIG_TABLE.get());
                output.accept(ObjectRegistry.MUD_STOVE.get());
                output.accept(ObjectRegistry.MUD_KITCHEN_SINK.get());
                output.accept(ObjectRegistry.MUD_COUNTER.get());
                output.accept(ObjectRegistry.MANGROVE_CABINET.get());
                output.accept(ObjectRegistry.MANGROVE_DRAWER.get());
                output.accept(ObjectRegistry.MANGROVE_TABLE.get());
                output.accept(ObjectRegistry.MANGROVE_CHAIR.get());
                output.accept(ObjectRegistry.MANGROVE_SHELF.get());
                output.accept(ObjectRegistry.MANGROVE_BIG_TABLE.get());
                output.accept(ObjectRegistry.QUARTZ_STOVE.get());
                output.accept(ObjectRegistry.QUARTZ_KITCHEN_SINK.get());
                output.accept(ObjectRegistry.QUARTZ_COUNTER.get());
                output.accept(ObjectRegistry.WARPED_CABINET.get());
                output.accept(ObjectRegistry.WARPED_DRAWER.get());
                output.accept(ObjectRegistry.WARPED_TABLE.get());
                output.accept(ObjectRegistry.WARPED_CHAIR.get());
                output.accept(ObjectRegistry.WARPED_SHELF.get());
                output.accept(ObjectRegistry.WARPED_BIG_TABLE.get());
                output.accept(ObjectRegistry.RED_NETHER_BRICKS_STOVE.get());
                output.accept(ObjectRegistry.RED_NETHER_BRICKS_KITCHEN_SINK.get());
                output.accept(ObjectRegistry.RED_NETHER_BRICKS_COUNTER.get());
                output.accept(ObjectRegistry.CRIMSON_CABINET.get());
                output.accept(ObjectRegistry.CRIMSON_DRAWER.get());
                output.accept(ObjectRegistry.CRIMSON_TABLE.get());
                output.accept(ObjectRegistry.CRIMSON_CHAIR.get());
                output.accept(ObjectRegistry.CRIMSON_SHELF.get());
                output.accept(ObjectRegistry.CRIMSON_BIG_TABLE.get());
                output.accept(ObjectRegistry.BASALT_STOVE.get());
                output.accept(ObjectRegistry.BASALT_KITCHEN_SINK.get());
                output.accept(ObjectRegistry.BASALT_COUNTER.get());
                output.accept(ObjectRegistry.CHERRY_CABINET.get());
                output.accept(ObjectRegistry.CHERRY_DRAWER.get());
                output.accept(ObjectRegistry.CHERRY_TABLE.get());
                output.accept(ObjectRegistry.CHERRY_CHAIR.get());
                output.accept(ObjectRegistry.CHERRY_SHELF.get());
                output.accept(ObjectRegistry.CHERRY_BIG_TABLE.get());
                output.accept(ObjectRegistry.BAMBOO_STOVE.get());
                output.accept(ObjectRegistry.BAMBOO_KITCHEN_SINK.get());
                output.accept(ObjectRegistry.BAMBOO_COUNTER.get());
                output.accept(ObjectRegistry.BAMBOO_CABINET.get());
                output.accept(ObjectRegistry.BAMBOO_DRAWER.get());
                output.accept(ObjectRegistry.BAMBOO_TABLE.get());
                output.accept(ObjectRegistry.BAMBOO_CHAIR.get());
                output.accept(ObjectRegistry.BAMBOO_SHELF.get());
                output.accept(ObjectRegistry.BAMBOO_BIG_TABLE.get());
            })
            .build());

    public static void init() {
        TABS.register();
    }
}
