package satisfyu.candlelight.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import satisfyu.candlelight.client.gui.handler.LetterGuiHandler;
import satisfyu.candlelight.util.CandlelightIdentifier;


@Environment(EnvType.CLIENT)
public class LetterGui extends AbstractContainerScreen<LetterGuiHandler> {

    private static final ResourceLocation TEXTURE = new CandlelightIdentifier("textures/gui/letter_gui.png");
    private EditBox nameField;

    public LetterGui(LetterGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    public void containerTick() {
        super.containerTick();
        this.nameField.tick();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.fill(x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    public void renderForeground(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.nameField.render(guiGraphics, mouseX, mouseY, delta);
        //TODO Geht das hier?!
        guiGraphics.drawString(this.font, (FormattedCharSequence) Component.translatable("block.candlelight.letter.translatable.text"), (int) (this.width / 1.95F - 1), (int) (this.height / 4.65F), 0x5A5A5A);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.renderForeground(guiGraphics, mouseX, mouseY, delta);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.minecraft.player.closeContainer();
        }

        return this.nameField.keyPressed(keyCode, scanCode, modifiers) || this.nameField.canConsumeInput() || super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.nameField = new EditBox(this.font, i + 62 + 30, j + 24, 50, 12, Component.empty());
        this.nameField.setCanLoseFocus(false);
        this.nameField.setTextColor(-1);
        this.nameField.setTextColorUneditable(-1);
        this.nameField.setBordered(false);
        this.nameField.setMaxLength(50);
        this.nameField.setResponder(this::onRenamed);
        this.nameField.setValue("");
        this.addWidget(this.nameField);
        this.setInitialFocus(this.nameField);
        this.nameField.setEditable(true);

        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    private void onRenamed(String name) {
        LetterGuiHandler.name = name;
    }
}
