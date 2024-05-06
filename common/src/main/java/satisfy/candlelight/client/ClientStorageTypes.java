package satisfy.candlelight.client;

import de.cristelknight.doapi.client.render.block.storage.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.block.storage.StorageTypeRenderer;
import net.minecraft.resources.ResourceLocation;
import satisfy.candlelight.Candlelight;
import satisfy.candlelight.client.render.JewelryRenderer;
import satisfy.candlelight.client.render.ShelfRenderer;
import satisfy.candlelight.client.render.TableSetRenderer;
import satisfy.candlelight.registry.StorageTypeRegistry;

public class ClientStorageTypes {
    public static void registerStorageType(ResourceLocation location, StorageTypeRenderer renderer){
        StorageBlockEntityRenderer.registerStorageType(location, renderer);
    }

    public static void init(){
        Candlelight.LOGGER.debug("Registering Storage Block Renderers!");
        registerStorageType(StorageTypeRegistry.TABLE_SET, new TableSetRenderer());
        registerStorageType(StorageTypeRegistry.SHELF, new ShelfRenderer());
        registerStorageType(StorageTypeRegistry.JEWELRY_BOX, new JewelryRenderer());
    }
}
