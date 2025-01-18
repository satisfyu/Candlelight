package net.satisfy.candlelight.core.networking.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.core.registry.ObjectRegistry;

public class SignNoteC2SPacket implements NetworkManager.NetworkReceiver {
    @Override
    public void receive(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        Player player = context.getPlayer();
        CompoundTag stack = buf.readNbt();
        int slot = buf.readInt();
        boolean sign = buf.readBoolean();

        ItemStack itemStack = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITTEN.get());
        itemStack.setTag(stack);

        if (sign) {
            player.getInventory().setItem(slot, itemStack);
        }
    }
}

