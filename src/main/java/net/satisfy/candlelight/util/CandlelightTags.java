package net.satisfy.candlelight.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import satisfyu.vinery.VineryIdentifier;

public class CandlelightTags {
    public static final TagKey<Item> WINE = TagKey.of(Registry.ITEM_KEY, new CandlelightIdentifier("wine"));
    public static final TagKey<Block> ALLOWS_COOKING_ON_PAN = TagKey.of(Registry.BLOCK_KEY, new CandlelightIdentifier("allows_cooking_on_pan"));
}
