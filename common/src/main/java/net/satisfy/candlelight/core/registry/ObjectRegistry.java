package net.satisfy.candlelight.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.core.block.*;
import net.satisfy.candlelight.core.block.CStoveBlock;
import net.satisfy.candlelight.core.item.*;
import net.satisfy.candlelight.core.util.CandlelightFoods;
import net.satisfy.candlelight.core.util.CandlelightIdentifier;
import net.satisfy.farm_and_charm.core.block.*;
import net.satisfy.farm_and_charm.core.item.food.EffectBlockItem;
import net.satisfy.farm_and_charm.core.item.food.EffectFoodBlockItem;
import net.satisfy.farm_and_charm.core.item.food.EffectFoodItem;
import net.satisfy.farm_and_charm.core.item.food.EffectItem;
import net.satisfy.farm_and_charm.core.registry.MobEffectRegistry;
import net.satisfy.farm_and_charm.core.util.GeneralUtil;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ObjectRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Candlelight.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Candlelight.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

    public static final RegistrySupplier<Item> TOMATO_SOUP = registerItem("tomato_soup", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.TOMATO_SOUP), 1));
    public static final RegistrySupplier<Item> MUSHROOM_SOUP = registerItem("mushroom_soup", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.MUSHROOM_SOUP), 1));
    public static final RegistrySupplier<Item> PASTA_WITH_MOZZARELLA = registerItem("pasta_with_mozzarella", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.PASTA), 2));
    public static final RegistrySupplier<Item> BOLOGNESE = registerItem("bolognese", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.BOLOGNESE), 1));
    public static final RegistrySupplier<Item> BEEF_WITH_MUSHROOM_IN_WINE_AND_POTATOES = registerItem("beef_with_mushroom_in_wine_and_potatoes", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.BEEF_WITH_MUSHROOM_IN_WINE_AND_POTATOES), 2));
    public static final RegistrySupplier<Item> PASTA_WITH_BOLOGNESE = registerItem("pasta_with_bolognese", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.HARVEST_PLATE), 1));
    public static final RegistrySupplier<Item> ROASTBEEF_WITH_GLAZED_CARROTS = registerItem("roastbeef_with_glazed_carrots", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.ROASTBEEF_WITH_GLAZED_CARROTS), 2));
    public static final RegistrySupplier<Item> ROASTED_LAMB_WITH_LETTUCE = registerItem("roasted_lamb_with_lettuce", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.ROASTED_LAMB_WITH_LETTUCE), 2));
    public static final RegistrySupplier<Item> FILLET_STEAK = registerItem("fillet_steak", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.FILLET_STEAK), 2));
    public static final RegistrySupplier<Item> TROPICAL_FISH_SUPREME = registerItem("tropical_fish_supreme", () -> new EffectFoodItem(getSettings().food(Foods.GOLDEN_CARROT), 1));
    public static final RegistrySupplier<Item> CHICKEN_ALFREDO = registerItem("chicken_alfredo", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.HARVEST_PLATE), 1));
    public static final RegistrySupplier<Item> SALMON_ON_WHITE_WINE = registerItem("salmon_on_white_wine", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.SALMON_ON_WHITE_WINE), 2));
    public static final RegistrySupplier<Item> CHICKEN_WITH_VEGETABLES = registerItem("chicken_with_vegetables", () -> new EffectFoodItem(getSettings().food(Foods.GOLDEN_CARROT), 2));

    public static final RegistrySupplier<Block> PORK_RIBS_BLOCK = registerWithoutItem("pork_ribs_block", () -> new EffectFoodBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2, CandlelightFoods.PORK_RIBS));
    public static final RegistrySupplier<Item> PORK_RIBS = registerItem("pork_ribs", () -> new EffectFoodBlockItem(PORK_RIBS_BLOCK.get(), getSettings().food(CandlelightFoods.PORK_RIBS), 2));
    public static final RegistrySupplier<Block> LASAGNE_BLOCK = registerWithoutItem("lasagne_block", () -> new EffectFoodBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 3, CandlelightFoods.LASAGNE));
    public static final RegistrySupplier<Item> LASAGNE = registerItem("lasagne", () -> new EffectFoodBlockItem(LASAGNE_BLOCK.get(), getSettings().food(CandlelightFoods.LASAGNE), 3));
    public static final RegistrySupplier<Block> BEEF_WELLINGTON_BLOCK = registerWithoutItem("beef_wellington_block", () -> new EffectFoodBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2, CandlelightFoods.BEEF_WELLINGTON));
    public static final RegistrySupplier<Item> BEEF_WELLINGTON = registerItem("beef_wellington", () -> new EffectFoodBlockItem(BEEF_WELLINGTON_BLOCK.get(), getSettings().food(CandlelightFoods.BEEF_WELLINGTON), 2));


    public static final RegistrySupplier<Item> CLOCHE = registerItem("cloche", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> NAPKIN = registerItem("napkin", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> MOZZARELLA = registerItem("mozzarella", () -> new Item(getSettings().food(Foods.BREAD)));
    public static final RegistrySupplier<Item> KHINKALI = registerItem("khinkali", () -> new Item(getSettings().food(Foods.GOLDEN_CARROT)));
    public static final RegistrySupplier<Item> BEETROOT_SALAD = registerItem("beetroot_salad", () -> new EffectItem(getFoodItemSettings(5, 0.6f, MobEffectRegistry.SUSTENANCE.get(), 2400), 2400, true));
    public static final RegistrySupplier<Item> CHICKEN_TERIYAKI = registerItem("chicken_teriyaki", () -> new EffectItem(getFoodItemSettings(8, 0.8f, MobEffectRegistry.SATIATION.get(), 6000), 6000, true));
    public static final RegistrySupplier<Item> SALAD = registerItem("salad", () -> new EffectItem(getFoodItemSettings(5, 0.7f, MobEffectRegistry.SUSTENANCE.get(), 3600), 3600, true));
    public static final RegistrySupplier<Item> BEEF_TARTARE = registerItem("beef_tartare", () -> new EffectItem(getFoodItemSettings(8, 0.9f, MobEffectRegistry.SATIATION.get(), 3000), 3000, true));
    public static final RegistrySupplier<Item> PASTA_WITH_LETTUCE = registerItem("pasta_with_lettuce", () -> new EffectItem(getFoodItemSettings(8, 0.9f, MobEffectRegistry.SATIATION.get(), 3600), 3600, true));
    public static final RegistrySupplier<Item> OMELET = registerItem("omelet", () -> new EffectItem(getFoodItemSettings(8, 0.4f, MobEffectRegistry.SWEETS.get(), 4800), 4800, true));
    public static final RegistrySupplier<Item> HARVEST_PLATE = registerItem("harvest_plate", () -> new EffectItem(getFoodItemSettings(7, 0.8f, MobEffectRegistry.FARMERS_BLESSING.get(), 4800), 4800, true));
    public static final RegistrySupplier<Item> CHOCOLATE_MOUSSE = registerItem("chocolate_mousse", () -> new EffectItem(getFoodItemSettings(4, 0.3f, MobEffectRegistry.SWEETS.get(), 2400), 2400, true));
    public static final RegistrySupplier<Item> GOLD_RING = registerItem("gold_ring", () -> new RingItem(ArmorMaterialRegistry.RING_ARMOR, ArmorItem.Type.CHESTPLATE, getSettings().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> COOKING_HAT = registerItem("cooking_hat", () -> new CandlelightHatItem(ArmorMaterialRegistry.COOK_ARMOR, ArmorItem.Type.HELMET, getSettings().rarity(Rarity.UNCOMMON), new CandlelightIdentifier("textures/models/armor/cooking_hat.png")));
    public static final RegistrySupplier<Item> CHEFS_JACKET = registerItem("chefs_jacket", () -> new CandlelightChestItem(ArmorMaterialRegistry.COOK_ARMOR, ArmorItem.Type.CHESTPLATE, getSettings().rarity(Rarity.UNCOMMON), new CandlelightIdentifier("textures/models/armor/cook.png")));
    public static final RegistrySupplier<Item> CHEFS_PANTS = registerItem("chefs_pants", () -> new CandlelightLegsItem(ArmorMaterialRegistry.COOK_ARMOR, ArmorItem.Type.LEGGINGS, getSettings().rarity(Rarity.UNCOMMON), new CandlelightIdentifier("textures/models/armor/cook.png")));
    public static final RegistrySupplier<Item> CHEFS_BOOTS = registerItem("chefs_boots", () -> new CandlelightBootsItem(ArmorMaterialRegistry.COOK_ARMOR, ArmorItem.Type.BOOTS, getSettings().rarity(Rarity.UNCOMMON), new CandlelightIdentifier("textures/models/armor/cook.png")));
    public static final RegistrySupplier<Item> FLOWER_CROWN = registerItem("flower_crown", () -> new CandlelightHatItem(ArmorMaterialRegistry.COOK_ARMOR, ArmorItem.Type.HELMET, getSettings().rarity(Rarity.UNCOMMON), new CandlelightIdentifier("textures/models/armor/flower_crown.png")));
    public static final RegistrySupplier<Item> DRESS = registerItem("dress", () -> new DyeableCandlelightArmorItem(ArmorMaterialRegistry.COOK_ARMOR, ArmorItem.Type.CHESTPLATE, 16744576, getSettings().rarity(Rarity.UNCOMMON), new CandlelightIdentifier("textures/models/armor/dress.png")));
    public static final RegistrySupplier<Item> SHIRT = registerItem("shirt", () -> new CandlelightChestItem(ArmorMaterialRegistry.COOK_ARMOR, ArmorItem.Type.CHESTPLATE, getSettings().rarity(Rarity.UNCOMMON), new CandlelightIdentifier("textures/models/armor/shirt.png")));
    public static final RegistrySupplier<Item> FORMAL_SHIRT = registerItem("formal_shirt", () -> new CandlelightChestItem(ArmorMaterialRegistry.COOK_ARMOR, ArmorItem.Type.CHESTPLATE, getSettings().rarity(Rarity.UNCOMMON), new CandlelightIdentifier("textures/models/armor/formal_shirt.png")));
    public static final RegistrySupplier<Item> NECKTIE = registerItem("necktie", () -> new CandlelightHatItem(ArmorMaterialRegistry.COOK_ARMOR, ArmorItem.Type.HELMET, getSettings().rarity(Rarity.COMMON), new CandlelightIdentifier("textures/models/armor/tie.png")));
    public static final RegistrySupplier<Item> TROUSERS_AND_VEST = registerItem("trousers_and_vest", () -> new DyeableCandlelightArmorItem(ArmorMaterialRegistry.COOK_ARMOR, ArmorItem.Type.LEGGINGS, 16715535, getSettings().rarity(Rarity.UNCOMMON), new CandlelightIdentifier("textures/models/armor/suit.png")));
    public static final RegistrySupplier<Item> NOTE_PAPER_WRITEABLE = registerItem("note_paper_writeable", () -> new WriteablePaperItem(getSettings().stacksTo(1)));
    public static final RegistrySupplier<Item> NOTE_PAPER_WRITTEN = registerItem("note_paper_written", () -> new WrittenPaperItem(getSettingsWithoutTab()));
    public static final RegistrySupplier<Item> LETTER_OPEN = registerItem("letter_open", () -> new LetterItem(getSettings()));
    public static final RegistrySupplier<Item> LETTER_CLOSED = registerItem("letter_closed", () -> new ClosedLetterItem(getSettingsWithoutTab().stacksTo(1)));
    public static final RegistrySupplier<Item> LOVE_LETTER_OPEN = registerItem("love_letter_open", () -> new LetterItem(getSettings()));
    public static final RegistrySupplier<Item> LOVE_LETTER_CLOSED = registerItem("love_letter", () -> new ClosedLetterItem(getSettingsWithoutTab().stacksTo(1)));
    public static final RegistrySupplier<Block> CANDLELIGHT_BANNER = registerWithItem("candlelight_banner", () -> new CompletionistBannerBlock(BlockBehaviour.Properties.of().strength(1F).instrument(NoteBlockInstrument.BASS).noCollission().sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CANDLELIGHT_WALL_BANNER = registerWithoutItem("candlelight_wall_banner", () -> new CompletionistWallBannerBlock(BlockBehaviour.Properties.of().strength(1F).instrument(NoteBlockInstrument.BASS).noCollission().sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> FLOORBOARD = registerWithItem("floorboard", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> DRAWER = registerWithItem("drawer", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> CABINET = registerWithItem("cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> SIDEBOARD = registerWithItem("sideboard", () -> new SideBoardBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.5f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CHAIR = registerWithItem("chair", () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SOFA = registerWithItem("sofa", () -> new SofaBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> TABLE = registerWithItem("table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> LAMP = registerWithItem("lamp", () -> new LampBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel(s -> s.getValue(LampBlock.LUMINANCE) ? 15 : 0).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SIDE_TABLE = registerWithItem("side_table", () -> new SideTableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> DINNER_BELL = registerWithItem("dinner_bell", () -> new DinnerBellBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistrySupplier<Block> COOKING_POT = registerWithItem("cooking_pot", () -> new LargeCookingPotBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistrySupplier<Block> COOKING_PAN = registerWithoutItem("cooking_pan", () -> new CookingPanBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistrySupplier<Item> COOKING_PAN_ITEM = registerItem("cooking_pan", () -> new CookingPanItem(COOKING_PAN.get(), getSettings()));
    public static final RegistrySupplier<Block> TABLE_SET = registerWithoutItem("table_set", () -> new TableSetBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Item> PLATE = registerItem("plate", () -> new TableSetBlockItem(TABLE_SET.get(), getSettings(), TableSetBlock.PlateType.PLATE));
    public static final RegistrySupplier<Item> BOWL = registerItem("bowl", () -> new TableSetBlockItem(TABLE_SET.get(), getSettings(), TableSetBlock.PlateType.BOWL));
    public static final RegistrySupplier<Block> GLASS_BLOCK = registerWithoutItem("glass", () -> new StackableBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noParticlesOnBreak(), 4));
    public static final RegistrySupplier<Item> GLASS = registerItem("glass", () -> new BlockItem(GLASS_BLOCK.get(), getSettings()));
    public static final RegistrySupplier<Block> WINE_GLASS_BLOCK = registerWithoutItem("wine_glass", () -> new StackableBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noParticlesOnBreak(), 4));
    public static final RegistrySupplier<Item> WINE_GLASS = registerItem("wine_glass", () -> new BlockItem(WINE_GLASS_BLOCK.get(), getSettings()));
    public static final RegistrySupplier<Block> FRESH_GARDEN_SALAD_BLOCK = registerWithoutItem("fresh_garden_salad_block", () -> new EffectFoodTrayBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 4, new FoodProperties.Builder().nutrition(6).saturationMod(0.9F).build()));
    public static final RegistrySupplier<Item> FRESH_GARDEN_SALAD = registerItem("fresh_garden_salad", () -> new EffectBlockItem(FRESH_GARDEN_SALAD_BLOCK.get(), getFoodItemSettings(6, 0.9f, MobEffectRegistry.FARMERS_BLESSING.get(), 3600)));
    public static final RegistrySupplier<Block> TOMATO_MOZZARELLA_BLOCK = registerWithoutItem("tomato_mozzarella_block", () -> new EffectFoodTrayBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 4, new FoodProperties.Builder().nutrition(6).saturationMod(0.7F).build()));
    public static final RegistrySupplier<Item> TOMATO_MOZZARELLA_SALAD = registerItem("tomato_mozzarella_salad", () -> new EffectBlockItem(TOMATO_MOZZARELLA_BLOCK.get(), getFoodItemSettings(5, 0.7f, MobEffectRegistry.FEAST.get(), 4800)));
    public static final RegistrySupplier<Block> TABLE_SIGN = registerWithItem("table_sign", () -> new BoardBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> PAINTING = registerWithItem("painting", () -> new SmallPaintingBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).noCollission()));
    public static final RegistrySupplier<Block> HEARTH = registerWithItem("hearth", () -> new WallDecorationBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).noCollission()));
    public static final RegistrySupplier<Block> ROSE = registerWithItem("rose", () -> new BonemealableFlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_ROSE = registerWithoutItem("potted_rose", () -> new FlowerPotBlock(ROSE.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY)));
    public static final RegistrySupplier<Block> JEWELRY_BOX = registerWithItem("jewelry_box", () -> new JewelryBoxBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> CHOCOLATE_BOX = registerWithItem("chocolate_box", () -> new EatableBoxBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)));
    public static final RegistrySupplier<Block> TYPEWRITER_IRON = registerWithItem("typewriter_iron", () -> new TypeWriterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2.0F, 3.0F).sound(SoundType.METAL)));
    public static final RegistrySupplier<Block> TYPEWRITER_COPPER = registerWithItem("typewriter_copper", () -> new TypeWriterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2.0F, 3.0F).sound(SoundType.METAL)));
    public static final RegistrySupplier<Block> NOTE_PAPER_BLOCK = registerWithoutItem("note_paper", () -> new StackableBlock(BlockBehaviour.Properties.copy(Blocks.DRIED_KELP_BLOCK), 8));
    public static final RegistrySupplier<Item> NOTE_PAPER = registerItem("note_paper", () -> new BlockItem(NOTE_PAPER_BLOCK.get(), getSettings()));
    public static final RegistrySupplier<Block> COBBLESTONE_STOVE = registerWithItem("cobblestone_stove", () -> new CStoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> COBBLESTONE_KITCHEN_SINK = registerWithItem("cobblestone_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> COBBLESTONE_COUNTER = registerWithItem("cobblestone_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_CABINET = registerWithItem("oak_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> OAK_DRAWER = registerWithItem("oak_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> OAK_TABLE = registerWithItem("oak_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> OAK_CHAIR = registerWithItem("oak_chair", () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> OAK_SHELF = registerWithItem("oak_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_BIG_TABLE = registerWithItem("oak_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> SANDSTONE_STOVE = registerWithItem("sandstone_stove", () -> new CStoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> SANDSTONE_KITCHEN_SINK = registerWithItem("sandstone_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> SANDSTONE_COUNTER = registerWithItem("sandstone_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_CABINET = registerWithItem("birch_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> BIRCH_DRAWER = registerWithItem("birch_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> BIRCH_TABLE = registerWithItem("birch_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> BIRCH_CHAIR = registerWithItem("birch_chair", () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BIRCH_SHELF = registerWithItem("birch_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_BIG_TABLE = registerWithItem("birch_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> STONE_BRICKS_STOVE = registerWithItem("stone_bricks_stove", () -> new CStoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> STONE_BRICKS_KITCHEN_SINK = registerWithItem("stone_bricks_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> STONE_BRICKS_COUNTER = registerWithItem("stone_bricks_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_CABINET = registerWithItem("spruce_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> SPRUCE_DRAWER = registerWithItem("spruce_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> SPRUCE_TABLE = registerWithItem("spruce_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> SPRUCE_CHAIR = registerWithItem("spruce_chair", () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SPRUCE_SHELF = registerWithItem("spruce_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_BIG_TABLE = registerWithItem("spruce_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> DEEPSLATE_STOVE = registerWithItem("deepslate_stove", () -> new CStoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> DEEPSLATE_KITCHEN_SINK = registerWithItem("deepslate_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> DEEPSLATE_COUNTER = registerWithItem("deepslate_counter", () -> new FacingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_CABINET = registerWithItem("dark_oak_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> DARK_OAK_DRAWER = registerWithItem("dark_oak_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> DARK_OAK_TABLE = registerWithItem("dark_oak_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistrySupplier<Block> DARK_OAK_CHAIR = registerWithItem("dark_oak_chair", () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> DARK_OAK_SHELF = registerWithItem("dark_oak_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_BIG_TABLE = registerWithItem("dark_oak_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> GRANITE_STOVE = registerWithItem("granite_stove", () -> new CStoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> GRANITE_KITCHEN_SINK = registerWithItem("granite_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> GRANITE_COUNTER = registerWithItem("granite_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_CABINET = registerWithItem("acacia_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> ACACIA_DRAWER = registerWithItem("acacia_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> ACACIA_TABLE = registerWithItem("acacia_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistrySupplier<Block> ACACIA_CHAIR = registerWithItem("acacia_chair", () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> ACACIA_SHELF = registerWithItem("acacia_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_BIG_TABLE = registerWithItem("acacia_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> END_STOVE = registerWithItem("end_stove", () -> new CStoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> END_KITCHEN_SINK = registerWithItem("end_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> END_COUNTER = registerWithItem("end_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_CABINET = registerWithItem("jungle_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> JUNGLE_DRAWER = registerWithItem("jungle_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> JUNGLE_TABLE = registerWithItem("jungle_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> JUNGLE_CHAIR = registerWithItem("jungle_chair", () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> JUNGLE_SHELF = registerWithItem("jungle_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_BIG_TABLE = registerWithItem("jungle_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> MUD_STOVE = registerWithItem("mud_stove", () -> new CStoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> MUD_KITCHEN_SINK = registerWithItem("mud_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> MUD_COUNTER = registerWithItem("mud_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_CABINET = registerWithItem("mangrove_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> MANGROVE_DRAWER = registerWithItem("mangrove_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> MANGROVE_TABLE = registerWithItem("mangrove_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> MANGROVE_CHAIR = registerWithItem("mangrove_chair", () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> MANGROVE_SHELF = registerWithItem("mangrove_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_BIG_TABLE = registerWithItem("mangrove_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> QUARTZ_STOVE = registerWithItem("quartz_stove", () -> new CStoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> QUARTZ_KITCHEN_SINK = registerWithItem("quartz_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> QUARTZ_COUNTER = registerWithItem("quartz_counter", () -> new FacingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_CABINET = registerWithItem("warped_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> WARPED_DRAWER = registerWithItem("warped_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> WARPED_TABLE = registerWithItem("warped_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> WARPED_CHAIR = registerWithItem("warped_chair", () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> WARPED_SHELF = registerWithItem("warped_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_BIG_TABLE = registerWithItem("warped_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_STOVE = registerWithItem("red_nether_bricks_stove", () -> new CStoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_KITCHEN_SINK = registerWithItem("red_nether_bricks_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_COUNTER = registerWithItem("red_nether_bricks_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_CABINET = registerWithItem("crimson_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> CRIMSON_DRAWER = registerWithItem("crimson_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> CRIMSON_TABLE = registerWithItem("crimson_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> CRIMSON_CHAIR = registerWithItem("crimson_chair", () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CRIMSON_SHELF = registerWithItem("crimson_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_BIG_TABLE = registerWithItem("crimson_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> BASALT_STOVE = registerWithItem("basalt_stove", () -> new CStoveBlock(BlockBehaviour.Properties.copy(Blocks.BASALT).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> BASALT_KITCHEN_SINK = registerWithItem("basalt_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.copy(Blocks.BASALT).noOcclusion()));
    public static final RegistrySupplier<Block> BASALT_COUNTER = registerWithItem("basalt_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.BASALT).noOcclusion()));
    public static final RegistrySupplier<Block> CHERRY_CABINET = registerWithItem("cherry_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> CHERRY_DRAWER = registerWithItem("cherry_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> CHERRY_TABLE = registerWithItem("cherry_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS)));
    public static final RegistrySupplier<Block> CHERRY_CHAIR = registerWithItem("cherry_chair", () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CHERRY_SHELF = registerWithItem("cherry_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CHERRY_BIG_TABLE = registerWithItem("cherry_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> BAMBOO_STOVE = registerWithItem("bamboo_stove", () -> new BambooStoveBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS).lightLevel(s -> 10)));
    public static final RegistrySupplier<Block> BAMBOO_KITCHEN_SINK = registerWithItem("bamboo_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_COUNTER = registerWithItem("bamboo_counter", () -> new FacingBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_CABINET = registerWithItem("bamboo_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> BAMBOO_DRAWER = registerWithItem("bamboo_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> BAMBOO_TABLE = registerWithItem("bamboo_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS)));
    public static final RegistrySupplier<Block> BAMBOO_CHAIR = registerWithItem("bamboo_chair", () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BAMBOO_SHELF = registerWithItem("bamboo_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_BIG_TABLE = registerWithItem("bamboo_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));

    public static void init() {
        ITEMS.register();
        BLOCKS.register();
    }

    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
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

    private static Item.Properties getFoodItemSettings(int nutrition, float saturationMod, MobEffect effect, int duration) {
        return getFoodItemSettings(nutrition, saturationMod, effect, duration, true, false);
    }

    @SuppressWarnings("all")
    private static Item.Properties getFoodItemSettings(int nutrition, float saturationMod, MobEffect effect, int duration, boolean alwaysEat, boolean fast) {
        return getSettings().food(createFood(nutrition, saturationMod, effect, duration, alwaysEat, fast));
    }

    private static FoodProperties createFood(int nutrition, float saturationMod, MobEffect effect, int duration, boolean alwaysEat, boolean fast) {
        FoodProperties.Builder food = new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturationMod);
        if (alwaysEat) food.alwaysEat();
        if (fast) food.fast();
        if (effect != null) food.effect(new MobEffectInstance(effect, duration), 1.0f);
        return food.build();
    }

    private static BlockBehaviour.Properties getLogBlockSettings() {
        return BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F).sound(SoundType.WOOD);
    }

    private static BlockBehaviour.Properties getWineSettings() {
        return BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().instabreak();
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return GeneralUtil.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new CandlelightIdentifier(name), block);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return GeneralUtil.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, new CandlelightIdentifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return GeneralUtil.registerItem(ITEMS, ITEM_REGISTRAR, new CandlelightIdentifier(path), itemSupplier);
    }
}