package satisfyu.candlelight.compat.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import satisfyu.candlelight.compat.rei.cookingpan.CookingPanCategory;
import satisfyu.candlelight.compat.rei.cookingpan.CookingPanDisplay;
import satisfyu.candlelight.recipe.CookingPanRecipe;

import java.util.ArrayList;
import java.util.List;


public class CandlelightReiClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CookingPanCategory());


        registry.addWorkstations(CookingPanDisplay.COOKING_PAN_DISPLAY, EntryStacks.of(satisfyu.candlelight.registry.ObjectRegistry.COOKING_PAN.get()));

    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(CookingPanRecipe.class, CookingPanDisplay::new);
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
    }

    public static List<Ingredient> ingredients(Recipe<Container> recipe, ItemStack stack){
        List<Ingredient> l = new ArrayList<>(recipe.getIngredients());
        l.add(0, Ingredient.of(stack.getItem()));
        return l;
    }

}
