package net.satisfy.candlelight.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.satisfy.candlelight.client.screen.NoteEditScreen;
import net.satisfy.candlelight.client.screen.NotePaperScreen;
import net.satisfy.candlelight.client.screen.SignedPaperScreen;

public class ClientUtil {
    public static void setNoteEditScreen(PlayerEntity user, ItemStack stack, Hand hand){
        MinecraftClient.getInstance().setScreen(new NoteEditScreen(user, stack, hand));
    }

    public static void setNotePaperScreen(PlayerEntity user, ItemStack stack, Hand hand){
        MinecraftClient.getInstance().setScreen(new NotePaperScreen(user, stack, hand));
    }

    public static void setSignedPaperScreen(ItemStack stack){
        MinecraftClient.getInstance().setScreen(new SignedPaperScreen(new SignedPaperScreen.WrittenBookContents(stack)));
    }



}
