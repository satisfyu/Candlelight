package satisfyu.candlelight.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import satisfyu.candlelight.client.gui.handler.WineStationGuiHandler;
import satisfyu.candlelight.util.CandlelightIdentifier;
@Environment(EnvType.CLIENT)
public class WineStationGui extends AbstractContainerScreen<WineStationGuiHandler> {

    private static final ResourceLocation BACKGROUND;

    public WineStationGui(WineStationGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);

        super.render(matrices, mouseX, mouseY, delta);

        renderTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }

    @Override
    protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);

        final int posX = this.leftPos;
        final int posY = this.topPos;
        this.blit(matrices, posX, posY, 0, 0, this.imageWidth - 1, this.imageHeight);
    }

    static {
        BACKGROUND = new CandlelightIdentifier("textures/gui/wine_station.png");
    }
}
