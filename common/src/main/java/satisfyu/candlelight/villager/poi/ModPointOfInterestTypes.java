package satisfyu.candlelight.villager.poi;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.util.CandlelightIdentifier;

public class ModPointOfInterestTypes{

    public static final ResourceKey<PoiType> SHOP_KEY = ResourceKey.create(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, new CandlelightIdentifier("shop"));


    public static void initU() {
        Candlelight.LOGGER.debug("Register " + ModPointOfInterestTypes.class);
    }
}
