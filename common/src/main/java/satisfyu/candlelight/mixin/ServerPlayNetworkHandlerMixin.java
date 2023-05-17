package satisfyu.candlelight.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.candlelight.registry.ObjectRegistry;

import java.util.List;
import java.util.function.UnaryOperator;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow
    private ServerPlayer player;

    @Shadow
    private void updateBookPages(List<FilteredText> messages, UnaryOperator<String> postProcessor, ItemStack book) {

    }

    @Inject(method = "updateBookContents", at = @At("HEAD"))
    private void updateBookContent(List<FilteredText> pages, int slotId, CallbackInfo ci) {
        ItemStack itemStack = this.player.getInventory().getItem(slotId);
        if (itemStack.is(ObjectRegistry.NOTE_PAPER_WRITEABLE.get())) {
            this.updateBookPages(pages, UnaryOperator.identity(), itemStack);

        }
    }

    @Inject(method = "signBook", at = @At("HEAD"), cancellable = true)
    private void addBook(FilteredText title, List<FilteredText> pages, int slotId, CallbackInfo ci) {
        ItemStack itemStack = this.player.getInventory().getItem(slotId);
        if (itemStack.is(ObjectRegistry.NOTE_PAPER_WRITEABLE.get())) {
            ItemStack itemStack2 = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITTEN.get());
            CompoundTag nbtCompound = itemStack.getTag();
            if (nbtCompound != null) {
                itemStack2.setTag(nbtCompound.copy());
            }

            itemStack2.addTagElement("author", StringTag.valueOf(this.player.getName().getString()));
            if (this.player.isTextFilteringEnabled()) {
                itemStack2.addTagElement("title", StringTag.valueOf(title.filteredOrEmpty()));
            } else {
                itemStack2.addTagElement("filtered_title", StringTag.valueOf(title.filteredOrEmpty()));
                itemStack2.addTagElement("title", StringTag.valueOf(title.raw()));
            }

            this.updateBookPages(pages, (text) -> {
                return Component.Serializer.toJson(Component.literal(text));
            }, itemStack2);
            this.player.getInventory().setItem(slotId, itemStack2);
            ci.cancel();
        }
    }
}