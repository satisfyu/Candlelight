package satisfy.candlelight.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRegistry {

    public static void init() {
        registerCompostableItem(ObjectRegistry.LETTUCE_SEEDS, .2f);
        registerCompostableItem(ObjectRegistry.LETTUCE, .3f);
        registerCompostableItem(ObjectRegistry.LETTUCE_CROP, .3f);
        registerCompostableItem(ObjectRegistry.TOMATO, .3f);
        registerCompostableItem(ObjectRegistry.TOMATO_SEEDS, .2f);
        registerCompostableItem(ObjectRegistry.TOMATO_CROP, .3f);
        registerCompostableItem(ObjectRegistry.ROSE, .3f);
        registerCompostableItem(ObjectRegistry.DOUGH, .4f);
        registerCompostableItem(ObjectRegistry.PASTA_RAW, .4f);
        registerCompostableItem(ObjectRegistry.LETTUCE_TOMATO, 1f);
        registerCompostableItem(ObjectRegistry.VEGGIE_PLATE, 1f);
    }


    public static <T extends ItemLike> void registerCompostableItem(RegistrySupplier<T> item, float chance) {
        if (item.get().asItem() != Items.AIR) {
            ComposterBlock.COMPOSTABLES.put(item.get(), chance);
        }
    }
}
