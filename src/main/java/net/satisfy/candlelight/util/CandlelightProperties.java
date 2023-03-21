package net.satisfy.candlelight.util;

import net.minecraft.state.property.EnumProperty;
import net.satisfy.candlelight.block.LineConnectingType;

public class CandlelightProperties {
    public static final EnumProperty<LineConnectingType> LINE_CONNECTING_TYPE;

    static {
        LINE_CONNECTING_TYPE = EnumProperty.of("type", LineConnectingType.class);
    }
}
