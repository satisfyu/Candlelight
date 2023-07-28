package satisfyu.candlelight.registry;

import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRegistry {

    public static void init() {
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BROCCOLI_SEEDS.get(), .2f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BROCCOLI.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TOMATO.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TOMATO_SEEDS.get(), .2f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ROSE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.DOUGH.get(), .4f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PASTA_RAW.get(), .4f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BROCCOLI_TOMATO.get(), 1f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.VEGGIE_PLATE.get(), 1f);
    }
}

