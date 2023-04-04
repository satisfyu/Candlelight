package net.satisfy.candlelight.client.screen.recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.slot.Slot;
import net.satisfy.candlelight.client.recipebook.AbstractCustomRecipeScreenHandler;

@Environment(EnvType.CLIENT)
public record CustomRecipeBookRecipe(Recipe<?> recipe) {

    public boolean hasIngredient(AbstractCustomRecipeScreenHandler<?> handledScreen) { //TODO anpassbar wann alles da
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
