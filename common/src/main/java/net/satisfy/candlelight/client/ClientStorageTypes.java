package net.satisfy.candlelight.client;

import de.cristelknight.doapi.client.render.block.storage.api.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.block.storage.api.StorageTypeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.render.JewelryRenderer;
import net.satisfy.candlelight.client.render.ShelfRenderer;
import net.satisfy.candlelight.client.render.TableSetRenderer;
import net.satisfy.candlelight.registry.StorageTypeRegistry;

public class ClientStorageTypes {
    public static void registerStorageType(ResourceLocation location, StorageTypeRenderer renderer) {
        StorageBlockEntityRenderer.registerStorageType(location, renderer);
    }

    public static void init() {
        Candlelight.LOGGER.debug("Registering Storage Block Renderers!");
        registerStorageType(StorageTypeRegistry.TABLE_SET, new TableSetRenderer());
        registerStorageType(StorageTypeRegistry.SHELF, new ShelfRenderer());
        registerStorageType(StorageTypeRegistry.JEWELRY_BOX, new JewelryRenderer());
    }
}
