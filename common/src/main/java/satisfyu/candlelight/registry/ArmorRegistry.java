package satisfyu.candlelight.registry;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import satisfyu.candlelight.client.model.feature.CookingHatModel;

import java.util.Map;

public class ArmorRegistry {

    public static void registerArmorModelLayers() {
        EntityModelLayerRegistry.register(CookingHatModel.LAYER_LOCATION, CookingHatModel::getTexturedModelData);
    }

    public static  <T extends LivingEntity> void registerArmorModels(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        models.put(ObjectRegistry.COOKING_HAT.get(), new CookingHatModel<>(modelLoader.bakeLayer(CookingHatModel.LAYER_LOCATION)));
    }
}