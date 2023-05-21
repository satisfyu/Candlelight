package satisfyu.candlelight.client.recipebook.custom;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.vinery.client.recipebook.IRecipeBookGroup;

import java.util.List;

@Environment(EnvType.CLIENT)
public enum CookingPanRecipeBookGroup implements IRecipeBookGroup {
    SEARCH(new ItemStack(Items.COMPASS)),
    WINE(new ItemStack(satisfyu.vinery.registry.ObjectRegistry.NOIR_WINE.get())),
    MISC(new ItemStack(ObjectRegistry.TRAY.get()), new ItemStack(Items.SUGAR));

    public static final List<IRecipeBookGroup> PAN_GROUPS = ImmutableList.of(SEARCH, WINE, MISC);

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
                if (recipe.getIngredients().stream().anyMatch((ingredient) -> ingredient.test(satisfyu.vinery.registry.ObjectRegistry.NOIR_WINE.get().asItem().getDefaultInstance()))) {
                    return true;
                }
            }
            case MISC -> {
                if (recipe.getIngredients().stream().noneMatch((ingredient) -> ingredient.test(satisfyu.vinery.registry.ObjectRegistry.NOIR_WINE.get().asItem().getDefaultInstance()))) {
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