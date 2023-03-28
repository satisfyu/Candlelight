package net.satisfy.candlelight.food;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.satisfy.candlelight.block.EffectFoodBlock;
import net.satisfy.candlelight.item.EffectFood;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.satisfy.candlelight.block.EffectFoodBlock.BITES;
import static net.satisfy.candlelight.item.EffectFoodHelper.*;

public class EffectFoodBlockItem extends BlockItem implements EffectFood {

    private final int foodStages;

    public EffectFoodBlockItem(Block block, Settings settings) {
        this(block, settings, 0);
    }

    public EffectFoodBlockItem(Block block, Settings settings, int foodStages) {
        super(block, settings);
        this.foodStages = foodStages;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(ItemPlacementContext context) {
        BlockState blockState = this.getBlock().getPlacementState(context);
        return blockState != null && this.canPlace(context, blockState) ? blockState.getBlock() instanceof EffectFoodBlock ? blockState.with(BITES , getStage(context.getStack())) : blockState : null;
    }

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
