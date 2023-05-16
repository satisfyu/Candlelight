package satisfyu.candlelight.food;

import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import satisfyu.candlelight.block.EffectFoodBlock;

import java.util.List;

import static satisfyu.candlelight.block.EffectFoodBlock.BITES;
import static satisfyu.vinery.item.food.EffectFoodHelper.*;

public class EffectFoodBlockItem extends BlockItem {

    private final int foodStages;

    public EffectFoodBlockItem(Block block, Properties settings) {
        this(block, settings, 0);
    }

    public EffectFoodBlockItem(Block block, Properties settings, int foodStages) {
        super(block, settings);
        this.foodStages = foodStages;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockState = this.getBlock().getStateForPlacement(context);
        return blockState != null && this.canPlace(context, blockState) ? blockState.getBlock() instanceof EffectFoodBlock ? blockState.setValue(BITES , getStage(context.getItemInHand())) : blockState : null;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!world.isClientSide) {
            List<Pair<MobEffectInstance, Float>> effects = getEffects(stack);
            for (Pair<MobEffectInstance, Float> effect : effects) {
                if (effect.getFirst() != null && world.random.nextFloat() < effect.getSecond()) {
                    user.addEffect(new MobEffectInstance(effect.getFirst()));
                }
            }
        }

        int slot = -1;
        Inventory playerInventory = null;
        if (user instanceof Player player && !player.isCreative()) {
            playerInventory = player.getInventory();
            slot = playerInventory.findSlotMatchingUnusedItem(stack);
        }
        ItemStack returnStack =  super.finishUsingItem(stack, world, user);
        int stage = getStage(stack);
        if (playerInventory != null && stage < foodStages) {
            ItemStack itemStack = setStage(new ItemStack(this), stage + 1);
            if (playerInventory.getItem(slot).isEmpty()) {
                playerInventory.add(slot, itemStack);
            }
            else {
                slot = playerInventory.getSlotWithRemainingSpace(itemStack);
                playerInventory.add(slot, itemStack);
            }
        }
        return returnStack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        List<Pair<MobEffectInstance, Float>> effects = getEffects(stack);
        if (effects.isEmpty()) {
            tooltip.add(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
        } else {
            for (Pair<MobEffectInstance, Float> statusEffectInstance : effects) {
                MutableComponent mutableText = Component.translatable(statusEffectInstance.getFirst().getDescriptionId());
                MobEffect statusEffect = statusEffectInstance.getFirst().getEffect();

                if (statusEffectInstance.getFirst().getDuration() > 20) {
                    mutableText = Component.translatable(
                            "potion.withDuration",
                            mutableText, MobEffectUtil.formatDuration(statusEffectInstance.getFirst(), statusEffectInstance.getSecond()));
                }

                tooltip.add(mutableText.withStyle(statusEffect.getCategory().getTooltipFormatting()));
            }
        }
    }
}
