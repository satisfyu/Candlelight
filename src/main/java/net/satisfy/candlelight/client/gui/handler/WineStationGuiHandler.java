package net.satisfy.candlelight.client.gui.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import net.satisfy.candlelight.registry.ScreenHandlerTypes;
import net.satisfy.candlelight.util.CandlelightTags;
import satisfyu.vinery.client.gui.handler.slot.ExtendedSlot;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.registry.VineryScreenHandlerTypes;
import satisfyu.vinery.util.VineryTags;

public class WineStationGuiHandler extends ScreenHandler {

    public final World world;
    public final Inventory inventory;
    public final PropertyDelegate propertyDelegate;
    private final int inputSlots;

    public WineStationGuiHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5), new ArrayPropertyDelegate(1));
    }


    public WineStationGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ScreenHandlerTypes.WINE_STATION_SCREEN_HANDLER, syncId);
        this.world = playerInventory.player.world;
        this.inventory = inventory;
        this.inputSlots = 3;
        this.propertyDelegate = propertyDelegate;
        addProperties(this.propertyDelegate);
        buildBlockEntityContainer(inventory);
        buildPlayerContainer(playerInventory);
    }

    private void buildBlockEntityContainer(Inventory inventory) {
        this.addSlot(new ExtendedSlot(inventory, 0,44, 20, (itemStack -> itemStack.isIn(CandlelightTags.WINE))));
        this.addSlot(new ExtendedSlot(inventory, 1,35, 39, (itemStack -> itemStack.isIn(CandlelightTags.WINE))));
        this.addSlot(new ExtendedSlot(inventory, 2,54, 39, (itemStack -> itemStack.isIn(CandlelightTags.WINE))));

        this.addSlot(new Slot(inventory, 3, 106, 18) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 4, 124, 36) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
    }

    private void buildPlayerContainer(PlayerInventory playerInventory) {
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        final int entityInputStart = 0;
        int entityOutputSlot = this.inputSlots;
        final int inventoryStart = entityOutputSlot + 1;
        final int hotbarStart = inventoryStart + 9 * 3;
        final int hotbarEnd = hotbarStart + 9;


        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (invSlot == entityOutputSlot) {
                item.onCraft(itemStack2, player.world, player);
                if (!this.insertItem(itemStack2, inventoryStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (invSlot >= entityInputStart && invSlot < entityOutputSlot ? !this.insertItem(itemStack2, inventoryStart, hotbarEnd, false) :
                    !this.insertItem(itemStack2, entityInputStart, entityOutputSlot, false) && (invSlot >= inventoryStart && invSlot < hotbarStart ? !this.insertItem(itemStack2, hotbarStart, hotbarEnd, false) :
                            invSlot >= hotbarStart && invSlot < hotbarEnd && !this.insertItem(itemStack2, inventoryStart, hotbarStart, false))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            }
            slot.markDirty();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, itemStack2);
            this.sendContentUpdates();
        }
        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
