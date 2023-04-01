package net.satisfy.candlelight.client.recipebook;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.satisfy.candlelight.client.screen.VineryRecipeResultCollection;

import java.util.List;

@Environment(EnvType.CLIENT)
public class VineryRecipeGroupButtonWidget extends ToggleButtonWidget {
    private final VineryRecipeBookGroup category;
    private float bounce;

    public VineryRecipeGroupButtonWidget(VineryRecipeBookGroup category) {
        super(0, 0, 35, 27, false);
        this.category = category;
        this.setTextureUV(153, 2, 35, 0, VineryRecipeBookWidget.TEXTURE);
    }

    public void checkForNewRecipes(MinecraftClient client) {
        VineryClientRecipeBook clientRecipeBook = new VineryClientRecipeBook();
        List<VineryRecipeResultCollection> list = clientRecipeBook.getResultsForGroup(this.category);
        if (client.player.currentScreenHandler instanceof AbstractRecipeScreenHandler) {

            for (VineryRecipeResultCollection recipeResultCollection : list) {

                for (Recipe<?> value : recipeResultCollection.getResults(clientRecipeBook.isFilteringCraftable((AbstractRecipeScreenHandler) client.player.currentScreenHandler))) {
                    if (clientRecipeBook.shouldDisplay(value)) {
                        this.bounce = 15.0F;
                        return;
                    }
                }
            }

        }
    }

    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.bounce > 0.0F) {
            float f = 1.0F + 0.1F * (float)Math.sin((this.bounce / 15.0F * 3.1415927F));
            matrices.push();
            matrices.translate((this.x + 8), (this.y + 12), 0.0);
            matrices.scale(1.0F, f, 1.0F);
            matrices.translate((-(this.x + 8)), (-(this.y + 12)), 0.0);
        }

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.texture);
        RenderSystem.disableDepthTest();
        int i = this.u;
        int j = this.v;
        if (this.toggled) {
            i += this.pressedUOffset;
        }

        if (this.isHovered()) {
            j += this.hoverVOffset;
        }

        int k = this.x;
        if (this.toggled) {
            k -= 2;
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexture(matrices, k, this.y, i, j, this.width, this.height);
        RenderSystem.enableDepthTest();
        this.renderIcons(minecraftClient.getItemRenderer());
        if (this.bounce > 0.0F) {
            matrices.pop();
            this.bounce -= delta;
        }
    }

    private void renderIcons(ItemRenderer itemRenderer) {
        List<ItemStack> list = this.category.getIcons();
        int i = this.toggled ? -2 : 0;
        if (list.size() == 1) {
            itemRenderer.renderInGui( list.get(0), this.x + 9 + i, this.y + 5);
        } else if (list.size() == 2) {
            itemRenderer.renderInGui( list.get(0), this.x + 3 + i, this.y + 5);
            itemRenderer.renderInGui( list.get(1), this.x + 14 + i, this.y + 5);
        }

    }

    public VineryRecipeBookGroup getVineryCategory() {
        return this.category;
    }

    public boolean hasKnownRecipes(VineryClientRecipeBook recipeBook) {
        this.visible = false;
        List<VineryRecipeResultCollection> list = recipeBook.getResultsForGroup(this.category);
        if (list != null) {

            for (VineryRecipeResultCollection recipeResultCollection : list) {
                if (recipeResultCollection.isInitialized() && recipeResultCollection.hasFittingRecipes()) {
                    this.visible = true;
                    break;
                }
            }
        }
        return this.visible;
    }
}
