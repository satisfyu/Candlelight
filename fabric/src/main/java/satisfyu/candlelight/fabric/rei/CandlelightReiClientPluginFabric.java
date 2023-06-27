package satisfyu.candlelight.fabric.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import satisfyu.candlelight.compat.rei.CandlelightReiClientPlugin;
import satisfyu.candlelight.compat.rei.cookingpan.CookingPanDisplay;
import satisfyu.candlelight.compat.rei.cookingpot.CookingPotDisplay;
import satisfyu.candlelight.recipe.CookingPanRecipe;
import satisfyu.candlelight.recipe.CookingPotRecipe;


public class CandlelightReiClientPluginFabric implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        CandlelightReiClientPlugin.registerCategories(registry);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        CandlelightReiClientPlugin.registerDisplays(registry);
    }
}
