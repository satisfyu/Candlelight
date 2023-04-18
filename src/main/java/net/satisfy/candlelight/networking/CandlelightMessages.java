package net.satisfy.candlelight.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Identifier;
import net.satisfy.candlelight.networking.packet.ItemStackSyncS2CPacket;
import net.satisfy.candlelight.networking.packet.SyncTypewriterDataC2SPacket;
import net.satisfy.candlelight.util.CandlelightIdentifier;

public class CandlelightMessages {
    public static final Identifier ITEM_SYNC = new CandlelightIdentifier("item_sync");
    public static final Identifier TYPEWRITER_SYNC = new CandlelightIdentifier("typewriter_sync");

    public static void registerC2SPackets() {
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(TYPEWRITER_SYNC, SyncTypewriterDataC2SPacket::receive);
    }
}
