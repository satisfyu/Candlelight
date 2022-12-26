package net.satisfy.candlelight.item;

import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.satisfy.candlelight.util.CropType;

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
