package net.satisfy.candlelight.compat.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;
import net.satisfy.candlelight.client.gui.CookingPanGui;
import net.satisfy.candlelight.compat.jei.CandlelightJEIPlugin;
import net.satisfy.candlelight.block.entity.CookingPanBlockEntity;
import net.satisfy.candlelight.recipe.CookingPanRecipe;

public class CookingPanCategory implements IRecipeCategory<CookingPanRecipe> {
    public static final RecipeType<CookingPanRecipe> COOKING_PAN = RecipeType.create(Candlelight.MOD_ID, "pan_cooking", CookingPanRecipe.class);
    public static final int WIDTH = 124;
    public static final int HEIGHT = 60;
    public static final int WIDTH_OF = 26;
    public static final int HEIGHT_OF = 13;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable burnIcon;
    private final IDrawableAnimated arrow;
    private final Component localizedName;

    public CookingPanCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(CookingPanGui.BACKGROUND, WIDTH_OF, HEIGHT_OF, WIDTH, HEIGHT);
        this.arrow = helper.drawableBuilder(CookingPanGui.BACKGROUND, 178, 15, 23, 30)
                .buildAnimated(CookingPanBlockEntity.MAX_COOKING_TIME, IDrawableAnimated.StartDirection.LEFT, false);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ObjectRegistry.COOKING_PAN.get().asItem().getDefaultInstance());
        this.burnIcon = helper.createDrawable(CookingPanGui.BACKGROUND, 176, 0, 17, 15);
        this.localizedName = Component.translatable("rei.candlelight.cooking_pan_category");
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CookingPanRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        int s = ingredients.size();

        builder.addSlot(RecipeIngredientRole.INPUT, 95 - WIDTH_OF, 55 - HEIGHT_OF).addItemStack(recipe.getContainer());

        for (int row = 0; row < 2; row++) {
            for (int slot = 0; slot < 3; slot++) {
                int current = slot + row + (row * 2);
                if(s - 1 < current) break;
                CandlelightJEIPlugin.addSlot(builder,30 + (slot * 18) - WIDTH_OF, 17 + (row * 18) - HEIGHT_OF, ingredients.get(current));
            }
        }
        assert Minecraft.getInstance().level != null;
        builder.addSlot(RecipeIngredientRole.OUTPUT, 124 - WIDTH_OF,  28 - HEIGHT_OF).addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
    }

    @Override
    public void draw(CookingPanRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, CookingPanGui.ARROW_X - WIDTH_OF, CookingPanGui.ARROW_Y - HEIGHT_OF);
        burnIcon.draw(guiGraphics, 124 - WIDTH_OF, 56 - HEIGHT_OF);
    }

    @Override
    public @NotNull RecipeType<CookingPanRecipe> getRecipeType() {
        return COOKING_PAN;
    }

    @Override
    public @NotNull Component getTitle() {
        return this.localizedName;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }
}
