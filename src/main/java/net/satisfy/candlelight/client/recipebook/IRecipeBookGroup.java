package net.satisfy.candlelight.client.recipebook;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;

import java.util.List;

public interface IRecipeBookGroup {
    boolean fitRecipe(Recipe<?> recipe);
    List<ItemStack> getIcons();
}
