package net.satisfy.candlelight.registry;

import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRegistry {

    public static void init() {
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ROSE.get().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.MOZZARELLA.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TOMATO_SOUP.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.MUSHROOM_SOUP.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BEETROOT_SALAD.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PASTA_WITH_MOZZARELLA.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BOLOGNESE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHICKEN_TERIYAKI.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SALAD.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHOCOLATE_MOUSSE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BEEF_TARTARE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PASTA_WITH_BOLOGNESE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PASTA_WITH_LETTUCE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.OMELET.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.FRESH_GARDEN_SALAD.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.HARVEST_PLATE.get().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BEEF_WITH_MUSHROOM_IN_WINE_AND_POTATOES.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TOMATO_MOZZARELLA_SALAD.get().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PORK_RIBS.get().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ROASTBEEF_WITH_GLAZED_CARROTS.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ROASTED_LAMB_WITH_LETTUCE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TROPICAL_FISH_SUPREME.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.FILLET_STEAK.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SALMON_ON_WHITE_WINE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHICKEN_ALFREDO.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHICKEN_WITH_VEGETABLES.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.LASAGNE.get().asItem(), .3f);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BEEF_WELLINGTON.get().asItem(), .3f);
    }
}
