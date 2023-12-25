package satisfy.candlelight.util;

import net.minecraft.util.StringRepresentable;

public enum CandlelightLineConnectingType implements StringRepresentable {
    NONE("none"),
    MIDDLE("middle"),
    LEFT("left"),
    RIGHT("right");

    private final String name;

    private CandlelightLineConnectingType(String type) {
        this.name = type;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
