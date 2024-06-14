package net.satisfy.candlelight.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.satisfy.candlelight.registry.ObjectRegistry;

import java.util.List;

public class ClosedLetterItem extends Item {
    public ClosedLetterItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        CompoundTag nbtCompound = stack.getTag();
        if (nbtCompound != null && nbtCompound.contains("letter_title")) {
            String name = nbtCompound.getString("letter_title");
            if (!name.isBlank()) {
                tooltip.add(Component.literal("for " + name).withStyle(ChatFormatting.RED));
            }
        }
        tooltip.add(Component.translatable("item.candlelight.letter.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        ItemStack output = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITTEN.get());
        if (itemStack.hasTag()) {
            assert itemStack.getTag() != null;
            output.setTag(itemStack.getTag().copy());
            assert output.getTag() != null;
            output.getTag().remove("letter_title");
        }
        user.setItemInHand(hand, output);
        user.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }
}