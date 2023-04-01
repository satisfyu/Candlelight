package net.satisfy.candlelight.client.recipebook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.satisfy.candlelight.registry.ObjectRegistry;

import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public enum VineryRecipeBookGroup {
    SEARCH(new ItemStack(Items.COMPASS)),
    WINE(new ItemStack(satisfyu.vinery.registry.ObjectRegistry.NOIR_WINE)),
    MISC(new ItemStack(ObjectRegistry.TRAY), new ItemStack(Items.APPLE)),
    UNKNOWN(new ItemStack(Items.BARRIER));

    public static final List<VineryRecipeBookGroup> PAN = ImmutableList.of(SEARCH, WINE, MISC);
    public static final Map<VineryRecipeBookGroup, List<VineryRecipeBookGroup>> SEARCH_MAP = ImmutableMap.of(SEARCH, ImmutableList.of(WINE, MISC));
    private final List<ItemStack> icons;

    VineryRecipeBookGroup(ItemStack... entries) {
        this.icons = ImmutableList.copyOf(entries);
    }

    public static List<VineryRecipeBookGroup> getGroups(VineryRecipeBookCategory category) {
        switch (category) {
            case PAN:
                return PAN;
            default:
                return ImmutableList.of();
        }
    }

    public List<ItemStack> getIcons() {
        return this.icons;
    }

}
