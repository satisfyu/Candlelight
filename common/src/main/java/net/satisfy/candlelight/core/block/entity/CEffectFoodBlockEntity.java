package net.satisfy.candlelight.core.block.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.farm_and_charm.core.block.entity.EffectFoodBlockEntity;
import java.util.List;
import java.util.stream.Collectors;

public class CEffectFoodBlockEntity extends EffectFoodBlockEntity {

    public CEffectFoodBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        EntityTypeRegistry.EFFECT_FOOD_BLOCK_ENTITY.get();
    }

    @Override
    public void addEffects(List<Pair<MobEffectInstance, Float>> effects) {
        List<Pair<MobEffectInstance, Float>> filteredEffects = effects.stream()
                .filter(effectPair -> effectPair.getFirst().getEffect() != MobEffects.HUNGER)
                .collect(Collectors.toList());
        super.addEffects(filteredEffects);
    }
}
