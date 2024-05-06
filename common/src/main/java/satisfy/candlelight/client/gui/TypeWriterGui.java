package satisfy.candlelight.client.gui;

import de.cristelknight.doapi.common.registry.DoApiSoundEventRegistry;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;
import satisfy.candlelight.block.entity.TypeWriterEntity;
import satisfy.candlelight.networking.CandlelightMessages;

@Environment(EnvType.CLIENT)
public class TypeWriterGui extends NoteGui {
    private final TypeWriterEntity typeWriterEntity;

    public TypeWriterGui(Player player, TypeWriterEntity typeWriterEntity) {
        super(player, typeWriterEntity.getPaper());
        this.typeWriterEntity = typeWriterEntity;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        boolean result = super.charTyped(chr, modifiers);
        if (result) {
            playTypingSound();
        }
        return result;
    }

    @Override
    protected void finalizeNote(boolean signNote) {
        if (this.dirty) {
            this.removeEmptyPages();
            this.writeNbtData(signNote);
        }
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeNbt(this.itemStack.getTag());
        buf.writeBlockPos(typeWriterEntity.getBlockPos());
        buf.writeBoolean(signNote);
        NetworkManager.sendToServer(CandlelightMessages.TYPEWRITER_SYNC, buf);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean result = super.keyPressed(keyCode, scanCode, modifiers);
        if (result && keyCode == GLFW.GLFW_KEY_ENTER) {
            playEnterSound();
        } else if (result) {
            playTypingSound();
        }
        return result;
    }

    private void playEnterSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_BELL, 1.0F));
    }

    private void playTypingSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(DoApiSoundEventRegistry.TYPEWRITER.get(), 2.0F));
    }
}
