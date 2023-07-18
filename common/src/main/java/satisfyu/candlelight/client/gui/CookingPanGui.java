package satisfyu.candlelight.client.gui;


import com.mojang.blaze3d.vertex.PoseStack;
import de.cristelknight.doapi.client.recipebook.screen.AbstractRecipeBookGUIScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import satisfyu.candlelight.client.recipebook.CookingPanRecipeBook;
import satisfyu.candlelight.client.gui.handler.CookingPanGuiHandler;
import satisfyu.candlelight.util.CandlelightIdentifier;

@Environment(EnvType.CLIENT)
public class CookingPanGui extends AbstractRecipeBookGUIScreen<CookingPanGuiHandler> {
    private static final ResourceLocation BACKGROUND;

    public CookingPanGui(CookingPanGuiHandler handler, Inventory playerInventory, Component title) {
        super(handler, playerInventory, title, new CookingPanRecipeBook(), BACKGROUND);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX += 20;
    }

    @Override
    public void renderProgressArrow(GuiGraphics guiGraphics) {
        int progress = this.menu.getScaledProgress(23);
        guiGraphics.blit(BACKGROUND,this.leftPos + 95, this.topPos + 14, 178, 15, progress, 30);
    }

    @Override
    public void renderBurnIcon(GuiGraphics guiGraphics, int posX, int posY) {
        if (this.menu.isBeingBurned()) {
           guiGraphics.blit(BACKGROUND,posX + 124, posY + 56, 176, 0, 17, 15);
        }
    }

    static {
        BACKGROUND = new CandlelightIdentifier("textures/gui/pan_gui.png");
    }
}
