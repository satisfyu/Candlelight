package satisfy.candlelight.forge;
/*
import de.cristelknight.doapi.common.util.VillagerUtil;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import satisfy.candlelight.Candlelight;
import satisfy.candlelight.forge.registry.CandlelightForgeVillagers;
import satisfy.candlelight.registry.ObjectRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//TODO

public class CandlelightEvents {

    @Mod.EventBusSubscriber(modid = Candlelight.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event) {
            if (event.getType().equals(CandlelightForgeVillagers.COOK.get())) {
                Map<Integer, List<VillagerTrades.ItemListing>> trades = new HashMap<>(event.getTrades());

                List<VillagerTrades.ItemListing> level1 = trades.get(1);
                level1.add(new VillagerUtil.BuyForOneEmeraldFactory(ObjectRegistry.LETTUCE.get(), 4, 4, 2));
                level1.add(new VillagerUtil.BuyForOneEmeraldFactory(ObjectRegistry.TOMATO.get(), 4, 4, 2));
                level1.add(new VillagerUtil.SellItemFactory(ObjectRegistry.TOMATO_SEEDS.get(), 5, 2, 3));
                level1.add(new VillagerUtil.SellItemFactory(ObjectRegistry.LETTUCE_SEEDS.get(), 4, 2, 2));
                level1.add(new VillagerUtil.SellItemFactory(ObjectRegistry.BUTTER.get(), 2, 6, 4));

                List<VillagerTrades.ItemListing> level2 = trades.get(2);
                level2.add(new VillagerUtil.SellItemFactory(ObjectRegistry.COOKING_PAN.get(), 6, 1, 3));
                level2.add(new VillagerUtil.SellItemFactory(ObjectRegistry.COOKING_POT.get(), 7, 1, 3));
                level2.add(new VillagerUtil.SellItemFactory(ObjectRegistry.TOOL_RACK.get(), 4, 2, 2));
                level2.add(new VillagerUtil.SellItemFactory(ObjectRegistry.NAPKIN.get(), 1, 2, 4));

                List<VillagerTrades.ItemListing> level3 = trades.get(3);
                level3.add(new VillagerUtil.SellItemFactory(ObjectRegistry.TOMATO_SOUP.get(), 2, 1, 2));
                level3.add(new VillagerUtil.SellItemFactory(ObjectRegistry.PASTA_RAW.get(), 2, 1, 2));
                level3.add(new VillagerUtil.SellItemFactory(ObjectRegistry.DOUGH.get(), 1, 1, 1));
                level3.add(new VillagerUtil.SellItemFactory(ObjectRegistry.MUSHROOM_SOUP.get(), 1, 4, 2));

                List<VillagerTrades.ItemListing> level4 = trades.get(4);
                level4.add(new VillagerUtil.SellItemFactory(ObjectRegistry.BEEF_TARTARE.get(), 5, 1, 5));
                level4.add(new VillagerUtil.SellItemFactory(ObjectRegistry.BEETROOT_SALAD.get(), 4, 1, 5));
                level4.add(new VillagerUtil.SellItemFactory(ObjectRegistry.OMELET.get(), 3, 1, 5));
                level4.add(new VillagerUtil.SellItemFactory(ObjectRegistry.LETTUCE_SALAD.get(), 3, 1, 5));
                level4.add(new VillagerUtil.SellItemFactory(ObjectRegistry.TABLE_BOWL.get(), 2, 1, 5));

                List<VillagerTrades.ItemListing> level5 = trades.get(5);
                level5.add(new VillagerUtil.SellItemFactory(ObjectRegistry.COOKING_HAT.get(), 9, 1, 5));
                level5.add(new VillagerUtil.SellItemFactory(ObjectRegistry.CHOCOLATE_BOX.get(), 9, 1, 5));
                level5.add(new VillagerUtil.SellItemFactory(ObjectRegistry.GLASS.get(), 3, 1, 5));
                level5.add(new VillagerUtil.BuyForOneEmeraldFactory(ObjectRegistry.BEEF_WELLINGTON.get(), 1, 2, 5));
                level5.add(new VillagerUtil.BuyForOneEmeraldFactory(ObjectRegistry.TROPICAL_FISH_SUPREME.get(), 1, 2, 5));

                event.getTrades().clear();
                event.getTrades().putAll(trades);
            }
        }
    }
}
*/