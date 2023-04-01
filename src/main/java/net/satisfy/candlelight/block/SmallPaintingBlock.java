package net.satisfy.candlelight.block;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;

import java.util.List;

public class SmallPaintingBlock extends DecorationBlock{
    public SmallPaintingBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.candlelight.decoration.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.candlelight.thankyou1.press_shift").formatted(Formatting.WHITE, Formatting.ITALIC));
            tooltip.add(Text.translatable("item.candlelight.thankyou2.press_shift").formatted(Formatting.WHITE, Formatting.BOLD));
            tooltip.add(Text.translatable("item.candlelight.lilitu.press_shift").formatted(Formatting.GOLD));
            tooltip.add(Text.translatable("item.candlelight.baumeisterjo.press_shift").formatted(Formatting.GOLD));
            tooltip.add(Text.translatable("item.candlelight.cristelknight.press_shift").formatted(Formatting.GOLD));
            tooltip.add(Text.translatable("item.candlelight.satisfyu.press_shift").formatted(Formatting.GOLD));
        } else {
            tooltip.add(Text.translatable("item.vinery.faucet.tooltip").formatted(Formatting.WHITE));
        }
    }
}
