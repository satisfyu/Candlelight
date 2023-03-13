package net.satisfy.candlelight.util;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import satisfyu.vinery.VineryIdentifier;

public class CandlelightTags {
    public static final TagKey<Item> WINE = TagKey.of(Registry.ITEM_KEY, new CandlelightIdentifier("wine"));
}
