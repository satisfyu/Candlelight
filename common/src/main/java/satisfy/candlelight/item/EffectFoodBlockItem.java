package satisfy.candlelight.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.satisfy.farm_and_charm.block.EffectFoodBlock;
import net.satisfy.farm_and_charm.item.food.EffectFood;
import net.satisfy.farm_and_charm.item.food.EffectFoodHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EffectFoodBlockItem extends BlockItem implements EffectFood {

    private final int foodStages;

    public EffectFoodBlockItem(Block block, Properties settings, int foodStages) {
        super(block, settings);
        this.foodStages = foodStages;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockState = this.getBlock().getStateForPlacement(context);
        return blockState != null && this.canPlace(context, blockState) ? blockState.getBlock() instanceof EffectFoodBlock ? blockState.setValue(EffectFoodBlock.BITES, EffectFoodHelper.getStage(context.getItemInHand())) : blockState : null;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!world.isClientSide) {
            List<Pair<MobEffectInstance, Float>> effects = EffectFoodHelper.getEffects(stack);
            for (Pair<MobEffectInstance, Float> effect : effects) {
                if (effect.getFirst() != null && world.random.nextFloat() < effect.getSecond()) {
                    user.addEffect(new MobEffectInstance(effect.getFirst()));
                }
            }
        }

        if (user instanceof Player player && !player.isCreative()) {
            Inventory playerInventory = player.getInventory();
            int slot = playerInventory.findSlotMatchingUnusedItem(stack);
            ItemStack returnStack = super.finishUsingItem(stack, world, user);
            int stage = EffectFoodHelper.getStage(stack);

            if (stage < this.foodStages) {
                ItemStack itemStack = EffectFoodHelper.setStage(new ItemStack(this), stage + 1);
                if (slot != -1 && playerInventory.getItem(slot).isEmpty()) {
                    playerInventory.setItem(slot, itemStack);
                } else {
                    slot = playerInventory.getSlotWithRemainingSpace(itemStack);
                    if (slot != -1) {
                        playerInventory.setItem(slot, itemStack);
                    }
                }
            }
            return returnStack;
        }
        return super.finishUsingItem(stack, world, user);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.candlelight.canbeplaced").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        EffectFoodHelper.getTooltip(stack, tooltip);
    }
}