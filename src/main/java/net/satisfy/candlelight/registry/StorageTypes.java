package net.satisfy.candlelight.registry;

import daniking.vinery.block.StorageBlock;
import daniking.vinery.client.render.block.StorageTypeRenderer;
import daniking.vinery.util.StorageTypeApi;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.render.block.CakeStandRenderer;
import net.satisfy.candlelight.client.render.block.TableSetRenderer;
import net.satisfy.candlelight.client.render.block.TrayRenderer;
import net.satisfy.candlelight.util.CandlelightIdentifier;

import java.util.Set;

public class StorageTypes implements StorageTypeApi {

    public static final Identifier CAKE_STAND = registerStorageType("cake_stand", new CakeStandRenderer());

    public static final Identifier TABLE_SET = registerStorageType("table_set", new TableSetRenderer());

    public static final Identifier TRAY = registerStorageType("tray", new TrayRenderer());


    public static void init(){
        Candlelight.LOGGER.debug("Registering Storage Block Renderers!");
    }

    public static Identifier registerStorageType(String string, StorageTypeRenderer renderer){
        return StorageBlock.registerStorageType(new CandlelightIdentifier(string), renderer);
    }

    @Override
    public void registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.CAKE_STAND);
        blocks.add(ObjectRegistry.TABLE_SET);
        blocks.add(ObjectRegistry.TRAY);
    }
}
