package satisfy.candlelight.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import satisfy.candlelight.Candlelight;
import satisfy.candlelight.client.gui.CookingPanGui;
import satisfy.candlelight.entity.CookingPanBlockEntity;
import satisfy.candlelight.recipe.CookingPanRecipe;
import satisfy.candlelight.registry.ObjectRegistry;

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
        this.background = helper.createDrawable(CookingPanGui.getBackground(), WIDTH_OF, HEIGHT_OF, WIDTH, HEIGHT);
        this.arrow = helper.drawableBuilder(CookingPanGui.getBackground(), 178, 15, 23, 30)
                .buildAnimated(CookingPanBlockEntity.MAX_COOKING_TIME, IDrawableAnimated.StartDirection.LEFT, false);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ObjectRegistry.COOKING_PAN.get().asItem().getDefaultInstance());
        this.burnIcon = helper.createDrawable(CookingPanGui.getBackground(), 176, 0, 17, 15);
        this.localizedName = Component.translatable("rei.candlelight.cooking_pan_category");
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CookingPanRecipe recipe, IFocusGroup focuses) {
        // ... (unchanged)
    }

    @Override
    public void draw(CookingPanRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        arrow.draw(stack, CookingPanGui.ARROW_X - WIDTH_OF, CookingPanGui.ARROW_Y - HEIGHT_OF);
        burnIcon.draw(stack, 124 - WIDTH_OF, 56 - HEIGHT_OF);
    }

    @Override
    public RecipeType<CookingPanRecipe> getRecipeType() {
        return COOKING_PAN;
    }

    @Override
    public Component getTitle() {
        return this.localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }
}
