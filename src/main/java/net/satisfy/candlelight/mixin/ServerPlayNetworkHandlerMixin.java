package net.satisfy.candlelight.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.filter.FilteredMessage;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.satisfy.candlelight.registry.ObjectRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.UnaryOperator;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow
    private ServerPlayerEntity player;

    @Shadow
    private void setTextToBook(List<FilteredMessage> messages, UnaryOperator<String> postProcessor, ItemStack book) {

    }

    @Inject(method = "updateBookContent", at = @At("HEAD"))
    private void updateBookContent(List<FilteredMessage> pages, int slotId, CallbackInfo ci) {
        ItemStack itemStack = this.player.getInventory().getStack(slotId);
        if (itemStack.isOf(ObjectRegistry.NOTE_PAPER_WRITEABLE)) {
            this.setTextToBook(pages, UnaryOperator.identity(), itemStack);

        }
    }

    @Inject(method = "addBook", at = @At("HEAD"), cancellable = true)
    private void addBook(FilteredMessage title, List<FilteredMessage> pages, int slotId, CallbackInfo ci) {
        ItemStack itemStack = this.player.getInventory().getStack(slotId);
        if (itemStack.isOf(ObjectRegistry.NOTE_PAPER_WRITEABLE)) {
            ItemStack itemStack2 = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITTEN);
            NbtCompound nbtCompound = itemStack.getNbt();
            if (nbtCompound != null) {
                itemStack2.setNbt(nbtCompound.copy());
            }

            itemStack2.setSubNbt("author", NbtString.of(this.player.getName().getString()));
            if (this.player.shouldFilterText()) {
                itemStack2.setSubNbt("title", NbtString.of(title.getString()));
            } else {
                itemStack2.setSubNbt("filtered_title", NbtString.of(title.getString()));
                itemStack2.setSubNbt("title", NbtString.of(title.raw()));
            }

            this.setTextToBook(pages, (text) -> {
                return Text.Serializer.toJson(Text.literal(text));
            }, itemStack2);
            this.player.getInventory().setStack(slotId, itemStack2);
            ci.cancel();
        }
    }
}