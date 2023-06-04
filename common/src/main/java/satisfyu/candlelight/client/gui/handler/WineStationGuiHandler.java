package satisfyu.candlelight.client.gui.handler;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import satisfyu.candlelight.client.gui.handler.slot.ExtendedSlot;
import satisfyu.candlelight.registry.ScreenHandlerTypes;
import satisfyu.candlelight.util.CandlelightTags;

public class WineStationGuiHandler extends AbstractContainerMenu {

    public final Level world;
    public final Container inventory;
    public final ContainerData propertyDelegate;
    private final int inputSlots;

    public WineStationGuiHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(5), new SimpleContainerData(1));
    }


    public WineStationGuiHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(ScreenHandlerTypes.WINE_STATION_SCREEN_HANDLER.get(), syncId);
        this.world = playerInventory.player.level;
        this.inventory = inventory;
        this.inputSlots = 3;
        this.propertyDelegate = propertyDelegate;
        addDataSlots(this.propertyDelegate);
        buildBlockEntityContainer(inventory);
        buildPlayerContainer(playerInventory);
    }

    private void buildBlockEntityContainer(Container inventory) {
        this.addSlot(new ExtendedSlot(inventory, 0,44, 20, (itemStack -> itemStack.is(CandlelightTags.WINE))));
        this.addSlot(new ExtendedSlot(inventory, 1,35, 39, (itemStack -> itemStack.is(CandlelightTags.WINE))));
        this.addSlot(new ExtendedSlot(inventory, 2,54, 39, (itemStack -> itemStack.is(CandlelightTags.WINE))));

        this.addSlot(new Slot(inventory, 3, 106, 18) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 4, 124, 36) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
    }

    private void buildPlayerContainer(Inventory playerInventory) {
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
    public ItemStack quickMoveStack(Player player, int invSlot) {
        final int entityInputStart = 0;
        int entityOutputSlot = this.inputSlots;
        final int inventoryStart = entityOutputSlot + 1;
        final int hotbarStart = inventoryStart + 9 * 3;
        final int hotbarEnd = hotbarStart + 9;


        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (invSlot == entityOutputSlot) {
                item.onCraftedBy(itemStack2, player.level, player);
                if (!this.moveItemStackTo(itemStack2, inventoryStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack2, itemStack);
            } else if (invSlot >= entityInputStart && invSlot < entityOutputSlot ? !this.moveItemStackTo(itemStack2, inventoryStart, hotbarEnd, false) :
                    !this.moveItemStackTo(itemStack2, entityInputStart, entityOutputSlot, false) && (invSlot >= inventoryStart && invSlot < hotbarStart ? !this.moveItemStackTo(itemStack2, hotbarStart, hotbarEnd, false) :
                            invSlot >= hotbarStart && invSlot < hotbarEnd && !this.moveItemStackTo(itemStack2, inventoryStart, hotbarStart, false))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }
            slot.setChanged();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, itemStack2);
            this.broadcastChanges();
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }
}
