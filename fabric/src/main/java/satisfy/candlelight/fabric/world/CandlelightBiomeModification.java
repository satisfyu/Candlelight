package satisfy.candlelight.fabric.world;

import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import satisfy.candlelight.util.CandlelightIdentifier;
import satisfy.candlelight.world.feature.CandlelightPlacedFeature;

import java.util.function.Predicate;


public class CandlelightBiomeModification {

    public static void init() {
        BiomeModification world = BiomeModifications.create(new CandlelightIdentifier("world_features"));
        Predicate<BiomeSelectionContext> spawnsRose = getCandlelightSelector();
        world.add(ModificationPhase.ADDITIONS, spawnsRose, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CandlelightPlacedFeature.ROSE_PATCH_CHANCE_KEY));
    }

    private static Predicate<BiomeSelectionContext> getCandlelightSelector() {
        return BiomeSelectors.tag(TagKey.create(Registries.BIOME, new CandlelightIdentifier("spawns_rose")));
    }



}
