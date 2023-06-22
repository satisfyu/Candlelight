package satisfyu.candlelight.registry;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import de.cristelknight.doapi.client.render.feature.FullCustomArmor;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfyu.candlelight.client.model.feature.CookingHatModel;
import satisfyu.candlelight.item.CookingHatItem;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.Map;
import java.util.Set;

@DoApiPlugin
public class DoAPIImpl implements DoApiAPI {
    public static final ResourceLocation TABLE_SET = new CandlelightIdentifier("table_set");

    @Override
    public void registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.TABLE_SET.get());

        StorageTypesRegistry.registerBlocks(blocks);
    }

    @Override
    public <T extends LivingEntity> void registerHat(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        models.put(ObjectRegistry.COOKING_HAT.get(), new CookingHatModel<>(modelLoader.bakeLayer(CookingHatModel.LAYER_LOCATION)));
    }

    @Override
    public <T extends LivingEntity> void registerArmor(Map<FullCustomArmor, Pair<HumanoidModel<T>, HumanoidModel<T>>> models, EntityModelSet modelLoader) {
    }
    public static void registerArmorModelLayers(){
        EntityModelLayerRegistry.register(CookingHatModel.LAYER_LOCATION, CookingHatModel::getTexturedModelData);
    }


}
