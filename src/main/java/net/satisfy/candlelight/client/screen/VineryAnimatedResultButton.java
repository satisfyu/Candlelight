package net.satisfy.candlelight.client.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.satisfy.candlelight.client.gui.handler.CookingPanScreenHandler;
import net.satisfy.candlelight.client.recipebook.VineryRecipeBook;

import java.util.Iterator;
import java.util.List;

public class VineryAnimatedResultButton extends ClickableWidget {
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("textures/gui/recipe_book.png");
    private static final Text MORE_RECIPES_TEXT = Text.translatable("gui.recipebook.moreRecipes");
    private CookingPanScreenHandler craftingScreenHandler;
    private VineryRecipeBook recipeBook;
    private VineryRecipeResultCollection resultCollection;
    private float time;
    private float bounce;
    private int currentResultIndex;

    public VineryAnimatedResultButton() {
        super(0, 0, 25, 25, ScreenTexts.EMPTY);
    }

    public void showResultCollection(VineryRecipeResultCollection resultCollection, VineryRecipeBookResults results) {
        this.resultCollection = resultCollection;
        this.craftingScreenHandler = (CookingPanScreenHandler) results.getClient().player.currentScreenHandler;
        this.recipeBook = results.getRecipeBook();
        List<Recipe<?>> list = resultCollection.getResults(this.recipeBook.isFilteringCraftable(this.craftingScreenHandler));

        for (Recipe<?> value : list) {
            if (this.recipeBook.shouldDisplay(value)) {
                results.onRecipesDisplayed(list);
                this.bounce = 15.0F;
                break;
            }
        }

    }

    public VineryRecipeResultCollection getResultCollection() {
        return this.resultCollection;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!Screen.hasControlDown()) {
            this.time += delta;
        }

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = 29;
        if (!this.resultCollection.hasCraftableRecipes()) {
            i += 25;
        }

        int j = 206;
        if (this.resultCollection.getResults(this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)).size() > 1) {
            j += 25;
        }

        boolean bl = this.bounce > 0.0F;
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        if (bl) {
            float f = 1.0F + 0.1F * (float)Math.sin((this.bounce / 15.0F * 3.1415927F));
            matrixStack.push();
            matrixStack.translate((double)(this.x + 8), (this.y + 12), 0.0);
            matrixStack.scale(f, f, 1.0F);
            matrixStack.translate((double)(-(this.x + 8)), (-(this.y + 12)), 0.0);
            RenderSystem.applyModelViewMatrix();
            this.bounce -= delta;
        }

        this.drawTexture(matrices, this.x, this.y, i, j, this.width, this.height);
        List<Recipe<?>> list = this.getResults();
        System.out.println("size" + list.size());
        this.currentResultIndex = MathHelper.floor(this.time / 30.0F) % list.size();
        ItemStack itemStack = (list.get(this.currentResultIndex)).getOutput();
        int k = 4;
        if (this.resultCollection.hasSingleOutput() && this.getResults().size() > 1) {
            minecraftClient.getItemRenderer().renderInGuiWithOverrides(itemStack, this.x + k + 1, this.y + k + 1, 0, 10);
            --k;
        }

        minecraftClient.getItemRenderer().renderInGui(itemStack, this.x + k, this.y + k);
        if (bl) {
            matrixStack.pop();
            RenderSystem.applyModelViewMatrix();
        }

    }

    private List<Recipe<?>> getResults() {
        List<Recipe<?>> list = this.resultCollection.getRecipes(true);
        if (!this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)) {
            list.addAll(this.resultCollection.getRecipes(false));
        }

        return list;
    }

    public boolean hasResults() {
        return this.getResults().size() == 1;
    }

    public Recipe<?> currentRecipe() {
        List<Recipe<?>> list = this.getResults();
        return (Recipe)list.get(this.currentResultIndex);
    }

    public List<Text> getTooltip(Screen screen) {
        ItemStack itemStack = (this.getResults().get(this.currentResultIndex)).getOutput();
        List<Text> list = Lists.newArrayList(screen.getTooltipFromItem(itemStack));
        if (this.resultCollection.getResults(this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)).size() > 1) {
            list.add(MORE_RECIPES_TEXT);
        }

        return list;
    }

    public void appendNarrations(NarrationMessageBuilder builder) {
        ItemStack itemStack = (this.getResults().get(this.currentResultIndex)).getOutput();
        builder.put(NarrationPart.TITLE, Text.translatable("narration.recipe", itemStack.getName()));
        if (this.resultCollection.getResults(this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)).size() > 1) {
            builder.put(NarrationPart.USAGE, Text.translatable("narration.button.usage.hovered"), Text.translatable("narration.recipe.usage.more"));
        } else {
            builder.put(NarrationPart.USAGE, Text.translatable("narration.button.usage.hovered"));
        }

    }

    public int getWidth() {
        return 25;
    }

    protected boolean isValidClickButton(int button) {
        return button == 0 || button == 1;
    }
}
