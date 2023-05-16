package satisfyu.candlelight.villager.poi;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.util.CandlelightIdentifier;

public class ModPointOfInterestTypes{
    public static final PoiType SHOP = registerPOI("shop", ObjectRegistry.WINE_STATION.get());
    public static final ResourceKey<PoiType> SHOP_KEY = ResourceKey.create(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, new CandlelightIdentifier("shop"));

    public static PoiType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(new CandlelightIdentifier(name),
                100, 1, ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates()));
    }

    public static void init() {
        Candlelight.LOGGER.debug("Register " + ModPointOfInterestTypes.class);
    }
}
