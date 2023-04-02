package net.satisfy.candlelight.client.recipebook;

import com.google.common.collect.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBook;
import net.satisfy.candlelight.client.screen.VineryRecipeResultCollection;
import net.satisfy.candlelight.registry.RecipeTypes;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.*;
@Environment(EnvType.CLIENT)
public class VineryClientRecipeBook extends RecipeBook {

    public <T extends Recipe<Inventory>> List<VineryRecipeResultCollection> getResultsForGroup(VineryRecipeBookGroup group, List<T> recipes) {
        List<VineryRecipeResultCollection> results = Lists.newArrayList();
        for (T recipe : recipes) {
            if (recipe.getType() == RecipeTypes.COOKING_PAN_RECIPE_TYPE) {
                if (getRecipeGroup(recipe) == group || group == VineryRecipeBookGroup.SEARCH) {
                    results.add(new VineryRecipeResultCollection(recipe));
                }
            }
        }
        return results;
    }

    private VineryRecipeBookGroup getRecipeGroup(Recipe<?> recipe) {
        RecipeType<?> recipeType = recipe.getType();
        if (recipeType == RecipeTypes.COOKING_PAN_RECIPE_TYPE) {
            if (recipe.getIngredients().stream().anyMatch((ingredient) -> ingredient.test(ObjectRegistry.NOIR_WINE.asItem().getDefaultStack()))) {
                return VineryRecipeBookGroup.WINE;
            } else {
                return VineryRecipeBookGroup.MISC;
            }
        }
        return VineryRecipeBookGroup.UNKNOWN;
    }
}
