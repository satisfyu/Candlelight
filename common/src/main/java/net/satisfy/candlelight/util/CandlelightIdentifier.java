package net.satisfy.candlelight.util;

import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.Candlelight;

@SuppressWarnings("unused")
public class CandlelightIdentifier extends ResourceLocation {

    public CandlelightIdentifier(String path) {
        super(Candlelight.MOD_ID, path);
    }

    public static String asString(String path) {
        return (Candlelight.MOD_ID + ":" + path);
    }
}

