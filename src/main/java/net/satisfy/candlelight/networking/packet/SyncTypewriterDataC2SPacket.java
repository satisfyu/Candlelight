package net.satisfy.candlelight.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.satisfy.candlelight.block.TypeWriterBlock;
import net.satisfy.candlelight.block.entity.TypeWriterEntity;
import net.satisfy.candlelight.block.entity.WineStationBlockEntity;
import net.satisfy.candlelight.registry.ObjectRegistry;

public class SyncTypewriterDataC2SPacket {
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

    public static void receive(MinecraftServer minecraftServer, ServerPlayerEntity serverPlayerEntity, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        NbtCompound stack = packetByteBuf.readNbt();
        BlockPos pos = packetByteBuf.readBlockPos();
        boolean sign = packetByteBuf.readBoolean();

        BlockEntity blockEntity = serverPlayerEntity.world.getBlockEntity(pos);
        if(blockEntity instanceof TypeWriterEntity typeWriterEntity)
        {
            ItemStack itemStack = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITEABLE);
            itemStack.setNbt(stack);
            typeWriterEntity.addPaper(itemStack);
        }
        if(sign)
        {
            serverPlayerEntity.world.setBlockState(pos, serverPlayerEntity.world.getBlockState(pos).with(TypeWriterBlock.FULL, 2), 2);
        }

    }
}
