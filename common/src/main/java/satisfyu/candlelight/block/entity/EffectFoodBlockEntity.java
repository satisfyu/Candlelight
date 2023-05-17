package satisfyu.candlelight.block.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.compress.utils.Lists;
import satisfyu.candlelight.registry.BlockEntityRegistry;
import satisfyu.vinery.item.food.EffectFoodHelper;

import java.util.List;

import static satisfyu.vinery.item.food.EffectFoodHelper.fromNbt;

public class EffectFoodBlockEntity extends BlockEntity  {
	public static final String STORED_EFFECTS_KEY = "StoredEffects";
	private List<Pair<MobEffectInstance, Float>> effects;


	public EffectFoodBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.EFFECT_FOOD_BLOCK_ENTITY.get(), blockPos, blockState);
	}


	public void addEffects(List<Pair<MobEffectInstance, Float>> effects) {
		this.effects = effects;
	}

	public List<Pair<MobEffectInstance, Float>> getEffects() {
		return effects != null ? effects : Lists.newArrayList();
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		this.effects = fromNbt(nbt != null ? nbt.getList(STORED_EFFECTS_KEY, 10) : new ListTag());
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		if (effects == null) {
			return;
		}
		ListTag nbtList = new ListTag();
		for (Pair<MobEffectInstance, Float> effect : effects) {
			nbtList.add(EffectFoodHelper.createNbt((short) MobEffect.getId(effect.getFirst().getEffect()), effect));

		}
		nbt.put(STORED_EFFECTS_KEY, nbtList);
	}
}


