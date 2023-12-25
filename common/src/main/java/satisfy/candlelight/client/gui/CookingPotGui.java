package satisfy.candlelight.client.gui;


import com.mojang.blaze3d.vertex.PoseStack;
import de.cristelknight.doapi.client.recipebook.screen.AbstractRecipeBookGUIScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import satisfy.candlelight.client.recipebook.CookingPotRecipeBook;
import satisfy.candlelight.client.gui.handler.CookingPotGuiHandler;
import satisfy.candlelight.util.CandlelightIdentifier;


@Environment(EnvType.CLIENT)
public class CookingPotGui extends AbstractRecipeBookGUIScreen<CookingPotGuiHandler> {
    public static final ResourceLocation BACKGROUND;
    public static final int ARROW_X = 95;
    public static final int ARROW_Y = 14;

    public CookingPotGui(CookingPotGuiHandler handler, Inventory playerInventory, Component title) {
        super(handler, playerInventory, title, new CookingPotRecipeBook(), BACKGROUND);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX += 20;
    }

    @Override
    public void renderProgressArrow(PoseStack matrices) {
        int progress = this.menu.getScaledProgress(18);
        this.blit(matrices, this.leftPos + ARROW_X, this.topPos + ARROW_Y, 178, 15, progress, 30);
    }

    @Override
    public void renderBurnIcon(PoseStack matrices, int posX, int posY) {
        if (this.menu.isBeingBurned()) {
            this.blit(matrices, posX + 124, posY + 56, 176, 0, 17, 15); //fire
        }
    }

    static {
        BACKGROUND = new CandlelightIdentifier("textures/gui/pot_gui.png");
    }
}