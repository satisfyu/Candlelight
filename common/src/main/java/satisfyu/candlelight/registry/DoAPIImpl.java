package satisfyu.candlelight.registry;

import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfyu.candlelight.client.model.CookingHatModel;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.Map;
import java.util.Set;

@DoApiPlugin
public class DoAPIImpl implements DoApiAPI {

    public static final ResourceLocation CAKE_STAND = new CandlelightIdentifier("cake_stand");

    public static final ResourceLocation TABLE_SET = new CandlelightIdentifier("table_set");

    public static final ResourceLocation TRAY = new CandlelightIdentifier("tray");

    @Override
    public void registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.CAKE_STAND.get());
        blocks.add(ObjectRegistry.TABLE_SET.get());
        blocks.add(ObjectRegistry.TRAY.get());
        //TODO
/*
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

 */
    }

    @Override
    public <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        models.put(ObjectRegistry.COOKING_HAT.get(), new CookingHatModel<>(modelLoader.bakeLayer(CookingHatModel.LAYER_LOCATION)));
    }
}
