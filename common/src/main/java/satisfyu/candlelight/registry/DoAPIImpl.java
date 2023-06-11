package satisfyu.candlelight.registry;

import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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
    public <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        ArmorRegistry.registerArmorModels(models, modelLoader);
    }
}
