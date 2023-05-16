package satisfyu.candlelight.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import satisfyu.candlelight.client.ClientUtil;

import java.util.List;

public class WrittenPaperItem extends Item {
    public WrittenPaperItem(Properties settings) {
        super(settings);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if(world.isClientSide())
        {
            ClientUtil.setSignedPaperScreen(itemStack);
        }
        else
        {
            user.containerMenu.broadcastChanges();
        }
        user.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }

    public Component getName(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTag();
        if (nbtCompound != null) {
            String string = nbtCompound.getString("title");
            if (!StringUtil.isNullOrEmpty(string)) {
                return Component.literal(string);
            }
        }

        return super.getName(stack);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        if (stack.hasTag()) {
            CompoundTag nbtCompound = stack.getTag();
            String string = nbtCompound.getString("author");
            if (!StringUtil.isNullOrEmpty(string)) {
                tooltip.add(Component.translatable("book.byAuthor", new Object[]{string}).withStyle(ChatFormatting.GRAY));
            }

           // tooltip.add(Text.translatable("book.generation." + nbtCompound.getInt("generation")).formatted(Formatting.GRAY));
        }

    }
}
