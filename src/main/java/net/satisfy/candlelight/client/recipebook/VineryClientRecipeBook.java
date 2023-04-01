package net.satisfy.candlelight.client.recipebook;

import com.google.common.collect.*;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.registry.Registry;
import net.satisfy.candlelight.client.screen.VineryRecipeResultCollection;
import net.satisfy.candlelight.registry.RecipeTypes;
import org.slf4j.Logger;

import java.util.*;
import java.util.stream.Stream;
@Environment(EnvType.CLIENT)
public class VineryClientRecipeBook extends VineryRecipeBook {
    private static final Logger LOGGER = LogUtils.getLogger();
    private Map<VineryRecipeBookGroup, List<VineryRecipeResultCollection>> resultsByGroup = ImmutableMap.of();
    private List<VineryRecipeResultCollection> orderedResults = ImmutableList.of();

    public <T extends Recipe<?>> void reload(List<T> recipes) {
        Map<VineryRecipeBookGroup, List<List<Recipe<?>>>> map = toGroupedMap(recipes);
        Map<VineryRecipeBookGroup, List<VineryRecipeResultCollection>> map2 = Maps.newHashMap();
        ImmutableList.Builder<VineryRecipeResultCollection> builder = ImmutableList.builder();
        map.forEach((group, recipesx) -> {
            Stream<VineryRecipeResultCollection> var10002 = recipesx.stream().map(VineryRecipeResultCollection::new);
            Objects.requireNonNull(builder);
            map2.put(group, var10002.peek(builder::add).collect(ImmutableList.toImmutableList()));
        });
        VineryRecipeBookGroup.SEARCH_MAP.forEach((group, searchGroups) ->
                map2.put(group, searchGroups.stream().flatMap((searchGroup) -> ( map2.getOrDefault(searchGroup, ImmutableList.of())).stream())
                        .collect(ImmutableList.toImmutableList()))
        );
        System.out.println("resultGroup" + resultsByGroup);
        this.resultsByGroup = ImmutableMap.copyOf(map2);
        this.orderedResults = builder.build();
    }

    private static <T extends Recipe<?>> Map<VineryRecipeBookGroup, List<List<Recipe<?>>>> toGroupedMap(List<T> recipes) {
        Map<VineryRecipeBookGroup, List<List<Recipe<?>>>> map = Maps.newHashMap();
        Table<VineryRecipeBookGroup, String, List<Recipe<?>>> table = HashBasedTable.create();

        for (Recipe<?> value : recipes) {
            if (!value.isIgnoredInRecipeBook() && !value.isEmpty()) {
                VineryRecipeBookGroup VineryRecipeBookGroup = getGroupForRecipe(value);
                String string = value.getGroup();
                if (string.isEmpty()) {
                    (map.computeIfAbsent(VineryRecipeBookGroup, (group) -> Lists.newArrayList()
                    )).add(ImmutableList.of(value));
                } else {
                    List<Recipe<?>> list = table.get(VineryRecipeBookGroup, string);
                    if (list == null) {
                        list = Lists.newArrayList();
                        table.put(VineryRecipeBookGroup, string, list);
                        (map.computeIfAbsent(VineryRecipeBookGroup, (group) -> Lists.newArrayList()
                        )).add(list);
                    }

                    (list).add(value);
                }
            }
        }
        System.out.println("map" + map);
        return map;
    }

    private static VineryRecipeBookGroup getGroupForRecipe(Recipe<?> recipe) {
        System.out.println("getGroupForRecipe");
        RecipeType<?> recipeType = recipe.getType();
        if (recipeType == RecipeTypes.COOKING_PAN_RECIPE_TYPE) {
            System.out.println("YAAY");
            ItemStack itemStack = recipe.getOutput();
            ItemGroup itemGroup = itemStack.getItem().getGroup();
            if (itemGroup == ItemGroup.BUILDING_BLOCKS) {
                return VineryRecipeBookGroup.WINE;
            } else {
                return VineryRecipeBookGroup.MISC;
            }
        } else {
            Logger var10000 = LOGGER;
            Object var10002 = LogUtils.defer(() -> Registry.RECIPE_TYPE.getId(recipe.getType()));
            Objects.requireNonNull(recipe);
            var10000.warn("Unknown recipe category: {}/{}", var10002, LogUtils.defer(recipe::getId));
            return VineryRecipeBookGroup.UNKNOWN;
        }
    }

    public List<VineryRecipeResultCollection> getOrderedResults() {
        return this.orderedResults;
    }

    public List<VineryRecipeResultCollection> getResultsForGroup(VineryRecipeBookGroup category) {
        return this.resultsByGroup.getOrDefault(category, Collections.emptyList());
    }
}
