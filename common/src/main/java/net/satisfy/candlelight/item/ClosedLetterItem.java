package net.satisfy.candlelight.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import net.satisfy.candlelight.registry.ObjectRegistry;

import java.util.List;

public class ClosedLetterItem extends Item {
    public ClosedLetterItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (customData.contains("letter_title")) {
            String name = customData.copyTag().getString("letter_title");
            if (!name.isBlank()) {
                list.add(Component.literal("for " + name).withStyle(ChatFormatting.RED));
            }
        }
        list.add(Component.translatable("item.candlelight.letter.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        ItemStack output = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITTEN.get());

        CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if(customData.contains("letter_title")) {
            CompoundTag tag = customData.copyTag();
            tag.remove("letter_title");
            output.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }

        user.setItemInHand(hand, output);
        user.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }
}