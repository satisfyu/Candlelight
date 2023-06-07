package satisfyu.candlelight.client;

import de.cristelknight.doapi.client.render.block.storage.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.block.storage.StorageTypeRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.client.render.block.TableSetRenderer;
import satisfyu.candlelight.registry.DoAPIImpl;
@Environment(EnvType.CLIENT)
public class StorageTypes {




    public static void init(){
        Candlelight.LOGGER.debug("Registering Storage Block Renderers!");
        //registerStorageType(DoAPIImpl.CAKE_STAND, new CakeStandRenderer());

        registerStorageType(DoAPIImpl.TABLE_SET, new TableSetRenderer());

        //registerStorageType(DoAPIImpl.TRAY, new TrayRenderer());
    }

    public static ResourceLocation registerStorageType(ResourceLocation location, StorageTypeRenderer renderer){
        return StorageBlockEntityRenderer.registerStorageType(location, renderer);
    }

}
