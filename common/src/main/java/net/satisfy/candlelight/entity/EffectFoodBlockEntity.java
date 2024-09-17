package net.satisfy.candlelight.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.registry.BlockEntityRegistry;
import net.satisfy.farm_and_charm.item.food.EffectFoodHelper;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class EffectFoodBlockEntity extends BlockEntity {
    public static final String STORED_EFFECTS_KEY = "StoredEffects";
    private List<Pair<MobEffectInstance, Float>> effects;

    public EffectFoodBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.EFFECT_FOOD_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    public void addEffects(List<Pair<MobEffectInstance, Float>> effects) {
        List<Pair<MobEffectInstance, Float>> filteredEffects = effects.stream()
                .filter(effectPair -> effectPair.getFirst().getEffect() != MobEffects.HUNGER)
                .collect(Collectors.toList());

        this.effects = filteredEffects;
    }

    public List<Pair<MobEffectInstance, Float>> getEffects() {
        return effects != null ? effects : Lists.newArrayList();
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.effects = EffectFoodHelper.fromNbt(compoundTag != null ? compoundTag.getList(STORED_EFFECTS_KEY, 10) : new ListTag());
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        if (effects == null) {
            return;
        }

        ListTag nbtList = new ListTag();
        for (Pair<MobEffectInstance, Float> effect : effects) {
            nbtList.add(EffectFoodHelper.createNbt((short) BuiltInRegistries.MOB_EFFECT.asHolderIdMap().getId(effect.getFirst().getEffect()), effect));
        }
        compoundTag.put(STORED_EFFECTS_KEY, nbtList);
    }
}


