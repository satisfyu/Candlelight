package net.satisfy.candlelight.block;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SeedItemBlock extends AliasedBlockItem {
    public SeedItemBlock(Block block, Settings settings) {
        super(block, settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.candlelight.ingredientitem.tooltip." + this.getTranslationKey()).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

}

