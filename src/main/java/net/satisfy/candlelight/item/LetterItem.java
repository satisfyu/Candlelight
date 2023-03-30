package net.satisfy.candlelight.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.satisfy.candlelight.client.gui.handler.LetterGuiHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LetterItem extends Item {
    public LetterItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(!world.isClient())
        {
            NamedScreenHandlerFactory screenHandlerFactory = new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    return Text.literal("");
                }

                @Nullable
                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                    return new LetterGuiHandler(syncId, inv);
                }
            };
            if(screenHandlerFactory != null)
            {
                user.openHandledScreen(screenHandlerFactory);
            }
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(  "item.candlelight.letter.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));

    }
}
