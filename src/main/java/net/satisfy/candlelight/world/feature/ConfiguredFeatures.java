package net.satisfy.candlelight.world.feature;

import daniking.vinery.VineryIdentifier;
import daniking.vinery.block.GrapeBush;
import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.satisfy.candlelight.registry.ObjectRegistry;
import net.satisfy.candlelight.util.CandlelightIdentifier;

import java.util.List;
import java.util.function.Predicate;

public class ConfiguredFeatures {


    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> STRAWBERRY_WILD_TAIGA_PATCH =
            net.minecraft.world.gen.feature.ConfiguredFeatures.register(CandlelightIdentifier.asString("strawberry_wild_taiga"), Feature.RANDOM_PATCH, net.minecraft.world.gen.feature.ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(net.satisfy.candlelight.registry.ObjectRegistry.STRAWBERRY_WILD_TAIGA.getDefaultState().with(GrapeBush.AGE, 3))), List.of(Blocks.GRASS_BLOCK), 36));
    public static final RegistryEntry<PlacedFeature> STRAWBERRY_WILD_TAIGA_PATCH_CHANCE = PlacedFeatures.register(CandlelightIdentifier.asString("strawberry_wild_taiga_patch_chance"), STRAWBERRY_WILD_TAIGA_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());


    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> STRAWBERRY_WILD_JUNGLE_PATCH =
            net.minecraft.world.gen.feature.ConfiguredFeatures.register(CandlelightIdentifier.asString("strawberry_wild_jungle"), Feature.RANDOM_PATCH, net.minecraft.world.gen.feature.ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.STRAWBERRY_WILD_JUNGLE.getDefaultState().with(GrapeBush.AGE, 3))), List.of(Blocks.GRASS_BLOCK), 36));
    public static final RegistryEntry<PlacedFeature> STRAWBERRY_WILD_JUNGLE_PATCH_CHANCE = PlacedFeatures.register(CandlelightIdentifier.asString("strawberry_wild_jungle_patch_chance"), STRAWBERRY_WILD_JUNGLE_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());


    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> TOMATOES_WILD_PATCH =
            net.minecraft.world.gen.feature.ConfiguredFeatures.register(CandlelightIdentifier.asString("tomatoes_wild_patch"), Feature.RANDOM_PATCH, net.minecraft.world.gen.feature.ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.TOMATOES_WILD.getDefaultState().with(GrapeBush.AGE, 3))), List.of(Blocks.GRASS_BLOCK), 36));
    public static final RegistryEntry<PlacedFeature> TOMATOES_WILD_PATCH_CHANCE = PlacedFeatures.register(CandlelightIdentifier.asString("tomatoes_wild_patch_chance"), TOMATOES_WILD_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());



    public static void init() {
        BiomeModification world = BiomeModifications.create(new VineryIdentifier("world_features"));
        Predicate<BiomeSelectionContext> taigaBiomes = BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.TAIGA, BiomeKeys.DARK_FOREST, BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA);
        Predicate<BiomeSelectionContext> jungleBiomes = BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE);
        Predicate<BiomeSelectionContext> dryBiomes = BiomeSelectors.includeByKey(BiomeKeys.RIVER, BiomeKeys.SWAMP, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.BEACH, BiomeKeys.WOODED_BADLANDS, BiomeKeys.SUNFLOWER_PLAINS);
        Predicate<BiomeSelectionContext> coldBiomes = BiomeSelectors.includeByKey(BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_BEACH, BiomeKeys.SNOWY_PLAINS);


        world.add(ModificationPhase.ADDITIONS, taigaBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, STRAWBERRY_WILD_TAIGA_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, jungleBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, STRAWBERRY_WILD_JUNGLE_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, dryBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, TOMATOES_WILD_PATCH_CHANCE.value()));

    }
}

