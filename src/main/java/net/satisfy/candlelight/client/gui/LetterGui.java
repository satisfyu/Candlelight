package net.satisfy.candlelight.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.satisfy.candlelight.client.gui.handler.LetterGuiHandler;
import net.satisfy.candlelight.util.CandlelightIdentifier;

public class LetterGui extends HandledScreen<LetterGuiHandler> {

    private static final Identifier TEXTURE = new CandlelightIdentifier("textures/gui/letter_gui.png");
    private TextFieldWidget nameField;


    public LetterGui(LetterGuiHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    public void handledScreenTick() {
        super.handledScreenTick();
        this.nameField.tick();
    }


    protected void setup() {
        this.client.keyboard.setRepeatEvents(true);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.nameField = new TextFieldWidget(this.textRenderer, i + 62 + 30, j + 24, 50, 12, Text.translatable("container.repair"));
        this.nameField.setFocusUnlocked(false);
        this.nameField.setEditableColor(-1);
        this.nameField.setUneditableColor(-1);
        this.nameField.setDrawsBackground(false);
        this.nameField.setMaxLength(50);
        this.nameField.setChangedListener(this::onRenamed);
        this.nameField.setText("");
        this.addSelectableChild(this.nameField);
        this.setInitialFocus(this.nameField);
        this.nameField.setEditable(true);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    public void renderForeground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.nameField.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.renderForeground(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.client.player.closeHandledScreen();
        }

        return !this.nameField.keyPressed(keyCode, scanCode, modifiers) && !this.nameField.isActive() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
    }

    @Override
    protected void init() {
        super.init();
        setup();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    private void onRenamed(String name) {
       // if (!name.isEmpty()) {
            String string = name;
            Slot slot = ((LetterGuiHandler)this.handler).getSlot(0);
//            if (slot != null && slot.hasStack() && !slot.getStack().hasCustomName() && name.equals(slot.getStack().getName().getString())) {
//                string = "";
//            }
            //System.out.println(name + " screne");
            LetterGuiHandler.name = name;
            //this.client.player.networkHandler.sendPacket(new RenameItemC2SPacket(string));
      //  }
    }
}
