package net.satisfy.candlelight.registry;

import daniking.vinery.Vinery;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.satisfy.candlelight.util.CIdentifier;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;


public class Objects {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();



    private static <T extends Block> T register(String path, T block) {
        return register(path, block, true);
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem) {
        return register(path, block, registerItem, settings -> {});
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem, Consumer<Item.Settings> consumer) {
        return register(path, block, registerItem, BlockItem::new, consumer);
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem, BiFunction<T, Item.Settings, ? extends BlockItem> function, Consumer<Item.Settings> consumer) {
        final Identifier id = new CIdentifier(path);
        BLOCKS.put(id, block);
        if (registerItem) {
            ITEMS.put(id, function.apply(block, getSettings(consumer)));
        }
        return block;
    }

    private static <T extends Item> T registerItem(String path, T item) {
        ITEMS.put(new CIdentifier(path), item);
        return item;
    }

    private static Item.Settings getSettings(Consumer<Item.Settings> consumer) {
        Item.Settings settings = new Item.Settings().group(Vinery.CREATIVE_TAB);
        consumer.accept(settings);
        return settings;
    }

    public static Map<Identifier, Block> getBlocks() {
        return Collections.unmodifiableMap(BLOCKS);
    }

    public static Map<Identifier, Item> getItems() {
        return Collections.unmodifiableMap(ITEMS);
    }

    public static void register(){
        Map<Identifier, Block> blocks = getBlocks();
        for(Identifier id : blocks.keySet()){
            Registry.register(Registry.BLOCK, id, blocks.get(id));
        }

        Map<Identifier, Item> items = getItems();
        for(Identifier id : items.keySet()){
            Registry.register(Registry.ITEM, id, items.get(id));
        }
    }
}
