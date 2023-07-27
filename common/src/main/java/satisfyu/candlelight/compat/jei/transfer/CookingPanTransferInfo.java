package satisfyu.candlelight.compat.jei.transfer;

import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import satisfyu.candlelight.client.gui.handler.CookingPanGuiHandler;
import satisfyu.candlelight.client.gui.handler.CookingPanGuiHandler;
import satisfyu.candlelight.compat.jei.category.CookingPanCategory;
import satisfyu.candlelight.compat.jei.category.CookingPotCategory;
import satisfyu.candlelight.recipe.CookingPanRecipe;
import satisfyu.candlelight.recipe.CookingPanRecipe;
import satisfyu.candlelight.registry.ScreenHandlerTypeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CookingPanTransferInfo implements IRecipeTransferInfo<CookingPanGuiHandler, CookingPanRecipe> {
    @Override
    public Class<? extends CookingPanGuiHandler> getContainerClass() {
        return CookingPanGuiHandler.class;
    }

    @Override
    public Optional<MenuType<CookingPanGuiHandler>> getMenuType() {
        return Optional.of(ScreenHandlerTypeRegistry.COOKING_PAN_SCREEN_HANDLER.get());
    }

    @Override
    public RecipeType<CookingPanRecipe> getRecipeType() {
        return CookingPanCategory.COOKING_PAN;
    }

    @Override
    public boolean canHandle(CookingPanGuiHandler container, CookingPanRecipe recipe) {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(CookingPanGuiHandler container, CookingPanRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        for(int i = 0; i < recipe.getIngredients().size(); i++){
            slots.add(container.getSlot(i));
        }
        slots.add(container.getSlot(6));
        return slots;
    }

    @Override
    public List<Slot> getInventorySlots(CookingPanGuiHandler container, CookingPanRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 8; i < 8 + 36; i++) {
            Slot slot = container.getSlot(i);
            slots.add(slot);
        }
        return slots;
    }
}
