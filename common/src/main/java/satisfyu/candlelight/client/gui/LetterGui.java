package satisfyu.candlelight.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import satisfyu.candlelight.client.gui.handler.LetterGuiHandler;
import satisfyu.candlelight.util.CandlelightIdentifier;
@Environment(EnvType.CLIENT)
public class LetterGui extends AbstractContainerScreen<LetterGuiHandler> {

    private static final ResourceLocation TEXTURE = new CandlelightIdentifier("textures/gui/letter_gui.png");
    private EditBox nameField;
    private static final Component FOR = Component.translatable("candlelight.repair");

    public LetterGui(LetterGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    public void containerTick() {
        super.containerTick();
        this.nameField.tick();
    }

    protected void setup() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.nameField = new EditBox(this.font, i + 62 + 30, j + 24, 50, 12, FOR);
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
    }

    @Override
    protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (int) ((height - imageHeight) / 2);
        blit(matrices, x, y, 0, 0, imageWidth, imageHeight);
    }

    public void renderForeground(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.nameField.render(matrices, mouseX, mouseY, delta);
        this.font.draw(matrices, Component.translatable("block.candlelight.letter.translatable.text"), this.width / 1.95F - 1, (int)(this.height / 4.65F) + 8, 0x5A5A5A);
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.renderForeground(matrices, mouseX, mouseY, delta);
        renderTooltip(matrices, mouseX, mouseY);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.minecraft.player.closeContainer();
        }

        return !this.nameField.keyPressed(keyCode, scanCode, modifiers) && !this.nameField.canConsumeInput() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
    }

    @Override
    protected void init() {
        super.init();
        setup();
        titleLabelX = (imageWidth - font.width(title)) / 2;
    }

    private void onRenamed(String name) {
        String string = name;
        Slot slot = ((LetterGuiHandler)this.menu).getSlot(0);
        LetterGuiHandler.name = name;
    }
}
