package satisfyu.candlelight.fabric.world;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import satisfyu.candlelight.registry.ObjectRegistry;

public class CompostableFabricRegistry {

    public static void init() {
        CompostingChanceRegistry.INSTANCE.add(ObjectRegistry.ROSE.get(), .3f);
        CompostingChanceRegistry.INSTANCE.add(ObjectRegistry.BROCCOLI_SEEDS.get(), .2f);
        CompostingChanceRegistry.INSTANCE.add(ObjectRegistry.TOMATO_SEEDS.get(), .2f);
        CompostingChanceRegistry.INSTANCE.add(ObjectRegistry.BROCCOLI.get(), .3f);
        CompostingChanceRegistry.INSTANCE.add(ObjectRegistry.TOMATO.get(), .3f);
        CompostingChanceRegistry.INSTANCE.add(ObjectRegistry.DOUGH.get(),.4f);
        CompostingChanceRegistry.INSTANCE.add(ObjectRegistry.PASTA_RAW.get(),.4f);
        CompostingChanceRegistry.INSTANCE.add(ObjectRegistry.BROCCOLI_TOMATO.get(),1f);
        CompostingChanceRegistry.INSTANCE.add(ObjectRegistry.VEGGIE_PLATE.get(), 1f);

    }
}
