package net.satisfy.candlelight.util;

import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.Candlelight;

@SuppressWarnings("unused")
public class CandlelightIdentifier {
    public static ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(Candlelight.MOD_ID, path);
    }

    public static ResourceLocation of(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}

