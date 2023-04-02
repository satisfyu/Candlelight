package net.satisfy.candlelight.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.screen.slot.Slot;
import net.satisfy.candlelight.client.recipebook.VineryRecipeBookWidget;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public abstract class AbstractCookingRecipeBookScreen extends VineryRecipeBookWidget {

    public AbstractCookingRecipeBookScreen() {
    }

    @Override
    protected void setBookButtonTexture() {
        this.toggleCraftableButton.setTextureUV(152, 41, 28, 18, TEXTURE); //TODO toggle Craftable textur
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.id < this.craftingScreenHandler.getCraftingSlotCount()) {
            this.ghostSlots.reset();
        }
    }
}
