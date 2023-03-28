package net.satisfy.candlelight.world.feature;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.block.AppleLeaves;
import net.satisfy.candlelight.registry.ObjectRegistry;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import satisfyu.vinery.VineryIdentifier;


import java.util.List;
import java.util.function.Predicate;

import static net.satisfy.candlelight.block.AppleLeaves.VARIANT;

public class ConfiguredFeatures {


    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> STRAWBERRY_WILD_TAIGA_PATCH =
            net.minecraft.world.gen.feature.ConfiguredFeatures.register(CandlelightIdentifier.asString("strawberry_wild_taiga"), Feature.RANDOM_PATCH, net.minecraft.world.gen.feature.ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(net.satisfy.candlelight.registry.ObjectRegistry.STRAWBERRY_WILD_TAIGA.getDefaultState())), List.of(Blocks.GRASS_BLOCK), 36));
    public static final RegistryEntry<PlacedFeature> STRAWBERRY_WILD_TAIGA_PATCH_CHANCE = PlacedFeatures.register(CandlelightIdentifier.asString("strawberry_wild_taiga_patch_chance"), STRAWBERRY_WILD_TAIGA_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());


    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> STRAWBERRY_WILD_JUNGLE_PATCH =
            net.minecraft.world.gen.feature.ConfiguredFeatures.register(CandlelightIdentifier.asString("strawberry_wild_jungle"), Feature.RANDOM_PATCH, net.minecraft.world.gen.feature.ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.STRAWBERRY_WILD_JUNGLE.getDefaultState())), List.of(Blocks.GRASS_BLOCK), 36));
    public static final RegistryEntry<PlacedFeature> STRAWBERRY_WILD_JUNGLE_PATCH_CHANCE = PlacedFeatures.register(CandlelightIdentifier.asString("strawberry_wild_jungle_patch_chance"), STRAWBERRY_WILD_JUNGLE_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());


    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> TOMATOES_WILD_PATCH =
            net.minecraft.world.gen.feature.ConfiguredFeatures.register(CandlelightIdentifier.asString("tomatoes_wild_patch"), Feature.RANDOM_PATCH, net.minecraft.world.gen.feature.ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.TOMATOES_WILD.getDefaultState())), List.of(Blocks.GRASS_BLOCK), 36));
    public static final RegistryEntry<PlacedFeature> TOMATOES_WILD_PATCH_CHANCE = PlacedFeatures.register(CandlelightIdentifier.asString("tomatoes_wild_patch_chance"), TOMATOES_WILD_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> ROSE_PATCH =
            net.minecraft.world.gen.feature.ConfiguredFeatures.register(CandlelightIdentifier.asString("rose"), Feature.RANDOM_PATCH, net.minecraft.world.gen.feature.ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.ROSE.getDefaultState())), List.of(Blocks.GRASS_BLOCK), 24));
    public static final RegistryEntry<PlacedFeature> ROSE_PATCH_CHANCE = PlacedFeatures.register(CandlelightIdentifier.asString("rose_patch_chance"), ROSE_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> BROCCOLI_PATCH =
            net.minecraft.world.gen.feature.ConfiguredFeatures.register(CandlelightIdentifier.asString("broccoli"), Feature.RANDOM_PATCH, net.minecraft.world.gen.feature.ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.WILD_BROCCOLI.getDefaultState())), List.of(Blocks.GRASS_BLOCK), 24));
    public static final RegistryEntry<PlacedFeature> BROCCOLI_PATCH_CHANCE = PlacedFeatures.register(CandlelightIdentifier.asString("broccoli_patch_chance"), BROCCOLI_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> APPLE_TREE = net.minecraft.world.gen.feature.ConfiguredFeatures.register(CandlelightIdentifier.asString("apple_tree"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.OAK_LOG), new LargeOakTrunkPlacer(4, 14, 2), appleLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of()).forceDirt().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> APPLE_TREE_BEE = net.minecraft.world.gen.feature.ConfiguredFeatures.register(CandlelightIdentifier.asString("apple_tree_bee"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.OAK_LOG), new LargeOakTrunkPlacer(4, 14, 2), appleLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f))).forceDirt().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> APPLE_TREE_VARIANT = net.minecraft.world.gen.feature.ConfiguredFeatures.register(CandlelightIdentifier.asString("apple_tree_variant"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.OAK_LOG), new LargeOakTrunkPlacer(4, 14, 2), appleLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of()).forceDirt().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> APPLE_TREE_VARIANT_WITH_BEE = net.minecraft.world.gen.feature.ConfiguredFeatures.register(CandlelightIdentifier.asString("apple_tree_variant_with_bee"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.OAK_LOG), new LargeOakTrunkPlacer(4, 14, 2), appleLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f))).forceDirt().build());

    public static final RegistryEntry<PlacedFeature> TREE_APPLE = PlacedFeatures.register("tree_apple", APPLE_TREE, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.01f, 1), ObjectRegistry.APPLE_TREE_SAPLING));

    private static RandomPatchFeatureConfig getFlowerGrassConfig(Block flowerGrass) {
        return new RandomPatchFeatureConfig(8, 2, 0, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(flowerGrass.getDefaultState())), createBlockPredicate(List.of(Blocks.GRASS_BLOCK))));
    }

    public static WeightedBlockStateProvider appleLeaveProvider(){
        return new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ObjectRegistry.APPLE_LEAVES.getDefaultState(), 10).add(ObjectRegistry.APPLE_LEAVES.getDefaultState().with(VARIANT, true), 4).add(ObjectRegistry.APPLE_LEAVES.getDefaultState().with(VARIANT, true).with(AppleLeaves.HAS_APPLES, true), 2));
    }

    public static void init() {
        BiomeModification world = BiomeModifications.create(new VineryIdentifier("world_features"));
        Predicate<BiomeSelectionContext> taigaBiomes = BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.TAIGA, BiomeKeys.DARK_FOREST, BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA);
        Predicate<BiomeSelectionContext> jungleBiomes = BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE);
        Predicate<BiomeSelectionContext> dryBiomes = BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.RIVER, BiomeKeys.SWAMP, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.BEACH, BiomeKeys.WOODED_BADLANDS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.PLAINS);
        Predicate<BiomeSelectionContext> coldBiomes = BiomeSelectors.includeByKey(BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_BEACH, BiomeKeys.SNOWY_PLAINS);
        Predicate<BiomeSelectionContext> plainsBiomes = BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.PLAINS, BiomeKeys.SWAMP, BiomeKeys.BIRCH_FOREST, BiomeKeys.MEADOW, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.RIVER);

        world.add(ModificationPhase.ADDITIONS, plainsBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, BROCCOLI_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, plainsBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ROSE_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, taigaBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, STRAWBERRY_WILD_TAIGA_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, jungleBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, STRAWBERRY_WILD_JUNGLE_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, dryBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, TOMATOES_WILD_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, coldBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, STRAWBERRY_WILD_TAIGA_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, getTreesSelector(), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, TREE_APPLE.value()));
    }

    private static Predicate<BiomeSelectionContext> getTreesSelector() {
        return BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS);
    }

    private static BlockPredicate createBlockPredicate(List<Block> validGround) {
        return !validGround.isEmpty() ? BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(validGround)) : BlockPredicate.IS_AIR;
    }

}

