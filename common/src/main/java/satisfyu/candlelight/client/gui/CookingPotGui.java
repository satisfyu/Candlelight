package satisfyu.candlelight.client.gui;


import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import satisfyu.candlelight.client.gui.custom.CookingPotRecipeBook;
import satisfyu.candlelight.client.gui.handler.CookingPotGuiHandler;
import satisfyu.candlelight.util.CandlelightIdentifier;


@Environment(EnvType.CLIENT)
public class CookingPotGui extends AbstractRecipeBookGUIScreen<CookingPotGuiHandler> {
    private static final ResourceLocation BACKGROUND;


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
        this.blit(matrices, this.leftPos + 95, this.topPos + 14, 178, 15, progress, 30); //Position Arrow
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
