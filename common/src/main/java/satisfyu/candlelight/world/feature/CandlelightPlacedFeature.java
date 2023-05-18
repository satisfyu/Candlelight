package satisfyu.candlelight.world.feature;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import satisfyu.candlelight.util.CandlelightIdentifier;


public class CandlelightPlacedFeature {
    public static final ResourceKey<PlacedFeature> APPLE_TREE_PLACED_KEY = registerKey("apple_tree");


    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new CandlelightIdentifier(name));
    }
}

