package net.satisfy.candlelight.food;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.satisfy.candlelight.item.EffectFood;

import java.util.List;

import static net.satisfy.candlelight.item.EffectFoodHelper.*;

public class EffectFoodItem extends Item implements EffectFood {

    private final int foodStages;

    public EffectFoodItem(Settings settings) {
        this(settings, 0);
    }
    public EffectFoodItem(Settings settings, int foodStages) {
        super(settings);
        this.foodStages = foodStages;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            List<Pair<StatusEffectInstance, Float>> effects = getEffects(stack);
            for (Pair<StatusEffectInstance, Float> effect : effects) {
                if (effect.getFirst() != null && world.random.nextFloat() < effect.getSecond()) {
                    user.addStatusEffect(new StatusEffectInstance(effect.getFirst()));
                }
            }
        }

        ItemStack returnStack =  super.finishUsing(stack, world, user);
        int stage = getStage(stack);
        if (user instanceof PlayerEntity player && !player.isCreative() && stage < foodStages) {
            player.giveItemStack(setStage(new ItemStack(this), stage + 1));
        }
        return returnStack;
    }
}
