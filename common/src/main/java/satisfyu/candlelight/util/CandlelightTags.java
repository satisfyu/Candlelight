package satisfyu.candlelight.util;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CandlelightTags {
    public static final TagKey<Item> WINE = TagKey.create(Registry.ITEM_REGISTRY, new CandlelightIdentifier("wine"));
    public static final TagKey<Block> ALLOWS_COOKING_ON_PAN = TagKey.create(Registry.BLOCK_REGISTRY, new CandlelightIdentifier("allows_cooking_on_pan"));
}
