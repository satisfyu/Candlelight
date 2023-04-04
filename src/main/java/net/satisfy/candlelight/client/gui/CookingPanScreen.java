package net.satisfy.candlelight.client.gui;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.satisfy.candlelight.client.gui.handler.CookingPanScreenHandler;
import net.satisfy.candlelight.client.recipebook.CustomRecipeBookWidget;
import net.satisfy.candlelight.client.screen.recipe.CookingPanRecipeBook;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CookingPanScreen extends HandledScreen<CookingPanScreenHandler>{
    private static final Identifier RECIPE_BUTTON_TEXTURE;
    public final CustomRecipeBookWidget recipeBook;
    private boolean narrow;
    private static final Identifier BACKGROUND;


    public CookingPanScreen(CookingPanScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
        this.recipeBook = new CookingPanRecipeBook();
    }

    public void renderProgressArrow(MatrixStack matrices) {
        int progress = this.handler.getScaledProgress(18);
        this.drawTexture(matrices, x + 95, y + 14, 178, 15, progress, 30); //Position Arrow
    }

    public void renderBurnIcon(MatrixStack matrices, int posX, int posY) {
        if (handler.isBeingBurned()) {
            this.drawTexture(matrices, posX + 124, posY + 56, 176, 0, 17, 15); //fire
        }
    }

    @Override
    protected void init() {
        super.init();
        this.narrow = this.width < 379;
        this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, this.handler);
        this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
        this.addDrawableChild(new TexturedButtonWidget(this.x + 5, this.y + 25, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (button) -> {
            this.recipeBook.toggleOpen();
            this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
            ((TexturedButtonWidget)button).setPos(this.x +  5, this.y + 25);
        }));
        this.titleX += 20;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        if (this.recipeBook.isOpen() && this.narrow) {
            this.drawBackground(matrices, delta, mouseX, mouseY);
            this.recipeBook.render(matrices, mouseX, mouseY, delta);
        } else {
            this.recipeBook.render(matrices, mouseX, mouseY, delta);
            super.render(matrices, mouseX, mouseY, delta);
            this.recipeBook.drawGhostSlots(matrices, this.x, this.y, true, delta);
        }

        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
        this.recipeBook.drawTooltip(matrices, this.x, this.y, mouseX, mouseY);
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        this.recipeBook.update();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);

        final int posX = this.x;
        final int posY = this.y;
        this.drawTexture(matrices, posX, posY, 0, 0, this.backgroundWidth - 1, this.backgroundHeight);

        renderProgressArrow(matrices);
        renderBurnIcon(matrices, posX, posY);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            return this.narrow && this.recipeBook.isOpen() || super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {
        super.onMouseClick(slot, slotId, button, actionType);
        this.recipeBook.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return !this.recipeBook.keyPressed(keyCode, scanCode, modifiers) && super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.backgroundWidth) || mouseY >= (double)(top + this.backgroundHeight);
        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight) && bl;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return this.recipeBook.charTyped(chr, modifiers) || super.charTyped(chr, modifiers);
    }

    @Override
    public void removed() {
        this.recipeBook.close();
        super.removed();
    }

    static {
        BACKGROUND = new CandlelightIdentifier("textures/gui/pan_gui.png");
        RECIPE_BUTTON_TEXTURE = new CandlelightIdentifier("textures/gui/recipe_button.png");
    }
}
