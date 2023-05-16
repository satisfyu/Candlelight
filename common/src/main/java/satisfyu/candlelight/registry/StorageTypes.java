package satisfyu.candlelight.registry;

import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import de.cristelknight.doapi.client.render.block.storage.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.block.storage.StorageTypeRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.client.model.CookingHatModel;
import satisfyu.candlelight.client.render.block.CakeStandRenderer;
import satisfyu.candlelight.client.render.block.TableSetRenderer;
import satisfyu.candlelight.client.render.block.TrayRenderer;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.Map;
import java.util.Set;

@DoApiPlugin
public class StorageTypes implements DoApiAPI {

    public static final ResourceLocation CAKE_STAND = registerStorageType("cake_stand", new CakeStandRenderer());

    public static final ResourceLocation TABLE_SET = registerStorageType("table_set", new TableSetRenderer());

    public static final ResourceLocation TRAY = registerStorageType("tray", new TrayRenderer());


    public static void init(){
        Candlelight.LOGGER.debug("Registering Storage Block Renderers!");
    }

    public static ResourceLocation registerStorageType(String string, StorageTypeRenderer renderer){
        return StorageBlockEntityRenderer.registerStorageType(new CandlelightIdentifier(string), renderer);
    }

    @Override
    public void registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.CAKE_STAND.get());
        blocks.add(ObjectRegistry.TABLE_SET.get());
        blocks.add(ObjectRegistry.TRAY.get());
        blocks.add(ObjectRegistry.ACACIA_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.ACACIA_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.OAK_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.OAK_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.DARK_OAK_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.DARK_OAK_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.SPRUCE_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.SPRUCE_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.BIRCH_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.BIRCH_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.MANGROVE_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.MANGROVE_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.JUNGLE_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.JUNGLE_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.WARPED_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.WARPED_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.CRIMSON_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.CRIMSON_WINE_RACK_SMALL.get());
    }

    @Override
    public <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        models.put(ObjectRegistry.COOKING_HAT.get(), new CookingHatModel<>(modelLoader.bakeLayer(CookingHatModel.LAYER_LOCATION)));
    }
}
