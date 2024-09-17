package net.satisfy.candlelight.networking.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import org.jetbrains.annotations.NotNull;

public record SignNoteC2SPacket(CompoundTag tag, int slot, boolean sigh) implements CustomPacketPayload {
    public static final ResourceLocation PACKET_RESOURCE_LOCATION = CandlelightIdentifier.of("sign_note");
    public static final CustomPacketPayload.Type<SignNoteC2SPacket> PACKET_ID = new CustomPacketPayload.Type<>(PACKET_RESOURCE_LOCATION);

    public static final StreamCodec<RegistryFriendlyByteBuf, SignNoteC2SPacket> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG, SignNoteC2SPacket::tag,
            ByteBufCodecs.INT, SignNoteC2SPacket::slot,
            ByteBufCodecs.BOOL, SignNoteC2SPacket::sigh,
            SignNoteC2SPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }
}

