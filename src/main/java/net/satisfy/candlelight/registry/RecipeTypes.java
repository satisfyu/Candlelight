package net.satisfy.candlelight.registry;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.satisfy.candlelight.recipe.CookingPanRecipe;
import net.satisfy.candlelight.util.CandlelightIdentifier;

import java.util.HashMap;
import java.util.Map;

public class RecipeTypes {

    private static final Map<Identifier, RecipeSerializer<?>> RECIPE_SERIALIZERS = new HashMap<>();
    private static final Map<Identifier, RecipeType<?>> RECIPE_TYPES = new HashMap<>();

    public static final RecipeType<CookingPanRecipe> COOKING_PAN_RECIPE_TYPE = create("pan_cooking");
    public static final RecipeSerializer<CookingPanRecipe> COOKING_PAN_RECIPE_SERIALIZER = create("pan_cooking", new CookingPanRecipe.Serializer());


    private static <T extends Recipe<?>> RecipeSerializer<T> create(String name, RecipeSerializer<T> serializer) {
        RECIPE_SERIALIZERS.put(new CandlelightIdentifier(name), serializer);
        return serializer;
    }

    private static <T extends Recipe<?>> RecipeType<T> create(String name) {
        final RecipeType<T> type = new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        RECIPE_TYPES.put(new CandlelightIdentifier(name), type);
        return type;
    }

    public static void init() {
        for (Map.Entry<Identifier, RecipeSerializer<?>> entry : RECIPE_SERIALIZERS.entrySet()) {
            Registry.register(Registry.RECIPE_SERIALIZER, entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Identifier, RecipeType<?>> entry : RECIPE_TYPES.entrySet()) {
            Registry.register(Registry.RECIPE_TYPE, entry.getKey(), entry.getValue());
        }
    }


}
