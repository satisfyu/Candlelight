package satisfyu.candlelight.world.feature;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import satisfyu.candlelight.util.CandlelightIdentifier;



public class CandlelightConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> APPLE_KEY = registerKey("apple_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> APPLE_VARIANT_KEY = registerKey("apple_tree_variant");


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new CandlelightIdentifier(name));
    }


}

