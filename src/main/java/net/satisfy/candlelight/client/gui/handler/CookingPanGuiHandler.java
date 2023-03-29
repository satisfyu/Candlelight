package net.satisfy.candlelight.client.gui.handler;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.satisfy.candlelight.block.entity.CookingPanEntity;
import net.satisfy.candlelight.registry.ScreenHandlerTypes;
import satisfyu.vinery.client.gui.handler.slot.ExtendedSlot;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.screen.sideTip.RecipeGUIHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class CookingPanGuiHandler extends RecipeGUIHandler {

    public CookingPanGuiHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(8), new ArrayPropertyDelegate(2));
    }

    public CookingPanGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ScreenHandlerTypes.COOKING_PAN_SCREEN_HANDLER, syncId, playerInventory, inventory, 7, propertyDelegate);
        buildBlockEntityContainer(inventory);
        buildPlayerContainer(playerInventory);
    }

    private void buildBlockEntityContainer(Inventory inventory) {
        this.addSlot(new ExtendedSlot(inventory, 6,95, 55, stack -> stack.isOf(Item.fromBlock(ObjectRegistry.CHERRY_JAR)) || stack.isOf(Items.BOWL)|| stack.isOf(Item.fromBlock(net.satisfy.candlelight.registry.ObjectRegistry.TRAY))));

        for (int row = 0; row < 2; row++) {
            for (int slot = 0; slot < 3; slot++) {
                this.addSlot(new Slot(inventory, slot + row + (row * 2), 30 + (slot * 18), 17 + (row * 18)));
            }
        }

        this.addSlot(new Slot(inventory, 7, 124, 28) {
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

    public boolean isBeingBurned() {
        return propertyDelegate.get(1) != 0;
    }

    public int getScaledProgress(int arrowWidth) {
        final int progress = this.propertyDelegate.get(0);
        final int totalProgress = CookingPanEntity.MAX_COOKING_TIME;
        if (progress == 0) {
            return 0;
        }
        return progress * arrowWidth/ totalProgress + 1;
    }
}
