package satisfyu.candlelight.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.recipe.CookingPanRecipe;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RecipeTypes {

    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Candlelight.MOD_ID, Registry.RECIPE_SERIALIZER_REGISTRY);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registry.RECIPE_TYPE_REGISTRY);

    public static final RegistrySupplier<RecipeType<CookingPanRecipe>> COOKING_PAN_RECIPE_TYPE = create("pan_cooking");
    public static final RegistrySupplier<RecipeSerializer<CookingPanRecipe>> COOKING_PAN_RECIPE_SERIALIZER = create("pan_cooking", CookingPanRecipe.Serializer::new);



    private static <T extends Recipe<?>> RegistrySupplier<RecipeSerializer<T>> create(String name, Supplier<RecipeSerializer<T>> serializer) {
        return RECIPE_SERIALIZERS.register(name, serializer);
    }

    private static <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> create(String name) {
        Supplier<RecipeType<T>> type = () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        return RECIPE_TYPES.register(name, type);
    }

    public static void init() {
        RECIPE_SERIALIZERS.register();
        RECIPE_TYPES.register();
    }


}