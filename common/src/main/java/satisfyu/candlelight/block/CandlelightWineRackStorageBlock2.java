package satisfyu.candlelight.block;

import satisfyu.vinery.block.WineRackStorageBlock;
import satisfyu.vinery.registry.VinerySoundEvents;

public class CandlelightWineRackStorageBlock2 extends WineRackStorageBlock {
    public CandlelightWineRackStorageBlock2(Properties settings) {
        super(settings, VinerySoundEvents.WINE_RACK_5_OPEN.get(), VinerySoundEvents.WINE_RACK_5_CLOSE.get());
    }
}
