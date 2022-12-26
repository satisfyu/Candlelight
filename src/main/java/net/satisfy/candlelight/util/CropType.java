package net.satisfy.candlelight.util;

import net.minecraft.util.StringIdentifiable;

public enum CropType implements StringIdentifiable  {
    TOMATO,
    STRAWBERRY;

    public String toString() {
        return this.asString();
    }
    @Override
    public String asString() {
        return this == TOMATO ? "tomato" : "strawberry";
    }

}
