package satisfyu.candlelight.networking.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import satisfyu.candlelight.block.TypeWriterBlock;
import satisfyu.candlelight.block.entity.TypeWriterEntity;
import satisfyu.candlelight.registry.ObjectRegistry;

public class SyncTypewriterDataC2SPacket implements NetworkManager.NetworkReceiver {
/*    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        int size = buf.readInt();
        DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.EMPTY);
        for(int i = 0; i < size; i++) {
            list.set(i, buf.readItemStack());
        }
        BlockPos position = buf.readBlockPos();

        if(client.world.getBlockEntity(position) instanceof WineStationBlockEntity blockEntity) {
            blockEntity.setInventory(list);
        }
    }*/

    @Override
    public void receive(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        Player player = context.getPlayer();
        CompoundTag stack = buf.readNbt();
        BlockPos pos = buf.readBlockPos();
        boolean sign = buf.readBoolean();

        BlockEntity blockEntity = player.level.getBlockEntity(pos);
        if(blockEntity instanceof TypeWriterEntity typeWriterEntity)
        {
            ItemStack itemStack = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITEABLE.get());
            itemStack.setTag(stack);
            typeWriterEntity.addPaper(itemStack);
        }
        if(sign)
        {
            player.level.setBlock(pos, player.level.getBlockState(pos).setValue(TypeWriterBlock.FULL, 2), 2);
            player.level.sendBlockUpdated(pos, player.level.getBlockState(pos),
                    player.level.getBlockState(pos).setValue(TypeWriterBlock.FULL, 2), Block.UPDATE_CLIENTS);
        }
    }
}
