package satisfyu.candlelight.fabric.registry;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.util.CandlelightIdentifier;

public class CandlelightPoiTypesFabric {

    public static final PoiType SHOP = registerPOI("shop", ObjectRegistry.WINE_STATION.get());

    public static PoiType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(new CandlelightIdentifier(name),
                100, 1, ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates()));
    }

    public static void loadClass(){

    }

}
