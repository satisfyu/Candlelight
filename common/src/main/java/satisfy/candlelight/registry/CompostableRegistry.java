package satisfy.candlelight.registry;

import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRegistry {

    public static void init() {
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ROSE.get().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.MOZZARELLA.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TOMATO_SOUP.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.MUSHROOM_SOUP.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BEETROOT_SALAD.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PASTA.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BOLOGNESE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHICKEN_TERIYAKI.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.LETTUCE_SALAD.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHOCOLATE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BEEF_TARTARE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PASTA_BOLOGNESE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PASTA_LETTUCE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PANCAKE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.LETTUCE_TOMATO.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.VEGGIE_PLATE.get().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.FRICASSE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TOMATO_MOZZARELLA_SALAD.get().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PORK_RIBS.get().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ROASTBEEF_CARROTS.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.LETTUCE_BEEF.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TROPICAL_FISH_SUPREME.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.COOKED_BEEF.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SALMON_WINE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHICKEN_ALFREDO.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHICKEN.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.LASAGNA.get().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BEEF_WELLINGTON.get().asItem(), .3f);
    }
}
