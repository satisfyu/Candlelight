package net.satisfy.candlelight.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.satisfy.candlelight.item.IngredientItem;
import net.satisfy.candlelight.registry.RecipeTypes;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.util.VineryUtils;

public class CookingPanRecipe implements Recipe<Inventory> {

    final Identifier id;
    private final DefaultedList<Ingredient> inputs;
    private final ItemStack container;
    private final ItemStack output;

    public CookingPanRecipe(Identifier id, DefaultedList<Ingredient> inputs, ItemStack container, ItemStack output) {
        this.id = id;
        this.inputs = inputs;
        this.container = container;
        this.output = output;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return VineryUtils.matchesRecipe(inventory, inputs, 0, 6);
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return this.output.copy();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    public ItemStack getContainer() {
        return container;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeTypes.COOKING_PAN_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.COOKING_PAN_RECIPE_TYPE;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    /*
    public static void registerDefaults() {
        DefaultedList<Ingredient> dL = DefaultedList.of();
        dL.add(Ingredient.ofStacks(Items.DIAMOND.getDefaultStack()));
        registerCookingPanRecipe(new VineryIdentifier("test"), dL, Items.EMERALD.getDefaultStack(), Items.REDSTONE.getDefaultStack());
    }

    public static void registerCookingPanRecipe(Identifier id, DefaultedList<Ingredient> inputs, ItemStack container, ItemStack output) {
        new CookingPanRecipe(id, inputs, container, output);
    }
*/

    public static class Serializer implements RecipeSerializer<CookingPanRecipe> {

        @Override
        public CookingPanRecipe read(Identifier id, JsonObject json) {
            final var ingredients = VineryUtils.deserializeIngredients(JsonHelper.getArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for CookingPan Recipe");
            } else if (ingredients.size() > 6) {
                throw new JsonParseException("Too many ingredients for CookingPan Recipe");
            } else {
                return new CookingPanRecipe(id, ingredients,  ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "container")), ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result")));
            }
        }

        @Override
        public CookingPanRecipe read(Identifier id, PacketByteBuf buf) {
            final var ingredients  = DefaultedList.ofSize(buf.readVarInt(), Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromPacket(buf));
            return new CookingPanRecipe(id, ingredients, buf.readItemStack(), buf.readItemStack());
        }

        @Override
        public void write(PacketByteBuf buf, CookingPanRecipe recipe) {
            buf.writeVarInt(recipe.inputs.size());
            recipe.inputs.forEach(entry -> entry.write(buf));
            buf.writeItemStack(recipe.getContainer());
            buf.writeItemStack(recipe.getOutput());
        }
    }
    
}