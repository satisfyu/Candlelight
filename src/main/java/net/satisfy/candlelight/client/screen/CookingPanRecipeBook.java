package net.satisfy.candlelight.client.screen;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

import java.util.Set;

public class CookingPanRecipeBook extends AbstractCookingRecipeBookScreen {
    private static final Text TOGGLE_SMELTABLE_RECIPES_TEXT = Text.translatable("gui.recipebook.toggleRecipes.smeltable");

    public CookingPanRecipeBook() {
    }

    @Override
    protected Text getToggleCraftableButtonText() {
        return TOGGLE_SMELTABLE_RECIPES_TEXT;
    }

    @Override
    protected Set<Item> getAllowedFuels() {
        return AbstractFurnaceBlockEntity.createFuelTimeMap().keySet();
    }
}
