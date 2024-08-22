package net.satisfy.candlelight.client.gui;

import dev.architectury.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.satisfy.candlelight.networking.packet.SignNoteC2SPacket;

@Environment(EnvType.CLIENT)
public class NotePaperGui extends NoteGui {
    private final InteractionHand hand;

    public NotePaperGui(Player player, ItemStack itemStack, InteractionHand hand) {
        super(player, itemStack);
        this.hand = hand;
    }

    @Override
    protected void finalizeNote(boolean signNote) {
        if (this.dirty) {
            this.removeEmptyPages();
            this.writeNbtData(signNote);
            int slot = this.hand == InteractionHand.MAIN_HAND ? this.player.getInventory().selected : 40;
            SignNoteC2SPacket packet = new SignNoteC2SPacket(itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag(), slot, signNote);
            NetworkManager.sendToServer(packet);
        }
    }
}
