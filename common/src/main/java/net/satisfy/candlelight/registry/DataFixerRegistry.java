package net.satisfy.candlelight.registry;

import de.cristelknight.doapi.common.util.datafixer.DataFixers;
import de.cristelknight.doapi.common.util.datafixer.StringPairs;
import net.satisfy.candlelight.Candlelight;

public class DataFixerRegistry {

    public static void init() {
        StringPairs p = DataFixers.getOrCreate(Candlelight.MOD_ID);
        p.add("candlelight:pasta", "candlelight:pasta_with_mozzarella");
        p.add("candlelight:lettuce_salad", "candlelight:salad");
        p.add("candlelight:cooked_beef", "candlelight:fillet_steak");
        p.add("candlelight:pasta_bolognese", "candlelight:pasta_with_bolognese");
        p.add("candlelight:salmon_wine", "candlelight:salmon_on_white_wine");
        p.add("candlelight:pancake", "candlelight:omelet");
        p.add("candlelight:ettuce_beef", "candlelight:roasted_lamb_with_lettuce");
        p.add("candlelight:lettuce_tomato_block", "candlelight:fresh_garden_salad_block");
        p.add("candlelight:lettuce_tomato", "candlelight:fresh_garden_salad");
        p.add("candlelight:veggie_plate", "candlelight:harvest_plate");
        p.add("candlelight:fricasse", "candlelight:beef_with_mushroom_in_wine_and_potatoes");
        p.add("candlelight:chocolate", "candlelight:chocolate_mousse");
        p.add("candlelight:roastbeef_carrots", "candlelight:roastbeef_with_glazed_carrots");
        p.add("candlelight:lasagna_block", "candlelight:lasagne_block");
        p.add("candlelight:lasagna", "candlelight:lasagne");
        p.add("candlelight:chicken", "candlelight:chicken_with_vegetables");
        p.add("candlelight:glass_block", "candlelight:wine_glass");
        p.add("candlelight:tomato_crop", "farm_and_charm:tomato_crop");
        p.add("candlelight:tomato_seeds", "farm_and_charm:tomato_seeds");
        p.add("candlelight:lettuce_crop", "farm_and_charm:lettuce_crop");
        p.add("candlelight:lettuce_seeds", "farm_and_charm:lettuce_seeds");
        p.add("candlelight:wild_lettuce", "farm_and_charm:wild_lettuce");
        p.add("candlelight:wild_tomatoes", "farm_and_charm:wild_tomatoes");
        p.add("candlelight:tool_rack", "farm_and_charm:tool_rack");
        p.add("candlelight:tomato_bag", "farm_and_charm:tomato_bag");
        p.add("candlelight:lettuce_bag", "farm_and_charm:lettuce_bag");
        p.add("candlelight:carrot_bag", "farm_and_charm:carrot_bag");
        p.add("candlelight:potato_bag", "farm_and_charm:potato_bag");
        p.add("candlelight:beetroot_bag", "farm_and_charm:beetroot_bag");
        p.add("candlelight:dough", "farm_and_charm:dough");
        p.add("candlelight:pasta_raw", "farm_and_charm:raw_pasta");
        p.add("candlelight:rotten_tomato", "farm_and_charm:rotten_tomato");
    }
}