package satisfyu.candlelight.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;

import java.util.List;

public class SmallPaintingBlock extends DecorationBlock {
    public SmallPaintingBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.candlelight.decoration.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));

        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("item.candlelight.thankyou1.press_shift").withStyle(ChatFormatting.WHITE, ChatFormatting.ITALIC));
            tooltip.add(Component.translatable("item.candlelight.thankyou2.press_shift").withStyle(ChatFormatting.WHITE, ChatFormatting.BOLD));
            tooltip.add(Component.translatable("item.candlelight.lilitu.press_shift").withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable("item.candlelight.baumeisterjo.press_shift").withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable("item.candlelight.cristelknight.press_shift").withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable("item.candlelight.satisfyu.press_shift").withStyle(ChatFormatting.GOLD));
        } else {
            tooltip.add(Component.translatable("item.candlelight.faucet.tooltip").withStyle(ChatFormatting.WHITE));
        }
    }
}
