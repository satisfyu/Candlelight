package satisfyu.candlelight.client.recipebook.custom;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import satisfyu.candlelight.client.recipebook.IRecipeBookGroup;

import java.util.List;

@Environment(EnvType.CLIENT)
public enum CookingPotRecipeBookGroup implements IRecipeBookGroup {
    SEARCH(new ItemStack(Items.COMPASS)),
    //TODO not wine and other icon
    WINE(new ItemStack(Blocks.BARRIER.asItem())),
    MISC(new ItemStack(Items.BOWL), new ItemStack(Items.SUGAR));

    public static final List<IRecipeBookGroup> POT_GROUPS = ImmutableList.of(SEARCH, WINE, MISC);

    private final List<ItemStack> icons;

    CookingPotRecipeBookGroup(ItemStack... entries) {
        this.icons = ImmutableList.copyOf(entries);
    }

    //TODO how to fit
    public boolean fitRecipe(Recipe<?> recipe) {
        switch (this) {
            case SEARCH -> {
                return true;
            }
            case WINE -> {
                if (recipe.getIngredients().stream().anyMatch((ingredient) -> ingredient.test(Blocks.BARRIER.asItem().getDefaultInstance()))) {
                    return true;
                }
            }
            case MISC -> {
                if (recipe.getIngredients().stream().noneMatch((ingredient) -> ingredient.test(Blocks.BARRIER.asItem().getDefaultInstance()))) {
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
