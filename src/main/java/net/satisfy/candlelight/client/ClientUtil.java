package net.satisfy.candlelight.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.satisfy.candlelight.client.screen.NoteEditScreen;

public class ClientUtil {
    public static void setNoteEditScreen(PlayerEntity user, ItemStack stack, Hand hand){
        MinecraftClient.getInstance().setScreen(new NoteEditScreen(user, stack, hand));
    }

    public static void setBookScreen(ItemStack stack){
        MinecraftClient.getInstance().setScreen(new BookScreen(new BookScreen.WrittenBookContents(stack)));
    }



}
