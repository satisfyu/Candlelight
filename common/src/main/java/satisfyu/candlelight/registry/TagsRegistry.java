package satisfyu.candlelight.registry;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfyu.candlelight.util.CandlelightIdentifier;

public class TagsRegistry {
    public static final TagKey<Item> FAUCET = TagKey.create(Registry.ITEM_REGISTRY, new CandlelightIdentifier("faucet"));
    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.create(Registry.ITEM.key(), new CandlelightIdentifier("ignore_block_item"));
    public static final TagKey<Block> ALLOWS_COOKING = TagKey.create(Registry.BLOCK_REGISTRY, new CandlelightIdentifier("allows_cooking"));

}
