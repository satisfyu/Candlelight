package satisfy.candlelight.compat.jei.transfer;

import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import satisfy.candlelight.client.gui.handler.CookingPotGuiHandler;
import satisfy.candlelight.compat.jei.category.CookingPotCategory;
import satisfy.candlelight.recipe.CookingPotRecipe;
import satisfy.candlelight.registry.ScreenHandlerTypeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CookingPotTransferInfo implements IRecipeTransferInfo<CookingPotGuiHandler, CookingPotRecipe> {
    @Override
    public Class<? extends CookingPotGuiHandler> getContainerClass() {
        return CookingPotGuiHandler.class;
    }

    @Override
    public Optional<MenuType<CookingPotGuiHandler>> getMenuType() {
        return Optional.of(ScreenHandlerTypeRegistry.COOKING_POT_SCREEN_HANDLER.get());
    }

    @Override
    public RecipeType<CookingPotRecipe> getRecipeType() {
        return CookingPotCategory.COOKING_POT;
    }

    @Override
    public boolean canHandle(CookingPotGuiHandler container, CookingPotRecipe recipe) {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(CookingPotGuiHandler container, CookingPotRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        for(int i = 0; i < recipe.getIngredients().size(); i++){
            slots.add(container.getSlot(i));
        }
        slots.add(container.getSlot(6));
        return slots;
    }

    @Override
    public List<Slot> getInventorySlots(CookingPotGuiHandler container, CookingPotRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 8; i < 8 + 36; i++) {
            Slot slot = container.getSlot(i);
            slots.add(slot);
        }
        return slots;
    }
}
