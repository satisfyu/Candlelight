package satisfyu.candlelight.client.gui.handler;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.registry.ScreenHandlerTypeRegistry;

import java.util.List;
import java.util.function.UnaryOperator;

public class LetterGuiHandler extends AbstractContainerMenu {
    private final Container inventory;
    public static String name = "";

    public LetterGuiHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(3));
    }

    public LetterGuiHandler(int syncId, Inventory playerInventory, Container inventory) {
        super(ScreenHandlerTypeRegistry.LETTER_SCREEN_HANDLER.get(), syncId);
        checkContainerSize(inventory, 3);
        this.inventory = inventory;
        inventory.startOpen(playerInventory.player);

        int m;
        int l;

        this.addSlot(new Slot(inventory, 0, 80 + 18 - 100 + 30 + 5, 15 + 0 * 18 - 2));
        this.addSlot(new Slot(inventory, 1, 80 + 18 - 100 + 30 + 5, 15 + 1 * 18 + 25 - 8));

        this.addSlot(new OutputSlot(inventory, 2, 64 + 18 + 50 - 30 + 8, 18 + 2 * 18 - 10 + 4, this));
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        ItemStack inputStack = this.inventory.getItem(0);
        ItemStack noteStack = this.inventory.getItem(1);

        if (inputStack.getItem() == ObjectRegistry.LETTER_OPEN.get() && noteStack.getItem() == ObjectRegistry.NOTE_PAPER_WRITTEN.get()) {
            ItemStack outputStack = new ItemStack(ObjectRegistry.LOVE_LETTER_CLOSED.get());
            CompoundTag nbtCompound = noteStack.getTag();

            if (nbtCompound != null) {
                outputStack.setTag(nbtCompound.copy());
            }

            outputStack.addTagElement("letter_title", StringTag.valueOf(name));
            this.inventory.setItem(2, outputStack);
        } else if (inputStack.getItem() == ObjectRegistry.LOVE_LETTER_OPEN.get() && noteStack.getItem() == ObjectRegistry.NOTE_PAPER_WRITTEN.get()) {
            ItemStack outputStack = new ItemStack(ObjectRegistry.LOVE_LETTER_CLOSED.get());
            CompoundTag nbtCompound = noteStack.getTag();

            if (nbtCompound != null) {
                outputStack.setTag(nbtCompound.copy());
            }

            outputStack.addTagElement("letter_title", StringTag.valueOf(name));
            this.inventory.setItem(2, outputStack);
        } else {
            this.inventory.setItem(2, ItemStack.EMPTY);
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        if (player instanceof ServerPlayer) {
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack itemStack = inventory.getItem(i);
                if (!itemStack.isEmpty()) {
                    if (player.isAlive() && !((ServerPlayer) player).hasDisconnected()) {
                        player.getInventory().placeItemBackInInventory(itemStack);
                    } else {
                        player.drop(itemStack, false);
                    }
                }
            }
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (invSlot < this.inventory.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.inventory.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.inventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return newStack;
    }

    public static class OutputSlot extends Slot {
        LetterGuiHandler container;

        public OutputSlot(Container itemHandler, int index, int xPosition, int yPosition, LetterGuiHandler container) {
            super(itemHandler, index, xPosition, yPosition);
            this.container = container;
        }

        @Override
        public void onTake(Player player, ItemStack stack) {
            this.container.inventory.setItem(0, ItemStack.EMPTY);
            this.container.inventory.setItem(1, ItemStack.EMPTY);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }
    }
}
