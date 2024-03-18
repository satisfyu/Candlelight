package satisfy.candlelight.registry;

import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRegistry {

    public static void init() {
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.LETTUCE_SEEDS.get().asItem(), .2f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.LETTUCE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.LETTUCE_CROP.get().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TOMATO.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TOMATO_SEEDS.get().asItem(), .2f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TOMATO_CROP.get().asItem().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ROSE.get().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.DOUGH.get(), .4f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PASTA_RAW.get(), .4f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.LETTUCE_TOMATO.get(), 1f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.VEGGIE_PLATE.get(), 1f);
    }
}
