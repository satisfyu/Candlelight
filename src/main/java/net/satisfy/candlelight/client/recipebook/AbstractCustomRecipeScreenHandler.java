package net.satisfy.candlelight.client.recipebook;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractCustomRecipeScreenHandler extends ScreenHandler {
    protected AbstractCustomRecipeScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    public abstract List<IRecipeBookGroup>  getGroups();

    public abstract boolean hasIngredient(Recipe<?> recipe);

    public abstract void populateRecipeFinder(RecipeMatcher finder);

    public abstract int getCraftingSlotCount();
}
