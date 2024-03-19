package satisfy.candlelight.compat.jei;

import dev.architectury.registry.registries.RegistrySupplier;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import satisfy.candlelight.compat.jei.category.CookingPanCategory;
import satisfy.candlelight.compat.jei.category.CookingPotCategory;
import satisfy.candlelight.compat.jei.transfer.CookingPanTransferInfo;
import satisfy.candlelight.compat.jei.transfer.CookingPotTransferInfo;
import satisfy.candlelight.recipe.CookingPanRecipe;
import satisfy.candlelight.recipe.CookingPotRecipe;
import satisfy.candlelight.registry.ObjectRegistry;
import satisfy.candlelight.registry.RecipeTypeRegistry;
import satisfy.candlelight.util.CandlelightIdentifier;

import java.util.List;
import java.util.Objects;


@JeiPlugin
public class CandlelightJEIPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CookingPotCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CookingPanCategory(registration.getJeiHelpers().getGuiHelper()));
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<CookingPotRecipe> cookingPotRecipes = rm.getAllRecipesFor(RecipeTypeRegistry.COOKING_POT_RECIPE_TYPE.get());
        registration.addRecipes(CookingPotCategory.COOKING_POT, cookingPotRecipes);

        List<CookingPanRecipe> cookingPanRecipes = rm.getAllRecipesFor(RecipeTypeRegistry.COOKING_PAN_RECIPE_TYPE.get());
        registration.addRecipes(CookingPanCategory.COOKING_PAN, cookingPanRecipes);
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new CandlelightIdentifier("jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        addCatalyst(registration, ObjectRegistry.COOKING_POT, CookingPotCategory.COOKING_POT);
        addCatalyst(registration, ObjectRegistry.COOKING_PAN, CookingPanCategory.COOKING_PAN);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new CookingPotTransferInfo());
        registration.addRecipeTransferHandler(new CookingPanTransferInfo());
    }

    private static void addCatalyst(IRecipeCatalystRegistration registration, RegistrySupplier<Block> block, RecipeType<?>... recipeTypes){
        registration.addRecipeCatalyst(block.get().asItem().getDefaultInstance(), recipeTypes);
    }
    public static void addSlot(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient){
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredient);
    }
}
