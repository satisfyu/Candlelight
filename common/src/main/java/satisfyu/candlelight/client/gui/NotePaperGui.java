
package satisfyu.candlelight.client.gui;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import satisfyu.candlelight.networking.CandlelightMessages;

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
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeNbt(itemStack.getTag());
            buf.writeInt(slot);
            buf.writeBoolean(signNote);
            NetworkManager.sendToServer(CandlelightMessages.SIGN_NOTE, buf);
        }
    }
}
