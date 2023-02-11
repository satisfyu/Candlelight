package net.satisfy.candlelight.registry;

import net.minecraft.block.Block;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.model.CookingHatModel;
import net.satisfy.candlelight.client.render.block.CakeStandRenderer;
import net.satisfy.candlelight.client.render.block.TableSetRenderer;
import net.satisfy.candlelight.client.render.block.TrayRenderer;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import satisfyu.vinery.block.StorageBlock;
import satisfyu.vinery.client.render.block.StorageTypeRenderer;
import satisfyu.vinery.util.VineryApi;

import java.util.Map;
import java.util.Set;

public class StorageTypes implements VineryApi {

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

    @Override
    public <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelLoader modelLoader) {
        models.put(ObjectRegistry.COOKING_HAT, new CookingHatModel<>(modelLoader.getModelPart(CookingHatModel.LAYER_LOCATION)));
    }
}
