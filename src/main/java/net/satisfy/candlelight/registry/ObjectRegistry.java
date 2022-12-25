package net.satisfy.candlelight.registry;

import com.mojang.datafixers.util.Pair;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.block.*;
import daniking.vinery.item.DrinkBlockBigItem;
import daniking.vinery.item.DrinkBlockItem;
import daniking.vinery.registry.VinerySoundEvents;
import daniking.vinery.util.VineryFoodComponent;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.CandlelightIdentifier;
import net.satisfy.candlelight.block.CakeBlock;
import net.satisfy.candlelight.block.DecorationBlock;
import net.satisfy.candlelight.block.SofaBlock;
import net.satisfy.candlelight.item.IngredientItem;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static daniking.vinery.registry.ObjectRegistry.*;


public class ObjectRegistry {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    //public static final  Item     TOMATO_SEEDS = register("tomato_seeds", new TomatoCropSeedItem(TOMATO_CROP, getSettings()));
    // public static final  Block    TOMATO_CROP = register("tomato_crop", new Bush(getBushSettings()), false);
    public static final  Item     TOMATO = register("tomato", new IngredientItem(getSettings().food(FoodComponents.APPLE)));
    public static final  Block    TOMATO_CRATE = register("tomato_crate", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    //public static final  Item     BROCCOLI_SEEDS = register("broccoli_seeds", new TomatoBushSeedItem(BROCCOLI_CROP, getSettings()));
    // public static final  Block    BROCCOLI_CROP = register("broccoli_crop", new Bush(getBushSettings()), false);
    //public static final  Item     BROCCOLI = register("broccoli", new IngredientItem().food(FoodComponents.APPLE))));
    public static final  Block    BROCCOLI_CRATE = register("broccoli_crate", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    //public static final  Item     STRAWBERRY_SEEDS = register("strawberry_seeds", new StrawberryCropSeedItem(TOMATO_BUSH, getSettings()));
    // public static final  Block    STRAWBERRY_CROP = register("strawberry_crop", new Bush(getBushSettings()), false);
    // public static final  Item     STRAWBERRY = register("strawberry", new IngredientItem().food(FoodComponents.SWEET_BERRIES))))
    public static final  Block    STRAWBERRY_CRATE = register("strawberry_crate", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    // public static final  Block    SOFA = register("sofa", new SofaBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)));
    public static final  Block    CHAIR = register("chair", new ChairBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)));
    public static final  Block    TABLE = register("table", new TableBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS)));
    public static final  Block    WOOD_FIRED_OVEN = register("wood_fired_oven", new WoodFiredOvenBlock(FabricBlockSettings.copyOf(Blocks.BRICKS).luminance(state -> state.get(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final  Block    STOVE = register("stove", new StoveBlock(FabricBlockSettings.copyOf(Blocks.BRICKS).luminance(12)));
    public static final  Block    KITCHEN_SINK = register("kitchen_sink", new KitchenSinkBlock(FabricBlockSettings.copy(Blocks.STONE).nonOpaque()));
    public static final  Block    DRAWER = register("drawer", new WineRackStorageBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD), VinerySoundEvents.WINE_RACK_3_OPEN, VinerySoundEvents.WINE_RACK_3_CLOSE));
    public static final  Block    CABINET = register("cabinet", new WineRackStorageBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD), VinerySoundEvents.WINE_RACK_5_OPEN, VinerySoundEvents.WINE_RACK_5_CLOSE));
    // public static final  Block    SIDEBOARD = register("sideboard", new WineRackStorageBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD), VinerySoundEvents.WINE_RACK_3_OPEN, VinerySoundEvents.WINE_RACK_3_CLOSE));
    public static final  Block    FLOORBOARD = register("floorboard", new Block(FabricBlockSettings.copy(CHERRY_PLANKS)));
    // public static final  Block    CAKE_STAND = register("cake_stand", new CakeDisplayBlock
    public static final  Block    COOKING_POT = register("cooking_pot", new CookingPotBlock(FabricBlockSettings.of(Material.METAL).nonOpaque()));
   // public static final  Block    COOKING_PAN = register("cooking_pan", new CookingPanBlock
   // public static final  Block    TRAY = register("tray", new TrayDisplayBlock;
   // public static final  Block    PLATE = register("plate", new PlateDisplayBlock;
   // public static final  Block    NAPKIN = register("napkin", new PlateDisplayBlock();
   // public static final  Block    WINE_GLASS = register("wine_glass", new PlatedisplayBlock();
    public static final  Item     BUTTER = register("butter", new IngredientItem(getSettings()));
    public static final  Item     CHOCOLATE = register("chocolate", new IngredientItem(getSettings().food(FoodComponents.COOKIE)));
    public static final  Item     MOZZARELLA = register("mozzarella", new IngredientItem(getSettings().food(FoodComponents.BREAD)));
    public static final  Item     TOMATO_SOUP = register("tomato_soup", new IngredientItem(getSettings().food(FoodComponents.BEETROOT_SOUP)));
    public static final  Item     BEETROOT_SALAD = register("beetroot_salat", new Item(getSettings().food(FoodComponents.BEETROOT_SOUP)));
    public static final  Item     PASTA = register("pasta", new Item(getSettings().food(FoodComponents.COOKED_MUTTON)));
    public static final  Item     BEEF_TARTARE = register("beef_tartare", new Item(getSettings().food(FoodComponents.COOKED_BEEF)));
    // public static final  Block    TOMATO_MOZZARELLA_SALAT = register("tomato_mozzarella_salat", new MealBlock
    public static final  Item     BROCCOLI_BEEF = register("broccoli_beef", new Item(getSettings().food(FoodComponents.GOLDEN_CARROT)));
    public static final  Item     BROCCOLI_TOMATO = register("broccoli_tomato", new Item(getSettings().food(FoodComponents.GOLDEN_CARROT)));
    // public static final  Item     ROASTBEEF_CARROTS = register("roastbeef_carrots", new MealBlock;
    public static final  Item     SALMON = register("salmon", new Item(getSettings().food(FoodComponents.GOLDEN_CARROT)));
    public static final  Item     VEGGIE_PLATE = register("veggie_plate", new Item(getSettings().food(FoodComponents.COOKED_BEEF)));
    public static final  Item     PORK_RIB = register("pork_rib", new Item(getSettings().food(FoodComponents.GOLDEN_CARROT)));
    // public static final  Block    LASAGNA = register("lasagna", new MealBlock
    // public static final  Block    BEEF_WELLINGTON = register("beef_wellington", new MealBlock
    public static final  Item     MASHED_POTATOES = register("mashed_potatoes", new Item(getSettings().food(FoodComponents.GOLDEN_CARROT)));
    public static final  Item     FRICASSE = register("fricasse", new Item(getSettings().food(FoodComponents.GOLDEN_APPLE)));
    public static final  Item     FRIED_EGG = register("fried_egg", new Item(getSettings().food(FoodComponents.BREAD)));
    public static final  Block    STRAWBERRY_JAM = register("strawberry_jam", new CherryJamBlock(FabricBlockSettings.of(Material.GLASS).breakInstantly().nonOpaque()));
    public static final  Item     VINEGAR = register("vinegar", new IngredientItem(getSettings()));
    public static final  Block    RED_WINE = registerWine("clark_wine", new WineBottleBlock(getWineSettings()), StatusEffects.FIRE_RESISTANCE);
    public static final  Block    PRAETORIAN_WINE = registerBigWine("chenet_wine", new ChenetBottleBlock(getWineSettings()), StatusEffects.JUMP_BOOST);
    public static final  Item     PANCAKE = register("pancake", new Item(getSettings().food(FoodComponents.BAKED_POTATO)));
    public static final  Item     WAFFLE = register("waffle", new Item(getSettings().food(FoodComponents.BAKED_POTATO)));
    public static final  Item     STRAWBERRY_GLAZED_COOKIE = register("strawberry_glazed_cookie", new Item(getSettings().food(FoodComponents.BREAD)));
    public static final  Item     CROISSANT = register("croissant", new Item(getSettings().food(FoodComponents.BREAD)));
    public static final  Item     BUNDT_CAKE = register("bundt_cake", new Item(getSettings().food(FoodComponents.COOKED_BEEF)));
    public static final  Block    SESAM_BREAD = register("sesam_bread", new BreadBlock(AbstractBlock.Settings.copy(Blocks.CAKE).nonOpaque()));
    public static final  Item     STRAWBERRY_CHOCOLATE = register("strawberry_chocolate", new Item(getSettings().food(FoodComponents.BREAD)));
    public static final  Item     STRAWBERRY_CAKE_SLICE = register("strawberry_cake_slice", new Item(getSettings().food(FoodComponents.BREAD)));
    public static final  Block    STRAWBERRY_CAKE = register("strawberry_cake", new CakeBlock((FabricBlockSettings.copyOf(Blocks.CAKE)), ObjectRegistry.STRAWBERRY_CAKE_SLICE));
    public static final  Item     STRAWBERRY_MILKSHAKE = register("strawberry_milkshake", new Item(getSettings().food(FoodComponents.BEETROOT_SOUP)));
    public static final  Item     STRAWBERRY_ICECREAM = register("strawberry_icecream", new Item(getSettings().food(FoodComponents.BEETROOT_SOUP)));
    public static final  Item     SWEETBERRY_CAKE_SLICE = register("sweetberry_cake_slice", new Item(getSettings().food(FoodComponents.BREAD)));
    public static final  Block    SWEETBERRY_CAKE = register("sweetberry_cake", new CakeBlock((FabricBlockSettings.copyOf(Blocks.CAKE)), ObjectRegistry.SWEETBERRY_CAKE_SLICE));
    public static final  Item     SWEETBERRY_MILKSHAKE = register("sweetberry_milkshake", new Item(getSettings().food(FoodComponents.BEETROOT_SOUP)));
    public static final  Item     SWEETBERRY_ICECREAM = register("sweetberry_icecream", new Item(getSettings().food(FoodComponents.BEETROOT_SOUP)));
    public static final  Item     CHOCOLATE_CAKE_SLICE = register("chocolate_cake_slice", new Item(getSettings().food(FoodComponents.BREAD)));
    public static final  Block    CHOCOLATE_CAKE = register("chocolate_cake", new CakeBlock((FabricBlockSettings.copyOf(Blocks.CAKE)), ObjectRegistry.CHOCOLATE_CAKE_SLICE));
    public static final  Item     CHOCOLATE_MILKSHAKE = register("chocolate_milkshake", new Item(getSettings().food(FoodComponents.BEETROOT_SOUP)));
    public static final  Item     CHOCOLATE_ICECREAM = register("chocolate_icecream", new Item(getSettings().food(FoodComponents.BEETROOT_SOUP)));
    public static final  Block    TABLE_SIGN = register("table_sign", new Block(FabricBlockSettings.of(Material.DECORATION)));
    public static final  Block    STREET_SIGN = register("street_sign", new Block(FabricBlockSettings.of(Material.DECORATION)));
    public static final  Block    PAINTING = register("painting", new DecorationBlock(FabricBlockSettings.of(Material.DECORATION).noCollision()));
    public static final  Block    HEARTH = register("hearth", new DecorationBlock(FabricBlockSettings.of(Material.DECORATION).noCollision()));
    //  public static final  Block    CANDLE = register("candle", new CandleBlock(FabricBlockSettings.of(Material.DECORATION).noCollision()));
    // public static final  Block    JEWELRY_BOX = register("jewelry_box", new Block
    // public static final  Block    CHOCOLATE_BOX = register("chocolate_box", new Block


    public static final  Block    ROSE = register("rose", new FlowerBlock(StatusEffect.byRawId(6), 1, FabricBlockSettings.copyOf(Blocks.DANDELION)));

            //letter items
    public static final  Item     NOTE_PAPER = register("note_paper", new Item(getSettings()));
    public static final  Item     NOTE_PAPER_WRITEABLE = register("note_paper_writeable", new Item(getSettings()));
    public static final  Item     NOTE_PAPER_WRITTEN = register("note_paper_written", new Item(getSettings()));
    public static final  Item     LETTER_OPEN = register("letter_open", new Item(getSettings()));
    public static final  Item     LETTER_CLOSED = register("letter_closed", new Item(getSettings()));
    public static final  Item     LOVE_LETTER = register("love_letter", new Item(getSettings()));

            //gold ring
    //   public static final  Item     GOLD_RING = register("gold_ring", new))




    public static final  Block    POTTED_ROSE = registerBlockWithoutItem("potted_rose",
            new FlowerPotBlock(ObjectRegistry.ROSE, FabricBlockSettings.copyOf(Blocks.POTTED_POPPY)));




    private static <T extends Block> T register(String path, T block) {
        return register(path, block, true);
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem) {
        return register(path, block, registerItem, settings -> {});
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem, Consumer<Item.Settings> consumer) {
        return register(path, block, registerItem, BlockItem::new, consumer);
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem, BiFunction<T, Item.Settings, ? extends BlockItem> function,  Consumer<Item.Settings> consumer) {
        final Identifier id = new VineryIdentifier(path);
        BLOCKS.put(id, block);
        if (registerItem) {
            ITEMS.put(id, function.apply(block, getSettings(consumer)));
        }
        return block;
    }


    private static <T extends Block> T registerWine(String path, T block, StatusEffect effect) {
        return register(path, block, true, DrinkBlockItem::new, settings -> settings.food(wineFoodComponent(effect)));
    }

    private static <T extends Block> T registerBigWine(String path, T block, StatusEffect effect) {
        return register(path, block, true, DrinkBlockBigItem::new, settings -> settings.food(wineFoodComponent(effect)));
    }

    private static FoodComponent wineFoodComponent(StatusEffect effect) {
        List<Pair<StatusEffectInstance, Float>> statusEffects = new ArrayList<>();
        statusEffects.add(new Pair<>(new StatusEffectInstance(effect, 45 * 20), 1.0f));
        return new VineryFoodComponent(statusEffects);
    }

    private static <T extends Item> T register(String path, T item) {
        final Identifier id = new VineryIdentifier(path);
        ITEMS.put(id, item);
        return item;
    }

    public static void init() {
        for (Map.Entry<Identifier, Block> entry : BLOCKS.entrySet()) {
            Registry.register(Registry.BLOCK, entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Identifier, Item> entry : ITEMS.entrySet()) {
            Registry.register(Registry.ITEM, entry.getKey(), entry.getValue());
        }
        FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();
        flammableRegistry.add(FLOORBOARD, 5, 20);
    }


    private static Item.Settings getSettings(Consumer<Item.Settings> consumer) {
        Item.Settings settings = new Item.Settings();
        consumer.accept(settings);
        return settings;
    }

    private static Item.Settings getSettings() {
        return getSettings(settings -> {});
    }

    private static Block.Settings getVineSettings() {
        return FabricBlockSettings.copyOf(Blocks.VINE);
    }

    private static Block.Settings getBushSettings() {
        return FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH);
    }


    private static AbstractBlock.Settings getGrassSettings() {
        return FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).nonOpaque();
    }

    private static AbstractBlock.Settings getGrapevineSettings() {
        return FabricBlockSettings.of(Material.WOOD).strength(2.0F).ticksRandomly().sounds(BlockSoundGroup.WOOD);
    }

    private static AbstractBlock.Settings getLogBlockSettings() {
        return AbstractBlock.Settings.of(Material.WOOD).strength(2.0F).sounds(BlockSoundGroup.WOOD);
    }

    private static AbstractBlock.Settings getSlabSettings() {
        return getLogBlockSettings().resistance(3.0F);
    }

    private static AbstractBlock.Settings getWineSettings() {
        return AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque().breakInstantly();
    }

    public static List<ItemConvertible> getItemConvertibles() {
        List<ItemConvertible> list = new ArrayList<>();
        for (Block entry : BLOCKS.values()) {
            if(entry.asItem() != null){
                list.add(entry);
            }
        }
        list.addAll(ITEMS.values());
        return list;
    }

    public static Map<Identifier, Block> getBlocks() {
        return Collections.unmodifiableMap(BLOCKS);
    }

    public static Map<Identifier, Item> getItems() {
        return Collections.unmodifiableMap(ITEMS);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(Candlelight.MOD_ID, name), block);
    }

}
