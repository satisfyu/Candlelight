package net.satisfy.candlelight.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import net.satisfy.candlelight.util.CandlelightUtil;

import java.util.List;

public class WrittenPaperItem extends Item {
    public WrittenPaperItem(Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (world.isClientSide()) {
            CandlelightUtil.setSignedPaperScreen(itemStack);
        } else {
            user.containerMenu.broadcastChanges();
        }
        user.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

        if(customData.contains("title")) {
            String title = customData.copyTag().getString("title");
            if(!StringUtil.isNullOrEmpty(title)) {
                return Component.literal(title);
            }
        }

        return super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

        if(customData.contains("author")) {
            String author = customData.copyTag().getString("author");
            if(!StringUtil.isNullOrEmpty(author)) {
                list.add(Component.translatable("book.byAuthor", author).withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
