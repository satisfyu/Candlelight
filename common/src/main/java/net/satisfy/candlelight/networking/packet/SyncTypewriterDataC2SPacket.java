package net.satisfy.candlelight.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import org.jetbrains.annotations.NotNull;

public record SyncTypewriterDataC2SPacket(CompoundTag tag, BlockPos pos, boolean sign) implements CustomPacketPayload {
    public static final ResourceLocation PACKET_RESOURCE_LOCATION = CandlelightIdentifier.of("typewriter_sync");
    public static final CustomPacketPayload.Type<SyncTypewriterDataC2SPacket> PACKET_ID = new CustomPacketPayload.Type<>(PACKET_RESOURCE_LOCATION);

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTypewriterDataC2SPacket> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG, SyncTypewriterDataC2SPacket::tag,
            BlockPos.STREAM_CODEC, SyncTypewriterDataC2SPacket::pos,
            ByteBufCodecs.BOOL, SyncTypewriterDataC2SPacket::sign,
            SyncTypewriterDataC2SPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }
}
