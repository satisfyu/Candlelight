package net.satisfy.candlelight.block.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.satisfy.candlelight.item.EffectFoodHelper;
import net.satisfy.candlelight.registry.CandlelightEntityTypes;

import java.util.List;

import static net.satisfy.candlelight.item.EffectFoodHelper.fromNbt;

public class EffectFoodBlockEntity extends BlockEntity  {
	public static final String STORED_EFFECTS_KEY = "StoredEffects";
	private List<Pair<StatusEffectInstance, Float>> effects;


	public EffectFoodBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(CandlelightEntityTypes.EFFECT_FOOD_BLOCK_ENTITY, blockPos, blockState);
	}


	public void addEffects(List<Pair<StatusEffectInstance, Float>> effects) {
		this.effects = effects;
	}

	public List<Pair<StatusEffectInstance, Float>> getEffects() {
		return effects;
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.effects = fromNbt(nbt != null ? nbt.getList(STORED_EFFECTS_KEY, 10) : new NbtList());
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		NbtList nbtList = new NbtList();
		for (Pair<StatusEffectInstance, Float> effect : effects) {
			nbtList.add(EffectFoodHelper.createNbt((short) StatusEffect.getRawId(effect.getFirst().getEffectType()), effect));

		}
		nbt.put(STORED_EFFECTS_KEY, nbtList);
	}
}


