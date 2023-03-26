package net.satisfy.candlelight.villager.poi;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.poi.PointOfInterestType;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.registry.ObjectRegistry;
import net.satisfy.candlelight.util.CandlelightIdentifier;

public class ModPointOfInterestTypes{
    public static final PointOfInterestType SHOP = registerPOI("shop", ObjectRegistry.WINE_STATION);
    public static final RegistryKey<PointOfInterestType> SHOP_KEY = RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, new CandlelightIdentifier("shop"));

    public static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(new CandlelightIdentifier(name),
                100, 1, ImmutableSet.copyOf(block.getStateManager().getStates()));
    }

    public static void init() {
        Candlelight.LOGGER.debug("Register " + ModPointOfInterestTypes.class);
    }
}
