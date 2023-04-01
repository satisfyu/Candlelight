package net.satisfy.candlelight.client.recipebook;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.book.RecipeBookOptions;
import net.minecraft.util.Util;

import java.util.Map;

public class VineryRecipeBookOptions {
    private static final Map<VineryRecipeBookCategory, Pair<String, String>> CATEGORY_OPTION_NAMES;
    private final Map<VineryRecipeBookCategory, VineryRecipeBookOptions.CategoryOption> categoryOptions;

    private VineryRecipeBookOptions(Map<VineryRecipeBookCategory, VineryRecipeBookOptions.CategoryOption> categoryOptions) {
        this.categoryOptions = categoryOptions;
    }

    public VineryRecipeBookOptions() {
        this(Util.make(Maps.newEnumMap(VineryRecipeBookCategory.class), (categoryOptions) -> {
            VineryRecipeBookCategory[] var1 = VineryRecipeBookCategory.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                VineryRecipeBookCategory recipeBookCategory = var1[var3];
                categoryOptions.put(recipeBookCategory, new VineryRecipeBookOptions.CategoryOption(false, false));
            }

        }));
    }

    public boolean isGuiOpen(VineryRecipeBookCategory category) {
        return (this.categoryOptions.get(category)).guiOpen;
    }

    public void setGuiOpen(VineryRecipeBookCategory category, boolean open) {
        (this.categoryOptions.get(category)).guiOpen = open;
    }

    public boolean isFilteringCraftable(VineryRecipeBookCategory category) {
        return (this.categoryOptions.get(category)).filteringCraftable;
    }

    public void setFilteringCraftable(VineryRecipeBookCategory category, boolean filtering) {
        (this.categoryOptions.get(category)).filteringCraftable = filtering;
    }

    public static VineryRecipeBookOptions fromPacket(PacketByteBuf buf) {
        Map<VineryRecipeBookCategory, VineryRecipeBookOptions.CategoryOption> map = Maps.newEnumMap(VineryRecipeBookCategory.class);
        VineryRecipeBookCategory[] var2 = VineryRecipeBookCategory.values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            VineryRecipeBookCategory recipeBookCategory = var2[var4];
            boolean bl = buf.readBoolean();
            boolean bl2 = buf.readBoolean();
            map.put(recipeBookCategory, new VineryRecipeBookOptions.CategoryOption(bl, bl2));
        }

        return new VineryRecipeBookOptions(map);
    }

    public void toPacket(PacketByteBuf buf) {
        VineryRecipeBookCategory[] var2 = VineryRecipeBookCategory.values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            VineryRecipeBookCategory recipeBookCategory = var2[var4];
            VineryRecipeBookOptions.CategoryOption categoryOption = this.categoryOptions.get(recipeBookCategory);
            if (categoryOption == null) {
                buf.writeBoolean(false);
                buf.writeBoolean(false);
            } else {
                buf.writeBoolean(categoryOption.guiOpen);
                buf.writeBoolean(categoryOption.filteringCraftable);
            }
        }

    }

    public static VineryRecipeBookOptions fromNbt(NbtCompound nbt) {
        Map<VineryRecipeBookCategory, VineryRecipeBookOptions.CategoryOption> map = Maps.newEnumMap(VineryRecipeBookCategory.class);
        CATEGORY_OPTION_NAMES.forEach((category, pair) -> {
            boolean bl = nbt.getBoolean((String)pair.getFirst());
            boolean bl2 = nbt.getBoolean((String)pair.getSecond());
            map.put(category, new VineryRecipeBookOptions.CategoryOption(bl, bl2));
        });
        return new VineryRecipeBookOptions(map);
    }

    public void writeNbt(NbtCompound nbt) {
        CATEGORY_OPTION_NAMES.forEach((category, pair) -> {
            VineryRecipeBookOptions.CategoryOption categoryOption = (VineryRecipeBookOptions.CategoryOption)this.categoryOptions.get(category);
            nbt.putBoolean((String)pair.getFirst(), categoryOption.guiOpen);
            nbt.putBoolean((String)pair.getSecond(), categoryOption.filteringCraftable);
        });
    }

    public VineryRecipeBookOptions copy() {
        Map<VineryRecipeBookCategory, VineryRecipeBookOptions.CategoryOption> map = Maps.newEnumMap(VineryRecipeBookCategory.class);
        VineryRecipeBookCategory[] var2 = VineryRecipeBookCategory.values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            VineryRecipeBookCategory recipeBookCategory = var2[var4];
            VineryRecipeBookOptions.CategoryOption categoryOption = this.categoryOptions.get(recipeBookCategory);
            map.put(recipeBookCategory, categoryOption.copy());
        }

        return new VineryRecipeBookOptions(map);
    }

    public void copyFrom(VineryRecipeBookOptions other) {
        this.categoryOptions.clear();
        VineryRecipeBookCategory[] var2 = VineryRecipeBookCategory.values();
        int var3 = var2.length;

        for (VineryRecipeBookCategory recipeBookCategory : var2) {
            CategoryOption categoryOption = other.categoryOptions.get(recipeBookCategory);
            this.categoryOptions.put(recipeBookCategory, categoryOption.copy());
        }

    }

    public boolean equals(Object o) {
        return this == o || o instanceof RecipeBookOptions && this.categoryOptions.equals(((VineryRecipeBookOptions)o).categoryOptions);
    }

    public int hashCode() {
        return this.categoryOptions.hashCode();
    }

    static {
        CATEGORY_OPTION_NAMES = ImmutableMap.of(VineryRecipeBookCategory.PAN, Pair.of("isGuiOpen", "isFilteringCraftable"));
    }

    private static final class CategoryOption {
        boolean guiOpen;
        boolean filteringCraftable;

        public CategoryOption(boolean guiOpen, boolean filteringCraftable) {
            this.guiOpen = guiOpen;
            this.filteringCraftable = filteringCraftable;
        }

        public VineryRecipeBookOptions.CategoryOption copy() {
            return new VineryRecipeBookOptions.CategoryOption(this.guiOpen, this.filteringCraftable);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof VineryRecipeBookOptions.CategoryOption)) {
                return false;
            } else {
                VineryRecipeBookOptions.CategoryOption categoryOption = (VineryRecipeBookOptions.CategoryOption)o;
                return this.guiOpen == categoryOption.guiOpen && this.filteringCraftable == categoryOption.filteringCraftable;
            }
        }

        public int hashCode() {
            int i = this.guiOpen ? 1 : 0;
            i = 31 * i + (this.filteringCraftable ? 1 : 0);
            return i;
        }

        public String toString() {
            return "[open=" + this.guiOpen + ", filtering=" + this.filteringCraftable + "]";
        }
    }
}
