package net.satisfy.candlelight.util;

import net.minecraft.util.Identifier;
import net.satisfy.candlelight.Candlelight;

public class CIdentifier extends Identifier {
    public CIdentifier(String path) {
        super(Candlelight.MOD_ID, path);
    }

    public String asString(String path){
        return new CIdentifier(path).toString();
    }
}
