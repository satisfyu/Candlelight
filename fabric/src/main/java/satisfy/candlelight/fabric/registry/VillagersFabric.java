package satisfy.candlelight.fabric.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;

import satisfy.candlelight.registry.ObjectRegistry;
import satisfy.candlelight.util.CandlelightIdentifier;

@SuppressWarnings("deprecation")
public class VillagersFabric {

    private static final CandlelightIdentifier COOK_POI_IDENTIFIER = new CandlelightIdentifier("cook_poi");
    public static final PoiType COOK_POI = PointOfInterestHelper.register(COOK_POI_IDENTIFIER, 1, 12, ObjectRegistry.COOKING_POT.get());
    public static final VillagerProfession COOK = Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, new ResourceLocation("candlelight", "cook"), VillagerProfessionBuilder.create().id(new ResourceLocation("candlelight", "cook")).workstation(ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, COOK_POI_IDENTIFIER)).build());

    public static void init() {
        TradeOfferHelper.registerVillagerOffers(COOK, 1, factories -> {
            factories.add(new VillagerUtil.BuyForOneEmeraldFactory(satisfy.farm_and_charm.registry.ObjectRegistry.LETTUCE.get(), 4, 4, 1));
            factories.add(new VillagerUtil.BuyForOneEmeraldFactory(satisfy.farm_and_charm.registry.ObjectRegistry.TOMATO.get(), 4, 4, 1));
            factories.add(new VillagerUtil.SellItemFactory(satisfy.farm_and_charm.registry.ObjectRegistry.TOMATO_SEEDS.get(), 5, 2, 1));
            factories.add(new VillagerUtil.SellItemFactory(satisfy.farm_and_charm.registry.ObjectRegistry.LETTUCE_SEEDS.get(), 4, 2, 1));
            factories.add(new VillagerUtil.SellItemFactory(satisfy.farm_and_charm.registry.ObjectRegistry.BUTTER.get(), 2, 6, 2));

        });

        TradeOfferHelper.registerVillagerOffers(COOK, 2, factories -> {
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.COOKING_PAN.get(), 6, 1, 3));
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.COOKING_POT.get(), 7, 1, 3));
            factories.add(new VillagerUtil.SellItemFactory(satisfy.farm_and_charm.registry.ObjectRegistry.TOOL_RACK.get(), 4, 2, 2));
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.NAPKIN.get(), 1, 2, 4));

        });

        TradeOfferHelper.registerVillagerOffers(COOK, 3, factories -> {
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.TOMATO_SOUP.get(), 2, 1, 2));
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.PASTA_RAW.get(), 2, 2, 3));
            factories.add(new VillagerUtil.SellItemFactory(satisfy.farm_and_charm.registry.ObjectRegistry.DOUGH.get(), 1, 3, 3));
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.MUSHROOM_SOUP.get(), 1, 4, 2));
        });

        TradeOfferHelper.registerVillagerOffers(COOK, 4, factories -> {
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.BEEF_TARTARE.get(), 5, 2, 5));
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.BEETROOT_SALAD.get(), 4, 1, 5));
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.PANCAKE.get(), 3, 4, 5));
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.LETTUCE_SALAD.get(), 3, 1, 5));
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.TABLE_BOWL.get(), 2, 1, 5));
        });

        TradeOfferHelper.registerVillagerOffers(COOK, 5, factories -> {
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.COOKING_HAT.get(), 9, 1, 5));
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.CHOCOLATE_BOX.get(), 9, 1, 5));
            factories.add(new VillagerUtil.SellItemFactory(ObjectRegistry.GLASS.get(), 3, 1, 5));
            factories.add(new VillagerUtil.BuyForOneEmeraldFactory(ObjectRegistry.BEEF_WELLINGTON.get(), 24, 1, 5));
            factories.add(new VillagerUtil.BuyForOneEmeraldFactory(ObjectRegistry.TROPICAL_FISH_SUPREME.get(), 1, 1, 5));
        });
    }
}