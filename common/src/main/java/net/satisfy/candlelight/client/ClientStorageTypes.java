package net.satisfy.candlelight.client;

import de.cristelknight.doapi.client.render.block.storage.api.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.block.storage.api.StorageTypeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.render.ToolRackRenderer;
import net.satisfy.candlelight.registry.StorageTypesRegistry;
import net.satisfy.candlelight.client.render.JewelryRenderer;
import net.satisfy.candlelight.client.render.ShelfRenderer;
import net.satisfy.candlelight.client.render.TableSetRenderer;

public class ClientStorageTypes {
    public static void registerStorageType(ResourceLocation location, StorageTypeRenderer renderer){
        StorageBlockEntityRenderer.registerStorageType(location, renderer);
    }

    public static void init(){
        Candlelight.LOGGER.debug("Registering Storage Block Renderers!");
        registerStorageType(StorageTypesRegistry.TABLE_BOWL, new TableSetRenderer());
        registerStorageType(StorageTypesRegistry.TABLE_SET, new TableSetRenderer());
        registerStorageType(StorageTypesRegistry.SHELF, new ShelfRenderer());
        registerStorageType(StorageTypesRegistry.TOOL_RACK, new ToolRackRenderer());
        registerStorageType(StorageTypesRegistry.JEWELRY_BOX, new JewelryRenderer());
    }
}
