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
import satisfyu.candlelight.registry.ObjectRegistry;

import java.util.List;

public class ClosedLetterItem extends Item {
    public ClosedLetterItem(Properties settings) {
        super(settings);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        if (stack.hasTag()) {
            CompoundTag nbtCompound = stack.getTag();
            String string = nbtCompound.getString("letter_title");
            if (!StringUtil.isNullOrEmpty(string)) {
                tooltip.add(Component.literal(string).withStyle(ChatFormatting.GRAY));
            }

        }

    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        ItemStack output = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITTEN.get());
        if(itemStack.hasTag())
        {
            output.setTag(itemStack.getTag().copy());
            output.getTag().remove("letter_title");
        }
        user.setItemInHand(hand, output);
        user.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }
}
