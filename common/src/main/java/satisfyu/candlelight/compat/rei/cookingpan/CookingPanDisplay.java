package satisfyu.candlelight.compat.rei.cookingpan;


import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.compat.rei.CandlelightReiClientPlugin;
import satisfyu.candlelight.recipe.CookingPanRecipe;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CookingPanDisplay extends BasicDisplay {

    public static final CategoryIdentifier<CookingPanDisplay> COOKING_PAN_DISPLAY = CategoryIdentifier.of(Candlelight.MOD_ID, "cooking_pan_display");

    public CookingPanDisplay(Recipe<Container> recipe) {
        this(EntryIngredients.ofIngredients(CandlelightReiClientPlugin.ingredients(recipe, getContainer(recipe))), Collections.singletonList(EntryIngredients.of(recipe.getResultItem())), Optional.ofNullable(recipe.getId()));
    }

    public CookingPanDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<ResourceLocation> location) {
        super(inputs, outputs, location);
    }


    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return COOKING_PAN_DISPLAY;
    }

    public static ItemStack getContainer(Recipe<Container> recipe){
        if(recipe instanceof CookingPanRecipe c){
            return c.getContainer();
        }
        else return ItemStack.EMPTY;
    }

}
