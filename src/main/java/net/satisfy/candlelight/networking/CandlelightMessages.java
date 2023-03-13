package net.satisfy.candlelight.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.satisfy.candlelight.networking.packet.ItemStackSyncS2CPacket;
import net.satisfy.candlelight.util.CandlelightIdentifier;

public class CandlelightMessages {
    public static final Identifier ITEM_SYNC = new CandlelightIdentifier("item_sync");

    public static void registerC2SPackets() {
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }
}
