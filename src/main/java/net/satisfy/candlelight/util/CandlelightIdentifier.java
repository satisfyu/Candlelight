package net.satisfy.candlelight.util;

import net.minecraft.util.Identifier;
import net.satisfy.candlelight.Candlelight;

public class CandlelightIdentifier extends Identifier {

    public CandlelightIdentifier(String path) {
        super(Candlelight.MOD_ID, path);
    }

    public static String asString(String path) {
        return (Candlelight.MOD_ID + ":" + path);
    }
}

