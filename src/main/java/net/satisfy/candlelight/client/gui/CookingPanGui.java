package net.satisfy.candlelight.client.gui;


import net.satisfy.candlelight.client.gui.handler.CookingPanGuiHandler;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import satisfyu.vinery.VineryIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import satisfyu.vinery.screen.sideTip.RecipeHandledGUI;
import satisfyu.vinery.screen.sideTip.SideToolTip;

public class CookingPanGui extends RecipeHandledGUI<CookingPanGuiHandler> {

    private static final Identifier BACKGROUND;
    private static final Identifier SIDE_TIP;
    private static final int FRAMES = 1;

    public CookingPanGui(CookingPanGuiHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, BACKGROUND, SIDE_TIP, FRAMES);
    }

    public void renderProgressArrow(MatrixStack matrices, int posX, int posY) {
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
        titleX += 20;
    }

    @Override
    public void addToolTips() {
        super.addToolTips();
        final int normalWidthAndHeight = 18;
        final int firstRow = 15;
        final int secondRow = 41;
        final int containerRow = 67;
        final int resultRow = 101;

        final int firstLine = 19;
        addToolTip(new SideToolTip(firstRow, firstLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.dough")));
        addToolTip(new SideToolTip(secondRow, firstLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.butter")));
        addToolTip(new SideToolTip(containerRow, firstLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.strawberry")));
        addToolTip(new SideToolTip(resultRow, firstLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.pancake")));

        final int secondLine = 44;
        addToolTip(new SideToolTip(firstRow, secondLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.tomato_soup")));
        addToolTip(new SideToolTip(secondRow, secondLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.cooked_beef")));
        addToolTip(new SideToolTip(containerRow, secondLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.red_wine")));
        addToolTip(new SideToolTip(resultRow, secondLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.bolognese")));

        final int thirdLine = 69;
        addToolTip(new SideToolTip(firstRow, thirdLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.beef")));
        addToolTip(new SideToolTip(secondRow, thirdLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.butter")));
        addToolTip(new SideToolTip(containerRow, thirdLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.red_wine")));
        addToolTip(new SideToolTip(resultRow, thirdLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.cooked_beef")));

        final int fourthLine = 94;
        addToolTip(new SideToolTip(firstRow, fourthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.mashed_potatoes")));
        addToolTip(new SideToolTip(secondRow, fourthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.cooked_beef")));
        addToolTip(new SideToolTip(containerRow, fourthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.white_wine")));
        addToolTip(new SideToolTip(resultRow, fourthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.fricasse")));

        final int fifthLine = 119;
        addToolTip(new SideToolTip(firstRow, fifthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.butter")));
        addToolTip(new SideToolTip(secondRow, fifthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.beef")));
        addToolTip(new SideToolTip(containerRow, fifthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.red_wine")));
        addToolTip(new SideToolTip(resultRow, fifthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.broccoli_beef")));

        final int sixthLine = 144;
        addToolTip(new SideToolTip(firstRow, sixthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.carrot")));
        addToolTip(new SideToolTip(secondRow, sixthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.beef")));
        addToolTip(new SideToolTip(containerRow, sixthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.red_wine")));
        addToolTip(new SideToolTip(resultRow, sixthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.candlelight.roastbeef_carrots")));
    }

    static {
        BACKGROUND = new CandlelightIdentifier("textures/gui/pan_gui.png");
        SIDE_TIP = new CandlelightIdentifier("textures/gui/cooking_pan_recipe_book.png");
    }
}
