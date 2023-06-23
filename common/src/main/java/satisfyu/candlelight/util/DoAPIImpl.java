package satisfyu.candlelight.util;

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
import satisfyu.candlelight.client.model.CookingHatModel;
import satisfyu.candlelight.registry.ArmorRegistry;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.registry.StorageTypesRegistry;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.Map;
import java.util.Set;

@DoApiPlugin
public class DoAPIImpl implements DoApiAPI {

    @Override
    public void registerBlocks(Set<Block> blocks) {
        StorageTypesRegistry.registerBlocks(blocks);
    }

    @Override
    public <T extends LivingEntity> void registerHat(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        ArmorRegistry.registerHatModels(models, modelLoader);
    }

    @Override
    public <T extends LivingEntity> void registerArmor(Map<FullCustomArmor, Pair<HumanoidModel<T>, HumanoidModel<T>>> models, EntityModelSet modelLoader) {
    }

}
