package satisfyu.candlelight.fabric.world;

import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import satisfyu.candlelight.util.CandlelightIdentifier;
import satisfyu.candlelight.world.feature.CandlelightPlacedFeature;

import java.util.function.Predicate;


public class CandlelightBiomeModification {

    public static void init() {
        BiomeModification world = BiomeModifications.create(new CandlelightIdentifier("world_features"));
        Predicate<BiomeSelectionContext> roseBiomes = getVinerySelector("spawns_rose");
        Predicate<BiomeSelectionContext> tomatoesBiomes = getVinerySelector("spawns_tomatoes");
        Predicate<BiomeSelectionContext> broccoliBiomes = getVinerySelector("spawns_broccoli");

        world.add(ModificationPhase.ADDITIONS, tomatoesBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CandlelightPlacedFeature.TOMATOES_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, roseBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CandlelightPlacedFeature.ROSE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, broccoliBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CandlelightPlacedFeature.BROCCOLI_PATCH_CHANCE_KEY));
    }

    private static Predicate<BiomeSelectionContext> getVinerySelector(String path) {
        return BiomeSelectors.tag(TagKey.create(Registry.BIOME_REGISTRY, new CandlelightIdentifier(path)));
    }



}
