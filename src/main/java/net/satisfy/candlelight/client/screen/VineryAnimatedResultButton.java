package net.satisfy.candlelight.client.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.satisfy.candlelight.client.gui.handler.CookingPanScreenHandler;

import java.util.List;

public class VineryAnimatedResultButton extends ClickableWidget {
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("textures/gui/recipe_book.png");
    private static final Text MORE_RECIPES_TEXT = Text.translatable("gui.recipebook.moreRecipes");
    private CookingPanScreenHandler craftingScreenHandler;
    private VineryRecipeResultCollection resultCollection;
    private float bounce;

    public VineryAnimatedResultButton() {
        super(0, 0, 25, 25, ScreenTexts.EMPTY);
    }

    public void showResultCollection(VineryRecipeResultCollection resultCollection, CookingPanScreenHandler craftingScreenHandler) {
        this.resultCollection = resultCollection;
        this.craftingScreenHandler = craftingScreenHandler;
    }

    public VineryRecipeResultCollection getResultCollection() {
        return this.resultCollection;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = 29;
        if (!this.resultCollection.hasIngredient(craftingScreenHandler)) {
            i += 25;
        }

        int j = 206;

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
        Recipe<?> recipe = this.getResult();
        int k = 4;

        minecraftClient.getItemRenderer().renderInGui(recipe.getOutput(), this.x + k, this.y + k);
        if (bl) {
            matrixStack.pop();
            RenderSystem.applyModelViewMatrix();
        }

    }

    private Recipe<?> getResult() {
        Recipe<?> list = this.resultCollection.getRecipe();
        return list;
    }

    public boolean hasResult() {
        return this.getResult() != null;
    }

    public Recipe<?> currentRecipe() {
        return this.getResult();
    }

    public List<Text> getTooltip(Screen screen) {
        ItemStack itemStack = this.getResult().getOutput();
        return Lists.newArrayList(screen.getTooltipFromItem(itemStack));
    }

    public void appendNarrations(NarrationMessageBuilder builder) {
        ItemStack itemStack = this.getResult().getOutput();
        builder.put(NarrationPart.TITLE, Text.translatable("narration.recipe", itemStack.getName()));

        builder.put(NarrationPart.USAGE, Text.translatable("narration.button.usage.hovered"));
    }

    public int getWidth() {
        return 25;
    }

    protected boolean isValidClickButton(int button) {
        return button == 0 || button == 1;
    }
}