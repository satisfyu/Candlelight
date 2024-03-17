package satisfy.candlelight.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfy.candlelight.util.CandlelightIdentifier;

@SuppressWarnings("unused")
public class TagsRegistry {
    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.create(Registries.ITEM, new CandlelightIdentifier("ignore_block_item"));
    public static final TagKey<Item> RINGS = TagKey.create(Registries.ITEM, new CandlelightIdentifier("rings"));
    public static final TagKey<Block> ALLOWS_COOKING = TagKey.create(Registries.BLOCK, new CandlelightIdentifier("allows_cooking"));
}
