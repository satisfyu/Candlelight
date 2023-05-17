package satisfyu.candlelight.block;

import satisfyu.vinery.block.WineRackStorageBlock;
import satisfyu.vinery.registry.VinerySoundEvents;

public class CandlelightWineRackStorageBlock extends WineRackStorageBlock {
    public CandlelightWineRackStorageBlock(Properties settings) {
        super(settings, VinerySoundEvents.WINE_RACK_3_OPEN.get(), VinerySoundEvents.WINE_RACK_3_CLOSE.get());
    }
}
