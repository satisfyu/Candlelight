package net.satisfy.candlelight.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.slot.Slot;
import net.satisfy.candlelight.client.gui.handler.CookingPanScreenHandler;

@Environment(EnvType.CLIENT)
public class VineryRecipeResultCollection {
    private final Recipe<?> recipe;

    public VineryRecipeResultCollection(Recipe<?> recipe) {
        this.recipe = recipe;
    }

    public Recipe<?> getRecipe() {
        return recipe;
    }

    public boolean hasIngredient(CookingPanScreenHandler handledScreen) {
        for (Ingredient ingredient : recipe.getIngredients()) {
            boolean found = false;
            for (Slot slot : handledScreen.slots) {
                if (ingredient.test(slot.getStack())) {
                    found = true;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}
