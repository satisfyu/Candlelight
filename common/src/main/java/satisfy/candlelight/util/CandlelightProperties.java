package satisfy.candlelight.util;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class CandlelightProperties {
    public static final EnumProperty<LineConnectingType> LINE_CONNECTING_TYPE;
    public static final IntegerProperty HEIGHT_1_3 = IntegerProperty.create("height", 1, 3);


    static {
        LINE_CONNECTING_TYPE = EnumProperty.create("type", LineConnectingType.class);

    }
}
