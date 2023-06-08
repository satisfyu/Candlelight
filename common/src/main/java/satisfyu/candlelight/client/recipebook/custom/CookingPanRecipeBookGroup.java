package satisfyu.candlelight.client.recipebook.custom;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import satisfyu.candlelight.block.EffectFoodBlock;
import satisfyu.candlelight.client.recipebook.IRecipeBookGroup;
import satisfyu.candlelight.item.food.EffectFoodItem;
import satisfyu.candlelight.registry.ObjectRegistry;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public enum CookingPanRecipeBookGroup implements IRecipeBookGroup {
    SEARCH(new ItemStack(Items.COMPASS)),
    MISC(new ItemStack(ObjectRegistry.DOUGH.get())),
    EFFECT(new ItemStack(Items.POTION)),
    BIG(new ItemStack(ObjectRegistry.BEEF_WELLINGTON.get()));

    public static final List<IRecipeBookGroup> PAN_GROUPS = ImmutableList.of(SEARCH, MISC, EFFECT, BIG);

    private final List<ItemStack> icons;

    CookingPanRecipeBookGroup(ItemStack... entries) {
        this.icons = ImmutableList.copyOf(entries);
    }

    public boolean fitRecipe(Recipe<?> recipe) {
        switch (this) {
            case SEARCH -> {
                return true;
            }
            case MISC -> {
                if (recipe.getIngredients().stream().noneMatch((ingredient) -> ingredient.test(Items.POTION.getDefaultInstance())) && recipe.getIngredients().stream().noneMatch((ingredient) -> Arrays.stream(ingredient.getItems()).anyMatch(itemStack -> itemStack.getItem() instanceof EffectFoodItem))) {
                    return true;
                }
            }
            case EFFECT -> {
                if (recipe.getIngredients().stream().anyMatch((ingredient) -> ingredient.test(Items.POTION.getDefaultInstance()))) {
                    return true;
                }
            }
            case BIG -> {
                if (recipe.getIngredients().stream().anyMatch((ingredient) -> Arrays.stream(ingredient.getItems()).anyMatch(itemStack -> itemStack.getItem() instanceof EffectFoodItem))) {
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
