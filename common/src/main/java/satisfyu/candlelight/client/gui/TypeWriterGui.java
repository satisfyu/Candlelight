package satisfyu.candlelight.client.gui;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
/*
@Environment(EnvType.CLIENT)
public class TypeWriterGui extends NoteGui {
    private final TypeWriterEntity typeWriterEntity;

    public TypeWriterGui(Player player, TypeWriterEntity typeWriterEntity) {
        super(player, typeWriterEntity.getPaper());
        this.typeWriterEntity = typeWriterEntity;
    }

    @Override
    protected void finalizeNote(boolean signNote) {
        if (this.dirty) {
            this.removeEmptyPages();
            this.writeNbtData(signNote);

            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeNbt(this.itemStack.getTag());
            buf.writeBlockPos(typeWriterEntity.getBlockPos());
            buf.writeBoolean(signNote);
            NetworkManager.sendToServer(CandlelightMessages.TYPEWRITER_SYNC, buf);
        }
    }
}
*/
