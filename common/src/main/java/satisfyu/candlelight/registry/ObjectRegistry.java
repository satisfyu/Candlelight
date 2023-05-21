package satisfyu.candlelight.registry;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.Util;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.block.CakeBlock;
import satisfyu.candlelight.block.LanternBlock;
import satisfyu.candlelight.block.*;
import satisfyu.candlelight.block.crops.*;
import satisfyu.candlelight.food.CandlelightFoods;
import satisfyu.candlelight.food.EffectFoodBlockItem;
import satisfyu.candlelight.item.*;
import satisfyu.candlelight.util.CandlelightIdentifier;
import satisfyu.candlelight.world.feature.CandlelightConfiguredFeatures;
import satisfyu.vinery.block.*;
import satisfyu.vinery.block.storage.FourBottleStorageBlock;
import satisfyu.vinery.block.storage.NineBottleStorageBlock;
import satisfyu.vinery.item.DrinkBlockBigItem;
import satisfyu.vinery.item.DrinkBlockItem;
import satisfyu.vinery.item.GrapeBushSeedItem;
import satisfyu.vinery.item.food.EffectFoodItem;
import satisfyu.vinery.registry.VineryEffects;
import satisfyu.vinery.util.GrapevineType;
import satisfyu.vinery.util.VineryFoodComponent;
import satisfyu.vinery.util.generators.ConfiguredFeatureSaplingGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class ObjectRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Candlelight.MOD_ID, Registry.ITEM_REGISTRY);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Candlelight.MOD_ID, Registry.BLOCK_REGISTRY);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();
    
    public static final ResourceLocation NOTE_PAPER_WRITTEN_PACKET_IDENTIFIER = new CandlelightIdentifier("note_paper_written");
    
    public static final RegistrySupplier<Block> TOMATO_CROP = registerWithoutItem("tomato_crop", () -> new TomatoCropBlock( getBushSettings()));
    public static final RegistrySupplier<Item> TOMATO_SEEDS = registerItem("tomato_seeds", () -> new GrapeBushSeedItem(TOMATO_CROP.get(), getSettings(), GrapevineType.TOMATO));
    public static final RegistrySupplier<Item> TOMATO = registerItem("tomato", () -> new Item(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Block> TOMATO_CRATE = registerWithItem("tomato_crate", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BROCCOLI_CROP = registerWithoutItem("broccoli_crop", () -> new BroccoliCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistrySupplier<Item> BROCCOLI_SEEDS = registerItem("broccoli_seeds", () -> new SeedItemBlock(BROCCOLI_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> BROCCOLI = registerItem("broccoli", () -> new IngredientItem(getSettings().food(Foods.POTATO)));
    public static final RegistrySupplier<Block> BROCCOLI_CRATE = registerWithItem("broccoli_crate", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> STRAWBERRY_CROP = registerWithoutItem("strawberry_crop", () -> new StrawberryCropBlock(getBushSettings()));
    public static final RegistrySupplier<Item> STRAWBERRY_SEEDS = registerItem("strawberry_seeds", () -> new SeedItemBlock(STRAWBERRY_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> STRAWBERRY = registerItem("strawberry", () -> new IngredientItem(getSettings().food(Foods.BEETROOT)));
    public static final RegistrySupplier<Block> STRAWBERRY_CRATE = registerWithItem("strawberry_crate", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> STRAWBERRY_WILD_TAIGA = registerWithoutItem("strawberry_wild_taiga", () -> new WildBush(getBushSettings()));
    public static final RegistrySupplier<Block> STRAWBERRY_WILD_JUNGLE = registerWithoutItem("strawberry_wild_jungle", () -> new WildBushJungle(getBushSettings()));
    public static final RegistrySupplier<Block> TOMATOES_WILD = registerWithoutItem("tomatoes_wild", () -> new WildBushTomato(getBushSettings()));
    public static final RegistrySupplier<Block> WILD_BROCCOLI = registerWithoutItem("wild_broccoli", () -> new WildBroccoli(getBushSettings()));
    public static final RegistrySupplier<Block> CHAIR = registerWithItem("chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> TABLE = registerWithItem("table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> SOFA = registerWithItem("sofa", () -> new SofaBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> LAMP = registerWithItem("lamp", () -> new LanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel(s -> s.getValue(LanternBlock.LUMINANCE) ? 15 : 0).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> DRAWER = registerWithItem("drawer", () -> new CandlelightWineRackStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CABINET = registerWithItem("cabinet", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SIDEBOARD = registerWithItem("sideboard", () -> new SideBoardBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> FLOORBOARD = registerWithItem("floorboard", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> WINE_STATION = registerWithItem("wine_station", () -> new WineStationBlock(BlockBehaviour.Properties.of(Material.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CAKE_STAND = registerWithItem("cake_stand", () -> new CakeStandBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> TRAY = registerWithItem("tray", () -> new TrayBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> COOKING_POT = registerWithItem("cooking_pot", () -> new CookingPotBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()){});
    public static final RegistrySupplier<Block> COOKING_PAN = registerWithItem("cooking_pan", () -> new CookingPanBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()));
    public static final RegistrySupplier<Block> TABLE_SET = registerWithItem("table_set", () -> new TableSetBlock(BlockBehaviour.Properties.copy(COOKING_POT.get())));
    public static final RegistrySupplier<Item> GLASS = registerItem("glass", () -> new TableSetItem(getSettings()));
    public static final RegistrySupplier<Item> NAPKIN = registerItem("napkin", () -> new TableSetItem(getSettings()));
    public static final RegistrySupplier<Item> BUTTER = registerItem("butter", () -> new IngredientItem(getSettings()));
    public static final RegistrySupplier<Item> MOZZARELLA = registerItem("mozzarella", () -> new IngredientItem(getSettings().food(Foods.BREAD)));
    public static final RegistrySupplier<Item> TOMATO_SOUP = registerItem("tomato_soup", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.TOMATO_SOUP), 1));
    public static final RegistrySupplier<Item> MUSHROOM_SOUP = registerItem("mushroom_soup", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.MUSHROOM_SOUP), 1));
    public static final RegistrySupplier<Item> BEETROOT_SALAD = registerItem("beetroot_salad", () -> new EffectFoodItem(getSettings().food(Foods.BEETROOT_SOUP), 1));
    public static final RegistrySupplier<Item> PASTA = registerItem("pasta", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.PASTA), 2));
    public static final RegistrySupplier<Item> BOLOGNESE = registerItem("bolognese", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.COOKED_BEEF), 1));
    public static final RegistrySupplier<Item> BEEF_TARTARE = registerItem("beef_tartare", () -> new Item(getSettings().food(Foods.COOKED_BEEF)));
    public static final RegistrySupplier<Item> COOKED_BEEF = registerItem("cooked_beef", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.COOKED_BEEF), 2));
    public static final RegistrySupplier<Item> BROCCOLI_BEEF = registerItem("broccoli_beef", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.BROCCOLI_BEEF), 2));
    public static final RegistrySupplier<Block> BROCCOLI_TOMATO_BLOCK = registerWithoutItem("broccoli_tomato_block", () -> new EffectFoodTrayBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2, CandlelightFoods.BROCCOLI_TOMATO));
    public static final RegistrySupplier<Item> BROCCOLI_TOMATO = registerItem("broccoli_tomato", () -> new EffectFoodBlockItem(BROCCOLI_TOMATO_BLOCK.get(), getSettings().food(CandlelightFoods.BROCCOLI_TOMATO), 2));
    public static final RegistrySupplier<Item> SALMON_WINE = registerItem("salmon_wine", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.SALMON_ON_WHITE_WINE), 2));
    public static final RegistrySupplier<Item> VEGGIE_PLATE = registerItem("veggie_plate", () -> new EffectFoodItem(getSettings().food(Foods.COOKED_BEEF), 2));
    public static final RegistrySupplier<Block> PORK_RIBS_BLOCK = registerWithoutItem("pork_ribs_block", () -> new EffectFoodBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2, CandlelightFoods.PORK_RIBS));
    public static final RegistrySupplier<Item> PORK_RIBS = registerItem("pork_ribs", () -> new EffectFoodBlockItem(PORK_RIBS_BLOCK.get(), getSettings().food(CandlelightFoods.PORK_RIBS), 2));
    public static final RegistrySupplier<Item> MASHED_POTATOES = registerItem("mashed_potatoes", () -> new EffectFoodItem(getSettings().food(Foods.GOLDEN_CARROT), 2));
    public static final RegistrySupplier<Item> FRICASSE = registerItem("fricasse", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.FRICASSE), 2));
    public static final RegistrySupplier<Item> CHICKEN = registerItem("chicken", () -> new EffectFoodItem(getSettings().food(Foods.GOLDEN_CARROT), 2));
    public static final RegistrySupplier<Block> TOMATO_MOZZARELLA_BLOCK = registerWithoutItem("tomato_mozzarella_block", () -> new EffectFoodTrayBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2, CandlelightFoods.TOMATO_MOZZARELLA_SALAD));
    public static final RegistrySupplier<Item> TOMATO_MOZZARELLA_SALAD = registerItem("tomato_mozzarella_salad", () -> new EffectFoodBlockItem(TOMATO_MOZZARELLA_BLOCK.get(), getSettings().food(CandlelightFoods.TOMATO_MOZZARELLA_SALAD), 2));
    public static final RegistrySupplier<Block> LASAGNA_BLOCK = registerWithoutItem("lasagne_block", () -> new EffectFoodBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 3, CandlelightFoods.LASAGNE));
    public static final RegistrySupplier<Item> LASAGNA = registerItem("lasagne", () -> new EffectFoodBlockItem(LASAGNA_BLOCK.get(), getSettings().food(CandlelightFoods.LASAGNE), 3));
    public static final RegistrySupplier<Item> ROASTBEEF_CARROTS = registerItem("roastbeef_carrots", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.ROASTBEEF_CARROTS), 2));
    public static final RegistrySupplier<Block> BEEF_WELLINGTON_BLOCK = registerWithoutItem("beef_wellington_block", () -> new EffectFoodBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2, CandlelightFoods.BEEF_WELLINGTON));
    public static final RegistrySupplier<Item> BEEF_WELLINGTON = registerItem("beef_wellington", () -> new EffectFoodBlockItem(BEEF_WELLINGTON_BLOCK.get(), getSettings().food(CandlelightFoods.BEEF_WELLINGTON), 2));
    public static final RegistrySupplier<Item> VINEGAR = registerItem("vinegar", () -> new IngredientItem(getSettings()));
    //public static final RegistrySupplier<Block> RED_WINE = registerWine("red_wine", () -> new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_FIRE_RESISTANCE);
    //public static final RegistrySupplier<Block> PRAETORIAN_WINE = registerBigWine("praetorian_wine", () -> new WineBottleBlock(getWineSettings(), 2), VineryEffects.IMPROVED_SPEED);
    public static final RegistrySupplier<Block> STRAWBERRY_JAM = registerWithItem("strawberry_jam", () -> new StackableBlock(BlockBehaviour.Properties.of(Material.GLASS).instabreak().noOcclusion().sound(SoundType.GLASS)));
    public static final RegistrySupplier<Item> PANCAKE = registerItem("pancake", () -> new Item(getSettings().food(Foods.BAKED_POTATO)));
    public static final RegistrySupplier<Item> WAFFLE = registerItem("waffle", () -> new Item(getSettings().food(Foods.BAKED_POTATO)));
    public static final RegistrySupplier<Item> STRAWBERRY_GLAZED_COOKIE = registerItem("strawberry_glazed_cookie", () -> new Item(getSettings().food(Foods.BREAD)));
    public static final RegistrySupplier<Item> SWEETBERRY_GLAZED_COOKIE = registerItem("sweetberry_glazed_cookie", () -> new Item(getSettings().food(Foods.BREAD)));
    public static final RegistrySupplier<Item> CHOCOLATE_GLAZED_COOKIE = registerItem("chocolate_glazed_cookie", () -> new Item(getSettings().food(Foods.BREAD)));
    public static final RegistrySupplier<Item> CROISSANT = registerItem("croissant", () -> new Item(getSettings().food(Foods.BREAD)));
    public static final RegistrySupplier<Item> BUNDT_CAKE = registerItem("bundt_cake", () -> new Item(getSettings().food(Foods.COOKED_BEEF)));
    public static final RegistrySupplier<Item> CHOCOLATE = registerItem("chocolate", () -> new IngredientItem(getSettings().food(Foods.COOKIE)));
    public static final RegistrySupplier<Item> STRAWBERRY_CHOCOLATE = registerItem("strawberry_chocolate", () -> new Item(getSettings().food(Foods.BREAD)));
    public static final RegistrySupplier<Item> STRAWBERRY_CAKE_SLICE = registerItem("strawberry_cake_slice", () -> new Item(getSettings().food(Foods.BREAD)));
    public static final RegistrySupplier<Block> STRAWBERRY_CAKE = registerWithItem("strawberry_cake", () -> new CakeBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), ObjectRegistry.STRAWBERRY_CAKE_SLICE));
    public static final RegistrySupplier<Item> STRAWBERRY_MILKSHAKE = registerItem("strawberry_milkshake", () -> new Item(getSettings().food(Foods.BEETROOT_SOUP)));
    public static final RegistrySupplier<Item> STRAWBERRY_ICECREAM = registerItem("strawberry_icecream", () -> new Item(getSettings().food(Foods.BEETROOT_SOUP)));
    public static final RegistrySupplier<Item> SWEETBERRY_CAKE_SLICE = registerItem("sweetberry_cake_slice", () -> new Item(getSettings().food(Foods.BREAD)));
    public static final RegistrySupplier<Block> SWEETBERRY_CAKE = registerWithItem("sweetberry_cake", () -> new CakeBlock((BlockBehaviour.Properties.copy(Blocks.CAKE)), ObjectRegistry.SWEETBERRY_CAKE_SLICE));
    public static final RegistrySupplier<Item> SWEETBERRY_MILKSHAKE = registerItem("sweetberry_milkshake", () -> new Item(getSettings().food(Foods.BEETROOT_SOUP)));
    public static final RegistrySupplier<Item> SWEETBERRY_ICECREAM = registerItem("sweetberry_icecream", () -> new Item(getSettings().food(Foods.BEETROOT_SOUP)));
    public static final RegistrySupplier<Item> CHOCOLATE_CAKE_SLICE = registerItem("chocolate_cake_slice", () -> new Item(getSettings().food(Foods.BREAD)));
    public static final RegistrySupplier<Block> CHOCOLATE_CAKE = registerWithItem("chocolate_cake", () -> new CakeBlock((BlockBehaviour.Properties.copy(Blocks.CAKE)), ObjectRegistry.CHOCOLATE_CAKE_SLICE));
    public static final RegistrySupplier<Item> CHOCOLATE_MILKSHAKE = registerItem("chocolate_milkshake", () -> new Item(getSettings().food(Foods.BEETROOT_SOUP)));
    public static final RegistrySupplier<Item> CHOCOLATE_ICECREAM = registerItem("chocolate_icecream", () -> new Item(getSettings().food(Foods.BEETROOT_SOUP)));
    public static final RegistrySupplier<Block> TABLE_SIGN = registerWithItem("table_sign", () -> new BoardBlock(BlockBehaviour.Properties.of(Material.DECORATION), false));
    public static final RegistrySupplier<Block> STREET_SIGN = registerWithItem("street_sign", () -> new BoardBlock(BlockBehaviour.Properties.of(Material.DECORATION), true));
    public static final RegistrySupplier<Block> PAINTING = registerWithItem("painting", () -> new SmallPaintingBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission()));
    public static final RegistrySupplier<Block> HEARTH = registerWithItem("hearth", () -> new DecorationBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission()));
    public static final RegistrySupplier<Block> ROSE = registerWithItem("rose", () -> new RoseBushBlock(MobEffect.byId(6), 1, BlockBehaviour.Properties.copy(Blocks.DANDELION)));
    public static final RegistrySupplier<Block> APPLE_TREE_SAPLING = registerWithItem("apple_tree_sapling", () -> new SaplingBlock(new ConfiguredFeatureSaplingGenerator() {

        @Override
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getTreeConfiguredFeature(RandomSource random, boolean bees) {
            if (random.nextBoolean()) return CandlelightConfiguredFeatures.APPLE_KEY;
            return CandlelightConfiguredFeatures.APPLE_VARIANT_KEY;
        }
    }, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistrySupplier<Block> APPLE_LEAVES = registerWithoutItem("apple_leaves", () -> new AppleLeaves(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> JEWELRY_BOX = registerWithItem("jewelry_box", () -> new JewelryBoxBlock(BlockBehaviour.Properties.of(Material.DECORATION)));
    public static final RegistrySupplier<Block> CHOCOLATE_BOX = registerWithItem("chocolate_box", () -> new ChocolateBoxBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)));
    public static final RegistrySupplier<Item> GOLD_RING = registerItem("gold_ring", () -> new RingItem(CandlelightMaterials.RING_ARMOR, EquipmentSlot.CHEST, getSettings().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> COOKING_HAT = registerItem("cooking_hat", () -> new CookingHatItem(getSettings().rarity(Rarity.COMMON)));

    public static final RegistrySupplier<Item> CHEFS_JACKET = registerItem("chefs_jacket", () -> new CookDefaultArmorItem(CandlelightMaterials.COOK_ARMOR, EquipmentSlot.CHEST, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> CHEFS_PANTS = registerItem("chefs_pants", () -> new CookDefaultArmorItem(CandlelightMaterials.COOK_ARMOR, EquipmentSlot.LEGS, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> CHEFS_BOOTS = registerItem("chefs_boots", () -> new CookDefaultArmorItem(CandlelightMaterials.COOK_ARMOR, EquipmentSlot.FEET, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Block> TYPEWRITER_IRON = registerWithItem("typewriter_iron", () -> new TypeWriterBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.0F, 3.0F).sound(SoundType.METAL)));
    public static final RegistrySupplier<Block> TYPEWRITER_COPPER = registerWithItem("typewriter_copper", () -> new TypeWriterBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.0F, 3.0F).sound(SoundType.METAL)));
    public static final RegistrySupplier<Item> NOTE_PAPER = registerItem("note_paper", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> NOTE_PAPER_WRITEABLE = registerItem("note_paper_writeable", () -> new WriteablePaperItem(getSettings().stacksTo(1)));
    public static final RegistrySupplier<Item> NOTE_PAPER_WRITTEN = registerItem("note_paper_written", () -> new WrittenPaperItem(getSettingsWithoutTab()));
    public static final RegistrySupplier<Item> LETTER_OPEN = registerItem("letter_open", () -> new LetterItem(getSettings()));
    public static final RegistrySupplier<Item> LETTER_CLOSED = registerItem("letter_closed", () -> new ClosedLetterItem(getSettingsWithoutTab().stacksTo(1)));
    public static final RegistrySupplier<Item> LOVE_LETTER_CLOSED = registerItem("love_letter", () -> new ClosedLetterItem(getSettingsWithoutTab()));
    public static final RegistrySupplier<Item> LOVE_LETTER_OPEN = registerItem("love_letter_open", () -> new LetterItem(getSettings()));
    public static final RegistrySupplier<Block> OAK_WINE_RACK_BIG = registerWithItem("oak_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_WINE_RACK_SMALL = registerWithItem("oak_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_CABINET = registerWithItem("oak_cabinet", () -> new CandlelightWineRackStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> OAK_DRAWER = registerWithItem("oak_drawer", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> OAK_TABLE = registerWithItem("oak_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> OAK_CHAIR = registerWithItem("oak_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> COBBLESTONE_WOOD_FIRED_OVEN = registerWithItem("cobblestone_wood_fired_oven", () -> new WoodFiredOvenBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> COBBLESTONE_STOVE = registerWithItem("cobblestone_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> COBBLESTONE_KITCHEN_SINK = registerWithItem("cobblestone_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_WINE_RACK_BIG = registerWithItem("birch_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_WINE_RACK_SMALL = registerWithItem("birch_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_CABINET = registerWithItem("birch_cabinet", () -> new CandlelightWineRackStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BIRCH_DRAWER = registerWithItem("birch_drawer", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BIRCH_TABLE = registerWithItem("birch_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> BIRCH_CHAIR = registerWithItem("birch_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SANDSTONE_WOOD_FIRED_OVEN = registerWithItem("sandstone_wood_fired_oven", () -> new WoodFiredOvenBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> SANDSTONE_STOVE = registerWithItem("sandstone_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> SANDSTONE_KITCHEN_SINK = registerWithItem("sandstone_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_WINE_RACK_BIG = registerWithItem("spruce_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_WINE_RACK_SMALL = registerWithItem("spruce_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_CABINET = registerWithItem("spruce_cabinet", () -> new CandlelightWineRackStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SPRUCE_DRAWER = registerWithItem("spruce_drawer", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SPRUCE_TABLE = registerWithItem("spruce_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> SPRUCE_CHAIR = registerWithItem("spruce_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> STONE_BRICKS_WOOD_FIRED_OVEN = registerWithItem("stone_bricks_wood_fired_oven", () -> new WoodFiredOvenBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> STONE_BRICKS_STOVE = registerWithItem("stone_bricks_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> STONE_BRICKS_KITCHEN_SINK = registerWithItem("stone_bricks_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_WINE_RACK_BIG = registerWithItem("dark_oak_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_WINE_RACK_SMALL = registerWithItem("dark_oak_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_CABINET = registerWithItem("dark_oak_cabinet", () -> new CandlelightWineRackStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> DARK_OAK_DRAWER = registerWithItem("dark_oak_drawer", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> DARK_OAK_TABLE = registerWithItem("dark_oak_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistrySupplier<Block> DARK_OAK_CHAIR = registerWithItem("dark_oak_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> DEEPSLATE_WOOD_FIRED_OVEN = registerWithItem("deepslate_wood_fired_oven", () -> new WoodFiredOvenBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> DEEPSLATE_STOVE = registerWithItem("deepslate_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> DEEPSLATE_KITCHEN_SINK = registerWithItem("deepslate_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_WINE_RACK_BIG = registerWithItem("acacia_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_WINE_RACK_SMALL = registerWithItem("acacia_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_CABINET = registerWithItem("acacia_cabinet", () -> new CandlelightWineRackStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> ACACIA_DRAWER = registerWithItem("acacia_drawer", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> ACACIA_TABLE = registerWithItem("acacia_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistrySupplier<Block> ACACIA_CHAIR = registerWithItem("acacia_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> GRANITE_WOOD_FIRED_OVEN = registerWithItem("granite_wood_fired_oven", () -> new WoodFiredOvenBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> GRANITE_STOVE = registerWithItem("granite_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> GRANITE_KITCHEN_SINK = registerWithItem("granite_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_WINE_RACK_BIG = registerWithItem("jungle_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_WINE_RACK_SMALL = registerWithItem("jungle_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_CABINET = registerWithItem("jungle_cabinet", () -> new CandlelightWineRackStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> JUNGLE_DRAWER = registerWithItem("jungle_drawer", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> JUNGLE_TABLE = registerWithItem("jungle_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> JUNGLE_CHAIR = registerWithItem("jungle_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> END_WOOD_FIRED_OVEN = registerWithItem("end_wood_fired_oven", () -> new WoodFiredOvenBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> END_STOVE = registerWithItem("end_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> END_KITCHEN_SINK = registerWithItem("end_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_WINE_RACK_BIG = registerWithItem("mangrove_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_WINE_RACK_SMALL = registerWithItem("mangrove_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_CABINET = registerWithItem("mangrove_cabinet", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> MANGROVE_DRAWER = registerWithItem("mangrove_drawer", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> MANGROVE_TABLE = registerWithItem("mangrove_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> MANGROVE_CHAIR = registerWithItem("mangrove_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> MUD_WOOD_FIRED_OVEN = registerWithItem("mud_wood_fired_oven", () -> new WoodFiredOvenBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> MUD_STOVE = registerWithItem("mud_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> MUD_KITCHEN_SINK = registerWithItem("mud_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_WINE_RACK_BIG = registerWithItem("warped_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_WINE_RACK_SMALL = registerWithItem("warped_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_CABINET = registerWithItem("warped_cabinet", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> WARPED_DRAWER = registerWithItem("warped_drawer", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> WARPED_TABLE = registerWithItem("warped_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> WARPED_CHAIR = registerWithItem("warped_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> QUARTZ_WOOD_FIRED_OVEN = registerWithItem("quartz_wood_fired_oven", () -> new WoodFiredOvenBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> QUARTZ_STOVE = registerWithItem("quartz_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> QUARTZ_KITCHEN_SINK = registerWithItem("quartz_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_WINE_RACK_BIG = registerWithItem("crimson_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_WINE_RACK_SMALL = registerWithItem("crimson_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_CABINET = registerWithItem("crimson_cabinet", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CRIMSON_DRAWER = registerWithItem("crimson_drawer", () -> new CandlelightWineRackStorageBlock2(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CRIMSON_TABLE = registerWithItem("crimson_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> CRIMSON_CHAIR = registerWithItem("crimson_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_WOOD_FIRED_OVEN = registerWithItem("red_nether_bricks_wood_fired_oven", () -> new WoodFiredOvenBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_STOVE = registerWithItem("red_nether_bricks_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_KITCHEN_SINK = registerWithItem("red_nether_bricks_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> POTTED_ROSE = registerWithoutItem("potted_rose", () -> new FlowerPotBlock(ObjectRegistry.ROSE.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY)));

    




    public static <T extends Block> RegistrySupplier<T> registerWine(String name, Supplier<T> block, RegistrySupplier<MobEffect> effect) {
        RegistrySupplier<T> toReturn = registerWithoutItem(name, block);

        registerItem(name, () -> new DrinkBlockItem(toReturn.get(), new Item.Properties().food(wineFoodComponent(effect.get())).tab(Candlelight.CANDLELIGHT_TAB)));
        return toReturn;
    }

    public static <T extends Block> RegistrySupplier<T> registerBigWine(String name, Supplier<T> block, RegistrySupplier<MobEffect> effect) {
        RegistrySupplier<T> toReturn = registerWithoutItem(name, block);

        registerItem(name, () -> new DrinkBlockBigItem(toReturn.get(), new Item.Properties().food(wineFoodComponent(effect.get())).tab(Candlelight.CANDLELIGHT_TAB)));
        return toReturn;
    }


    private static FoodProperties wineFoodComponent(MobEffect effect) {
        List<Pair<MobEffectInstance, Float>> statusEffects = new ArrayList<>();
        statusEffects.add(new Pair<>(new MobEffectInstance(effect, 45 * 20), 1.0f));
        return new VineryFoodComponent(statusEffects);
    }
    

    public static void init() {
        ITEMS.register();
        BLOCKS.register();
    }


    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties().tab(Candlelight.CANDLELIGHT_TAB);
        consumer.accept(settings);
        return settings;
    }

    private static Item.Properties getSettingsWithoutTab(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    private static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }

    private static Item.Properties getSettingsWithoutTab() {
        return getSettingsWithoutTab(settings -> {
        });
    }



    private static BlockBehaviour.Properties getBushSettings() {
        return BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH);
    }



    private static BlockBehaviour.Properties getLogBlockSettings() {
        return BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD);
    }


    private static BlockBehaviour.Properties getWineSettings() {
        return BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().instabreak();
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return registerWithItem(name, block, Candlelight.CANDLELIGHT_TAB);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block, @Nullable CreativeModeTab tab) {
        return Util.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new CandlelightIdentifier(name), block, tab);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return Util.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, new CandlelightIdentifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return Util.registerItem(ITEMS, ITEM_REGISTRAR, new CandlelightIdentifier(path), itemSupplier);
    }

}