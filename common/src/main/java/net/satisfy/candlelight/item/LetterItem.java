package net.satisfy.candlelight.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.satisfy.candlelight.client.gui.handler.LetterGuiHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LetterItem extends Item {
    public LetterItem(Properties settings) {
        super(settings);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (!world.isClientSide()) {
            MenuProvider screenHandlerFactory = new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return Component.literal("");
                }

                @Override
                public @NotNull AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
                    return new LetterGuiHandler(syncId, inv);
                }
            };
            user.openMenu(screenHandlerFactory);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.candlelight.letter.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}
