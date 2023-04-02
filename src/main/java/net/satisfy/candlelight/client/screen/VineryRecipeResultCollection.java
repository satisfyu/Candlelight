package net.satisfy.candlelight.client.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.satisfy.candlelight.client.recipebook.VineryRecipeBook;

import java.util.List;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class VineryRecipeResultCollection {
    private final List<Recipe<?>> recipes;
    private final boolean singleOutput;
    private final Set<Recipe<?>> craftableRecipes = Sets.newHashSet();
    private final Set<Recipe<?>> fittingRecipes = Sets.newHashSet();
    private final Set<Recipe<?>> unlockedRecipes = Sets.newHashSet();

    public VineryRecipeResultCollection(List<Recipe<?>> recipes) {
        this.recipes = ImmutableList.copyOf(recipes);
        if (recipes.size() <= 1) {
            this.singleOutput = true;
        } else {
            this.singleOutput = shouldHaveSingleOutput(recipes);
        }

    }

    private static boolean shouldHaveSingleOutput(List<Recipe<?>> recipes) {
        int i = recipes.size();
        ItemStack itemStack = (recipes.get(0)).getOutput();

        for(int j = 1; j < i; ++j) {
            ItemStack itemStack2 = (recipes.get(j)).getOutput();
            if (!ItemStack.areItemsEqualIgnoreDamage(itemStack, itemStack2) || !ItemStack.areNbtEqual(itemStack, itemStack2)) {
                return false;
            }
        }

        return true;
    }

    public boolean isInitialized() {
        return !this.unlockedRecipes.isEmpty();
    }

    public void initialize(VineryRecipeBook recipeBook) {
        for (Recipe<?> value : this.recipes) {
            if (recipeBook.contains(value)) {
                this.unlockedRecipes.add(value);
            }
        }

    }

    public void computeCraftables(RecipeMatcher recipeFinder, int gridWidth, int gridHeight, VineryRecipeBook recipeBook) {
        for (Recipe<?> recipe : this.recipes) {
            boolean bl = recipe.fits(gridWidth, gridHeight) && recipeBook.contains(recipe); //TODO
            if (bl) {
                this.fittingRecipes.add(recipe);
            } else {
                this.fittingRecipes.remove(recipe);
            }

            //if (bl && recipeFinder.match(recipe, null)) {
            if(bl) {
                this.craftableRecipes.add(recipe);
            } else {
                this.craftableRecipes.remove(recipe);
            }
        }
    }

    public boolean isCraftable(Recipe<?> recipe) {
        return this.craftableRecipes.contains(recipe);
    }

    public boolean hasCraftableRecipes() {
        return !this.craftableRecipes.isEmpty();
    }

    public boolean hasFittingRecipes() {
        return !this.fittingRecipes.isEmpty();
    }

    public List<Recipe<?>> getAllRecipes() {
        return this.recipes;
    }

    public List<Recipe<?>> getResults(boolean craftableOnly) {
        List<Recipe<?>> list = Lists.newArrayList();
        Set<Recipe<?>> set = craftableOnly ? this.craftableRecipes : this.fittingRecipes;

        for (Recipe<?> value : this.recipes) {
            if (set.contains(value)) {
                list.add(value);
            }
        }

        return list;
    }

    public List<Recipe<?>> getRecipes(boolean craftable) {
        List<Recipe<?>> list = Lists.newArrayList();
        for (Recipe<?> recipe : this.recipes) {
            //if (this.fittingRecipes.contains(recipe) && this.craftableRecipes.contains(recipe) == craftable) { //TODO
                list.add(recipe);
            //}
        }

        return list;
    }

    public boolean hasSingleOutput() {
        return this.singleOutput;
    }
}
