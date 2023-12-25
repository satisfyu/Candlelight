package satisfy.candlelight.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cristelknight.doapi.client.recipebook.screen.AbstractRecipeBookGUIScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import satisfy.candlelight.client.recipebook.CookingPanRecipeBook;
import satisfy.candlelight.client.gui.handler.CookingPanGuiHandler;
import satisfy.candlelight.util.CandlelightIdentifier;

@Environment(EnvType.CLIENT)
public class CookingPanGui extends AbstractRecipeBookGUIScreen<CookingPanGuiHandler> {
    public static final ResourceLocation BACKGROUND;
    public static final int ARROW_X = 95;
    public static final int ARROW_Y = 14;

    public CookingPanGui(CookingPanGuiHandler handler, Inventory playerInventory, Component title) {
        super(handler, playerInventory, title, new CookingPanRecipeBook(), getBackground());
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX += 20;
    }

    @Override
    public void renderProgressArrow(PoseStack matrices) {
        int progress = this.menu.getScaledProgress(18);
        this.blit(matrices, this.leftPos + 95, this.topPos + 14, 178, 15, progress, 30); //Position Arrow
    }

    @Override
    public void renderBurnIcon(PoseStack matrices, int posX, int posY) {
        if (this.menu.isBeingBurned()) {
            this.blit(matrices, posX + 124, posY + 56, 176, 0, 17, 15); //fire
        }
    }

    public static ResourceLocation getBackground() {
        return BACKGROUND;
    }

    static {
        BACKGROUND = new CandlelightIdentifier("textures/gui/pan_gui.png");
    }
}
