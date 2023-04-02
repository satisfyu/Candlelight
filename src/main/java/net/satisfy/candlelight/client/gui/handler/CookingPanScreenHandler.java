package net.satisfy.candlelight.client.gui.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.world.World;
import net.satisfy.candlelight.block.entity.CookingPanEntity;
import net.satisfy.candlelight.client.recipebook.VineryRecipeBookCategory;
import net.satisfy.candlelight.registry.RecipeTypes;
import net.satisfy.candlelight.registry.ScreenHandlerTypes;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.client.gui.handler.slot.ExtendedSlot;
import satisfyu.vinery.registry.ObjectRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class CookingPanScreenHandler extends AbstractRecipeScreenHandler<Inventory> {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected final World world;
    private final RecipeType<? extends Recipe<?>> recipeType;
    private final VineryRecipeBookCategory category;
    private final int inputSlots;

    public CookingPanScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(8), new ArrayPropertyDelegate(2));
    }

    public CookingPanScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ScreenHandlerTypes.COOKING_PAN_SCREEN_HANDLER, syncId);
        this.recipeType = RecipeTypes.COOKING_PAN_RECIPE_TYPE;
        this.category = VineryRecipeBookCategory.PAN;
        checkSize(inventory, 8);
        checkDataCount(propertyDelegate, 2);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.world;
        this.inputSlots = 7;

        buildBlockEntityContainer(inventory);
        buildPlayerContainer(playerInventory);

        this.addProperties(propertyDelegate);
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

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        if (this.inventory instanceof RecipeInputProvider) {
            ((RecipeInputProvider)this.inventory).provideRecipeInputs(finder);
        }
    }

    @Override
    public void clearCraftingSlots() {
        for (int i = 0; i < 7; i++) {
            this.getSlot(i).setStack(ItemStack.EMPTY);
        }
    }

    @Override
    public boolean matches(Recipe<? super Inventory> recipe) {
        return recipe.matches(this.inventory, this.world);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 7;
    }

    @Override
    public int getCraftingWidth() {
        return 3;
    }

    @Override
    public int getCraftingHeight() {
        return 2;
    }

    @Override
    public int getCraftingSlotCount() {
        return 7;
    }

    @Nullable
    @Override
    public RecipeBookCategory getCategory() {
        return null;
    }

    public VineryRecipeBookCategory getVineryCategory() {
        return this.category;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != 7;
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
