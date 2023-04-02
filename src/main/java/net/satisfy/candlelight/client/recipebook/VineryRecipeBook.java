package net.satisfy.candlelight.client.recipebook;

import com.google.common.collect.Sets;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.util.Identifier;
import net.satisfy.candlelight.client.gui.handler.CookingPanScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class VineryRecipeBook extends RecipeBook {
    protected final Set<Identifier> recipes = Sets.newHashSet();
    protected final Set<Identifier> toBeDisplayed = Sets.newHashSet();
    private final VineryRecipeBookOptions options = new VineryRecipeBookOptions();

    public VineryRecipeBook() {
    }

    public void copyFrom(VineryRecipeBook book) {
        this.recipes.clear();
        this.toBeDisplayed.clear();
        this.options.copyFrom(book.options);
        this.recipes.addAll(book.recipes);
        this.toBeDisplayed.addAll(book.toBeDisplayed);
    }

    public void add(Recipe<?> recipe) {
        if (!recipe.isIgnoredInRecipeBook()) {
            this.add(recipe.getId());
        }

    }

    protected void add(Identifier id) {
        this.recipes.add(id);
    }

    public boolean contains(@Nullable Recipe<?> recipe) {
        return recipe != null && this.recipes.contains(recipe.getId());
    }

    public boolean contains(Identifier id) {
        return this.recipes.contains(id);
    }

    public void remove(Recipe<?> recipe) {
        this.remove(recipe.getId());
    }

    protected void remove(Identifier id) {
        this.recipes.remove(id);
        this.toBeDisplayed.remove(id);
    }

    public boolean shouldDisplay(Recipe<?> recipe) {
        return this.toBeDisplayed.contains(recipe.getId());
    }

    public void onRecipeDisplayed(Recipe<?> recipe) {
        this.toBeDisplayed.remove(recipe.getId());
    }

    public void display(Recipe<?> recipe) {
        this.display(recipe.getId());
    }

    protected void display(Identifier id) {
        this.toBeDisplayed.add(id);
    }

    public boolean isGuiOpen(VineryRecipeBookCategory category) {
        return this.options.isGuiOpen(category);
    }

    public void setGuiOpen(VineryRecipeBookCategory category, boolean open) {
        this.options.setGuiOpen(category, open);
    }

    public boolean isFilteringCraftable(CookingPanScreenHandler handler) {
        return this.isFilteringCraftable(handler.getVineryCategory());
    }

    public boolean isFilteringCraftable(VineryRecipeBookCategory category) {
        return this.options.isFilteringCraftable(category);
    }
}