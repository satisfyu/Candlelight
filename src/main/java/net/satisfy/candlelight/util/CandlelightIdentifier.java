package net.satisfy.candlelight.util;

import daniking.vinery.Vinery;
import net.minecraft.util.Identifier;

public class CandlelightIdentifier extends Identifier {

    public CandlelightIdentifier(String path) {
        super(Vinery.MODID, path);
    }

    public static String asString(String path) {
        return (Vinery.MODID + ":" + path);
    }
}

