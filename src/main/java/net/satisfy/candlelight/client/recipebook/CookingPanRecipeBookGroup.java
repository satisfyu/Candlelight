package net.satisfy.candlelight.client.recipebook;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.satisfy.candlelight.registry.ObjectRegistry;

import java.util.List;

@Environment(EnvType.CLIENT)
public enum CookingPanRecipeBookGroup implements IRecipeBookGroup {
    SEARCH(new ItemStack(Items.COMPASS)),
    WINE(new ItemStack(satisfyu.vinery.registry.ObjectRegistry.NOIR_WINE)),
    MISC(new ItemStack(ObjectRegistry.TRAY), new ItemStack(Items.SUGAR));

    public static final List<IRecipeBookGroup> PAN = ImmutableList.of(SEARCH, WINE, MISC);

    private final List<ItemStack> icons;

    CookingPanRecipeBookGroup(ItemStack... entries) {
        this.icons = ImmutableList.copyOf(entries);
    }

    public boolean fitRecipe(Recipe<?> recipe) {
        switch (this) {
            case SEARCH -> {
                return true;
            }
            case WINE -> {
                if (recipe.getIngredients().stream().anyMatch((ingredient) -> ingredient.test(satisfyu.vinery.registry.ObjectRegistry.NOIR_WINE.asItem().getDefaultStack()))) {
                    return true;
                }
            }
            case MISC -> {
                if (recipe.getIngredients().stream().noneMatch((ingredient) -> ingredient.test(satisfyu.vinery.registry.ObjectRegistry.NOIR_WINE.asItem().getDefaultStack()))) {
                    return true;
                }
            }
            default -> {
                return false;
            }
        }
        return false;
    }

    @Override
    public List<ItemStack> getIcons() {
        return this.icons;
    }

}
