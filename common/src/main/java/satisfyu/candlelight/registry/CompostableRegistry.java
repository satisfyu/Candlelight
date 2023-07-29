package satisfyu.candlelight.registry;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRegistry {

    public static void init() {
        registerCompostableItem(ObjectRegistry.BROCCOLI_SEEDS.get(), .2f);
        registerCompostableItem(ObjectRegistry.BROCCOLI.get(), .3f);
        registerCompostableItem(ObjectRegistry.BROCCOLI_CROP.get(), .3f);
        registerCompostableItem(ObjectRegistry.TOMATO.get(), .3f);
        registerCompostableItem(ObjectRegistry.TOMATO_SEEDS.get(), .2f);
        registerCompostableItem(ObjectRegistry.TOMATO_CROP.get(), .3f);
        registerCompostableItem(ObjectRegistry.ROSE.get(), .3f);
        registerCompostableItem(ObjectRegistry.DOUGH.get(), .4f);
        registerCompostableItem(ObjectRegistry.PASTA_RAW.get(), .4f);
        registerCompostableItem(ObjectRegistry.BROCCOLI_TOMATO.get(), 1f);
        registerCompostableItem(ObjectRegistry.VEGGIE_PLATE.get(), 1f);
    }


    public static void registerCompostableItem(ItemLike item, float chance) {
        if (item.asItem() != Items.AIR) {
            ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
        }
    }
}
