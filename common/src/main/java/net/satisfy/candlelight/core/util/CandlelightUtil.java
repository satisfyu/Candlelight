package net.satisfy.candlelight.core.util;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.client.gui.NotePaperGui;
import net.satisfy.candlelight.client.gui.SignedPaperGui;
import net.satisfy.candlelight.client.gui.TypeWriterGui;
import net.satisfy.candlelight.core.block.entity.TypeWriterEntity;

public class CandlelightUtil {
    public static void setNotePaperScreen(Player user, ItemStack stack, InteractionHand hand) {
        Minecraft.getInstance().setScreen(new NotePaperGui(user, stack, hand));
    }


    public static void setTypeWriterScreen(Player user, TypeWriterEntity typeWriterEntity) {
        if (user instanceof LocalPlayer)
            Minecraft.getInstance().setScreen(new TypeWriterGui(user, typeWriterEntity));
    }

    public static void setSignedPaperScreen(ItemStack stack) {
        Minecraft.getInstance().setScreen(new SignedPaperGui(new SignedPaperGui.WrittenPaperContents(stack)));
    }

    public static void registerColorArmor(Item item, int defaultColor) {
        ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> 0 < tintIndex ? 0x00FFFFFF : getColor(stack, defaultColor), item);
    }

    static int getColor(ItemStack itemStack, int defaultColor) {
        CompoundTag displayTag = itemStack.getTagElement("display");
        if (null != displayTag && displayTag.contains("color", Tag.TAG_ANY_NUMERIC))
            return displayTag.getInt("color");
        return defaultColor;
    }

}
