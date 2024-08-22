package net.satisfy.candlelight.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.satisfy.candlelight.util.CandlelightIdentifier;

public class TagRegistry {
    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.create(Registries.ITEM, CandlelightIdentifier.of("ignore_block_item"));
    public static final TagKey<Item> RINGS = TagKey.create(Registries.ITEM, CandlelightIdentifier.of("rings"));
}
