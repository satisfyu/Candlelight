package satisfyu.candlelight.networking.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import satisfyu.candlelight.block.TypeWriterBlock;
import satisfyu.candlelight.entity.TypeWriterEntity;
import satisfyu.candlelight.registry.ObjectRegistry;

public class SyncTypewriterDataC2SPacket implements NetworkManager.NetworkReceiver {

    @Override
    public void receive(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        Player player = context.getPlayer();
        CompoundTag nbt = buf.readNbt();
        BlockPos pos = buf.readBlockPos();
        boolean sign = buf.readBoolean();
        ItemStack note = sign ? ObjectRegistry.NOTE_PAPER_WRITTEN.get().getDefaultInstance() : ObjectRegistry.NOTE_PAPER_WRITEABLE.get().getDefaultInstance();
        context.queue(() -> {
            BlockEntity blockEntity = player.level().getBlockEntity(pos);
            if (blockEntity instanceof TypeWriterEntity typeWriterEntity) {
                note.setTag(nbt);
                typeWriterEntity.addPaper(note);
            }
            BlockState blockState = player.level().getBlockState(pos);
            if (sign) {
                player.level().setBlock(pos, blockState.setValue(TypeWriterBlock.FULL, 2), 2);
                player.level().sendBlockUpdated(pos, blockState, blockState.setValue(TypeWriterBlock.FULL, 2), Block.UPDATE_CLIENTS);
            }
        });
    }
}
