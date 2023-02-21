package net.satisfy.candlelight.client.gui.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.filter.FilteredMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.satisfy.candlelight.registry.ObjectRegistry;
import net.satisfy.candlelight.registry.ScreenHandlerTypes;

import java.util.List;
import java.util.function.UnaryOperator;

public class LetterGuiHandler extends ScreenHandler {
    private final Inventory inventory;
    public static String name = "";

    //This constructor gets called on the client when the server wants it to open the screenHandler,
    //The client will call the other constructor with an empty Inventory and the screenHandler will automatically
    //sync this empty inventory with the inventory on the server.
    public LetterGuiHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3));
    }

    //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    public LetterGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ScreenHandlerTypes.LETTER_SCREEN_HANDLER, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);

        //This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        //This will not render the background of the slots however, this is the Screens job
        int m;
        int l;
        //Our inventory

        this.addSlot(new Slot(inventory, 0, 62 + 18 - 100 + 30 + 5, 17 + 0 * 18 - 2));
        this.addSlot(new Slot(inventory, 1, 62 + 18 - 100 + 30 + 5, 17 + 1 * 18 + 25 - 8));

        this.addSlot(new OutputSlot(inventory, 2, 62 + 18 + 50 - 30 + 8, 17 + 2 * 18 - 10 + 4, this));
        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }

    }

    @Override
    public void sendContentUpdates() {
        super.sendContentUpdates();
        //System.out.println(name);
        if((this.inventory.getStack(0).getItem() == ObjectRegistry.LETTER_OPEN && this.inventory.getStack(1).getItem() == ObjectRegistry.NOTE_PAPER_WRITTEN))
        {
            ItemStack stack = new ItemStack(ObjectRegistry.LETTER_CLOSED);

            NbtCompound nbtCompound = this.inventory.getStack(1).getNbt();
            if (nbtCompound != null) {
                stack.setNbt(nbtCompound.copy());
            }

            stack.setSubNbt("letter_title", NbtString.of(name));

            this.inventory.setStack(2, stack);
        }
        else if((this.inventory.getStack(1).getItem() == ObjectRegistry.LETTER_OPEN && this.inventory.getStack(0).getItem() == ObjectRegistry.NOTE_PAPER_WRITTEN))
        {
            ItemStack stack = new ItemStack(ObjectRegistry.LETTER_CLOSED);

            NbtCompound nbtCompound = this.inventory.getStack(0).getNbt();
            if (nbtCompound != null) {
                stack.setNbt(nbtCompound.copy());
            }

            stack.setSubNbt("letter_title", NbtString.of(name));

            this.inventory.setStack(2, stack);
        }
        else
        {
            this.inventory.setStack(2, ItemStack.EMPTY);
        }
        //this.onContentChanged(this.inventory);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        System.out.println("es");
        super.onContentChanged(inventory);

    }

    private void setTextToBook(List<FilteredMessage> messages, UnaryOperator<String> postProcessor, ItemStack book) {
        NbtList nbtList = new NbtList();

        NbtCompound nbtCompound = new NbtCompound();
        int i = 0;

        for(int j = messages.size(); i < j; ++i) {
            FilteredMessage filteredMessage = (FilteredMessage)messages.get(i);
            String string = filteredMessage.raw();
            nbtList.add(NbtString.of((String)postProcessor.apply(string)));
            if (filteredMessage.isFiltered()) {
                nbtCompound.putString(String.valueOf(i), (String)postProcessor.apply(filteredMessage.getString()));
            }
        }

        if (!nbtCompound.isEmpty()) {
            book.setSubNbt("filtered_pages", nbtCompound);
        }


        book.setSubNbt("pages", nbtList);
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        if (player instanceof ServerPlayerEntity) {
            for(int i = 0; i < inventory.size(); i++)
            {
                ItemStack itemStack = inventory.getStack(i);
                if (!itemStack.isEmpty()) {
                    if (player.isAlive() && !((ServerPlayerEntity)player).isDisconnected()) {
                        player.getInventory().offerOrDrop(itemStack);
                    } else {
                        player.dropItem(itemStack, false);
                    }

                    // this.setCursorStack(ItemStack.EMPTY);
                }
            }
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    public static class OutputSlot extends Slot{
        LetterGuiHandler container;

        public OutputSlot(Inventory itemHandler, int index, int xPosition, int yPosition, LetterGuiHandler container) {
            super(itemHandler, index, xPosition, yPosition);
            this.container = container;
        }
        @Override
        public void onTakeItem(PlayerEntity p_190901_1_, ItemStack p_190901_2_) {
            this.container.inventory.setStack(0, ItemStack.EMPTY);
            this.container.inventory.setStack(1, ItemStack.EMPTY);
        }
        @Override
        public boolean canInsert(ItemStack p_75214_1_) {
            return false;
        }

    }



}