package satisfyu.candlelight.world.feature;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import satisfyu.candlelight.block.AppleLeaves;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.List;

import static satisfyu.candlelight.block.AppleLeaves.VARIANT;


public class ConfiguredFeatures {


    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STRAWBERRY_WILD_TAIGA_PATCH =
            net.minecraft.data.worldgen.features.FeatureUtils.register(CandlelightIdentifier.asString("strawberry_wild_taiga"), Feature.RANDOM_PATCH, net.minecraft.data.worldgen.features.FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ObjectRegistry.STRAWBERRY_WILD_TAIGA.defaultBlockState())), List.of(Blocks.GRASS_BLOCK), 36));
    public static final Holder<PlacedFeature> STRAWBERRY_WILD_TAIGA_PATCH_CHANCE = PlacementUtils.register(CandlelightIdentifier.asString("strawberry_wild_taiga_patch_chance"), STRAWBERRY_WILD_TAIGA_PATCH, RarityFilter.onAverageOnceEvery(92), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());


    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STRAWBERRY_WILD_JUNGLE_PATCH =
            net.minecraft.data.worldgen.features.FeatureUtils.register(CandlelightIdentifier.asString("strawberry_wild_jungle"), Feature.RANDOM_PATCH, net.minecraft.data.worldgen.features.FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ObjectRegistry.STRAWBERRY_WILD_JUNGLE.defaultBlockState())), List.of(Blocks.GRASS_BLOCK), 36));
    public static final Holder<PlacedFeature> STRAWBERRY_WILD_JUNGLE_PATCH_CHANCE = PlacementUtils.register(CandlelightIdentifier.asString("strawberry_wild_jungle_patch_chance"), STRAWBERRY_WILD_JUNGLE_PATCH, RarityFilter.onAverageOnceEvery(92), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());


    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TOMATOES_WILD_PATCH =
            net.minecraft.data.worldgen.features.FeatureUtils.register(CandlelightIdentifier.asString("tomatoes_wild_patch"), Feature.RANDOM_PATCH, net.minecraft.data.worldgen.features.FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ObjectRegistry.TOMATOES_WILD.defaultBlockState())), List.of(Blocks.GRASS_BLOCK), 36));
    public static final Holder<PlacedFeature> TOMATOES_WILD_PATCH_CHANCE = PlacementUtils.register(CandlelightIdentifier.asString("tomatoes_wild_patch_chance"), TOMATOES_WILD_PATCH, RarityFilter.onAverageOnceEvery(92), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ROSE_PATCH =
            net.minecraft.data.worldgen.features.FeatureUtils.register(CandlelightIdentifier.asString("rose"), Feature.RANDOM_PATCH, net.minecraft.data.worldgen.features.FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ObjectRegistry.ROSE.defaultBlockState())), List.of(Blocks.GRASS_BLOCK), 24));
    public static final Holder<PlacedFeature> ROSE_PATCH_CHANCE = PlacementUtils.register(CandlelightIdentifier.asString("rose_patch_chance"), ROSE_PATCH, RarityFilter.onAverageOnceEvery(92), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> BROCCOLI_PATCH =
            net.minecraft.data.worldgen.features.FeatureUtils.register(CandlelightIdentifier.asString("broccoli"), Feature.RANDOM_PATCH, net.minecraft.data.worldgen.features.FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ObjectRegistry.WILD_BROCCOLI.defaultBlockState())), List.of(Blocks.GRASS_BLOCK), 24));
    public static final Holder<PlacedFeature> BROCCOLI_PATCH_CHANCE = PlacementUtils.register(CandlelightIdentifier.asString("broccoli_patch_chance"), BROCCOLI_PATCH, RarityFilter.onAverageOnceEvery(92), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> APPLE_TREE = net.minecraft.data.worldgen.features.FeatureUtils.register(CandlelightIdentifier.asString("apple_tree"), Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(4, 14, 2), appleLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of()).forceDirt().build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> APPLE_TREE_BEE = net.minecraft.data.worldgen.features.FeatureUtils.register(CandlelightIdentifier.asString("apple_tree_bee"), Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(4, 14, 2), appleLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new BeehiveDecorator(0.5f))).forceDirt().build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> APPLE_TREE_VARIANT = net.minecraft.data.worldgen.features.FeatureUtils.register(CandlelightIdentifier.asString("apple_tree_variant"), Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(4, 14, 2), appleLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of()).forceDirt().build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> APPLE_TREE_VARIANT_WITH_BEE = net.minecraft.data.worldgen.features.FeatureUtils.register(CandlelightIdentifier.asString("apple_tree_variant_with_bee"), Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(4, 14, 2), appleLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new BeehiveDecorator(0.5f))).forceDirt().build());

    public static final Holder<PlacedFeature> TREE_APPLE = PlacementUtils.register("tree_apple", APPLE_TREE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.01f, 1), ObjectRegistry.APPLE_TREE_SAPLING.get()));

    private static RandomPatchConfiguration getFlowerGrassConfig(Block flowerGrass) {
        return new RandomPatchConfiguration(8, 2, 0, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(flowerGrass.defaultBlockState())), createBlockPredicate(List.of(Blocks.GRASS_BLOCK))));
    }

    public static WeightedStateProvider appleLeaveProvider(){
        return new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ObjectRegistry.APPLE_LEAVES.get().defaultBlockState(), 10).add(ObjectRegistry.APPLE_LEAVES.get().defaultBlockState().setValue(VARIANT, true), 4).add(ObjectRegistry.APPLE_LEAVES.get().defaultBlockState().setValue(VARIANT, true).setValue(AppleLeaves.HAS_APPLES, true), 2));
    }

    public static void init() {
        BiomeModification world = BiomeModifications.create(new CandlelightIdentifier("world_features"));
        Predicate<BiomeSelectionContext> taigaBiomes = BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.TAIGA, Biomes.DARK_FOREST, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA);
        Predicate<BiomeSelectionContext> jungleBiomes = BiomeSelectors.includeByKey(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE);
        Predicate<BiomeSelectionContext> dryBiomes = BiomeSelectors.includeByKey(Biomes.SAVANNA, Biomes.RIVER, Biomes.SWAMP, Biomes.SAVANNA_PLATEAU, Biomes.BEACH, Biomes.WOODED_BADLANDS, Biomes.SUNFLOWER_PLAINS, Biomes.PLAINS);
        Predicate<BiomeSelectionContext> coldBiomes = BiomeSelectors.includeByKey(Biomes.SNOWY_TAIGA, Biomes.SNOWY_BEACH, Biomes.SNOWY_PLAINS);
        Predicate<BiomeSelectionContext> plainsBiomes = BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.PLAINS, Biomes.SWAMP, Biomes.BIRCH_FOREST, Biomes.MEADOW, Biomes.SUNFLOWER_PLAINS, Biomes.RIVER);

        world.add(ModificationPhase.ADDITIONS, plainsBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BROCCOLI_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, plainsBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ROSE_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, taigaBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, STRAWBERRY_WILD_TAIGA_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, jungleBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, STRAWBERRY_WILD_JUNGLE_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, dryBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TOMATOES_WILD_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, coldBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, STRAWBERRY_WILD_TAIGA_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, getTreesSelector(), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TREE_APPLE.value()));
    }

    private static Predicate<BiomeSelectionContext> getTreesSelector() {
        return BiomeSelectors.includeByKey(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS);
    }

    private static BlockPredicate createBlockPredicate(List<Block> validGround) {
        return !validGround.isEmpty() ? BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(validGround)) : BlockPredicate.ONLY_IN_AIR_PREDICATE;
    }

}

