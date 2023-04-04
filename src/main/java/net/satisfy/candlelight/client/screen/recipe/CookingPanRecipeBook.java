package net.satisfy.candlelight.client.screen.recipe;

import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.satisfy.candlelight.client.recipebook.CustomRecipeBookWidget;
import net.satisfy.candlelight.recipe.CookingPanRecipe;
import net.satisfy.candlelight.registry.RecipeTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CookingPanRecipeBook extends CustomRecipeBookWidget {
    private static final Text TOGGLE_COOKABLE_TEXT;

    public CookingPanRecipeBook() {
    }

    @Override
    public void showGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        this.ghostSlots.addSlot(recipe.getOutput(), slots.get(7).x, slots.get(7).y);
        if (recipe instanceof CookingPanRecipe cookingPanRecipe) {
            this.ghostSlots.addSlot(cookingPanRecipe.getContainer(), slots.get(0).x, slots.get(0).y);
        }
        int j = 1;
        for (Ingredient ingredient : recipe.getIngredients()) {
            ItemStack inputStack = ingredient.getMatchingStacks()[0]; //TODO
            this.ghostSlots.addSlot(inputStack, slots.get(j).x, slots.get(j++).y);
        }
    }

    @Override
    public void insertRecipe(Recipe<?> recipe) {
        if (recipe instanceof CookingPanRecipe cookingPanRecipe) {
            int slotIndex = 0;
            for (Slot slot : screenHandler.slots) {
                if (cookingPanRecipe.getContainer().getItem() == slot.getStack().getItem()) {
                    MinecraftClient.getInstance().interactionManager.clickSlot(screenHandler.syncId, slotIndex, 0, SlotActionType.PICKUP, MinecraftClient.getInstance().player);
                    MinecraftClient.getInstance().interactionManager.clickSlot(screenHandler.syncId, 0, 0, SlotActionType.PICKUP, MinecraftClient.getInstance().player);
                    break;
                }
                ++slotIndex;
            }
        }
        int usedInputSlots = 1;
        for (Ingredient ingredient : recipe.getIngredients()) {
            int slotIndex = 0;
            for (Slot slot : screenHandler.slots) {
                ItemStack itemStack = slot.getStack();

                if (ingredient.test(itemStack) && usedInputSlots < 7) {
                    MinecraftClient.getInstance().interactionManager.clickSlot(screenHandler.syncId, slotIndex, 0, SlotActionType.PICKUP, MinecraftClient.getInstance().player);
                    MinecraftClient.getInstance().interactionManager.clickSlot(screenHandler.syncId, usedInputSlots, 0, SlotActionType.PICKUP, MinecraftClient.getInstance().player);
                    ++usedInputSlots;
                    break;
                }
                ++slotIndex;
            }
        }

    }

    @Override
    protected void setCraftableButtonTexture() {
        this.toggleCraftableButton.setTextureUV(152, 41, 28, 18, TEXTURE);
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.id < this.screenHandler.getCraftingSlotCount()) {
            this.ghostSlots.reset();
        }
    }

    @Override
    protected RecipeType<? extends Recipe<Inventory>> getRecipeType() {
        return RecipeTypes.COOKING_PAN_RECIPE_TYPE;
    }

    @Override
    protected Text getToggleCraftableButtonText() {
        return TOGGLE_COOKABLE_TEXT;
    }

    static {
        TOGGLE_COOKABLE_TEXT = Text.translatable("gui.candlelight.recipebook.toggleRecipes.cookable");
    }
}
