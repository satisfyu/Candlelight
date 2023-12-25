package satisfy.candlelight.networking;


import dev.architectury.networking.NetworkManager;
import net.minecraft.resources.ResourceLocation;
//import satisfyu.candlelight.networking.packet.SyncTypewriterDataC2SPacket;
import satisfy.candlelight.networking.packet.SignNoteC2SPacket;
import satisfy.candlelight.networking.packet.SyncTypewriterDataC2SPacket;
import satisfy.candlelight.util.CandlelightIdentifier;

public class CandlelightMessages {
    public static final ResourceLocation TYPEWRITER_SYNC = new CandlelightIdentifier("typewriter_sync");
    public static final ResourceLocation SIGN_NOTE = new CandlelightIdentifier("sign_note");

    public static void registerC2SPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, TYPEWRITER_SYNC, new SyncTypewriterDataC2SPacket());
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, SIGN_NOTE, new SignNoteC2SPacket());
    }
}
