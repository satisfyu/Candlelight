package net.satisfy.candlelight.util;

import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;

public class CropSeedItem extends AliasedBlockItem {

    private final CropType type;

    public CropSeedItem(Block block, Settings settings, CropType type) {
        super(block, settings);
        this.type = type;
    }

    public CropType getType() {
        return type;
    }
}
