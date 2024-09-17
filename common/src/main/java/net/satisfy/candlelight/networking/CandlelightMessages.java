package net.satisfy.candlelight.networking;

import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.block.TypeWriterBlock;
import net.satisfy.candlelight.entity.TypeWriterEntity;
import net.satisfy.candlelight.networking.packet.SignNoteC2SPacket;
import net.satisfy.candlelight.networking.packet.SyncTypewriterDataC2SPacket;
import net.satisfy.candlelight.registry.ObjectRegistry;

public class CandlelightMessages {
    public static void registerC2SPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, SignNoteC2SPacket.PACKET_ID, SignNoteC2SPacket.PACKET_CODEC, (payload, context) -> {
            Player player = context.getPlayer();
            CompoundTag tag = payload.tag();
            int slot = payload.slot();
            boolean sign = payload.sigh();

            context.queue(() -> {
                ItemStack itemStack = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITTEN.get());
                itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

                if (sign) {
                    player.getInventory().setItem(slot, itemStack);
                }
            });
        });

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, SyncTypewriterDataC2SPacket.PACKET_ID, SyncTypewriterDataC2SPacket.PACKET_CODEC, (payload, context) -> {
            Player player = context.getPlayer();
            CompoundTag tag = payload.tag();
            BlockPos pos = payload.pos();
            boolean sign = payload.sign();

            ItemStack note = sign ? ObjectRegistry.NOTE_PAPER_WRITTEN.get().getDefaultInstance() : ObjectRegistry.NOTE_PAPER_WRITEABLE.get().getDefaultInstance();

            context.queue(() -> {
                BlockEntity blockEntity = player.level().getBlockEntity(pos);
                if (blockEntity instanceof TypeWriterEntity typeWriterEntity) {
                    note.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                    typeWriterEntity.addPaper(note);
                }
                BlockState blockState = player.level().getBlockState(pos);
                if (sign) {
                    player.level().setBlock(pos, blockState.setValue(TypeWriterBlock.FULL, 2), 2);
                    player.level().sendBlockUpdated(pos, blockState, blockState.setValue(TypeWriterBlock.FULL, 2), Block.UPDATE_CLIENTS);
                }
            });
        });
    }
}
