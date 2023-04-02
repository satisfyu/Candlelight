package net.satisfy.candlelight.client.screen;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.satisfy.candlelight.recipe.CookingPanRecipe;

import java.util.List;

public class CookingPanRecipeBook extends AbstractCookingRecipeBookScreen {
    private static final Text TOGGLE_SMELTABLE_RECIPES_TEXT = Text.translatable("gui.recipebook.toggleRecipes.smeltable"); //TODO

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
            ItemStack inputStack = ingredient.getMatchingStacks()[0];
            this.ghostSlots.addSlot(inputStack, slots.get(j).x, slots.get(j++).y);
        }
    }

    @Override
    protected Text getToggleCraftableButtonText() {
        return TOGGLE_SMELTABLE_RECIPES_TEXT;
    }
}
