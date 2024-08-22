package net.satisfy.candlelight.registry;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.satisfy.candlelight.util.CandlelightIdentifier;

import java.util.EnumMap;
import java.util.List;

public class ArmorMaterialRegistry {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create("candlelight", Registries.ARMOR_MATERIAL);

    public static final Holder<ArmorMaterial> COOK_ARMOR =
            ARMOR_MATERIALS.register("copper", () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1);
                        map.put(ArmorItem.Type.LEGGINGS, 1);
                        map.put(ArmorItem.Type.CHESTPLATE, 1);
                        map.put(ArmorItem.Type.HELMET, 1);
                        map.put(ArmorItem.Type.BODY, 1);
                    }),
                    0,
                    ArmorMaterials.LEATHER.value().equipSound(),
                    () -> Ingredient.of(ItemStack.EMPTY),
                    List.of(
                            new ArmorMaterial.Layer(
                                    CandlelightIdentifier.of("cook")
                            ),
                            new ArmorMaterial.Layer(
                                    CandlelightIdentifier.of("cook"), "_overlay", true
                            )
                    ),
                    ArmorMaterials.LEATHER.value().toughness(),
                    ArmorMaterials.LEATHER.value().knockbackResistance()
            ));

    public static final Holder<ArmorMaterial> RING_ARMOR =
            ARMOR_MATERIALS.register("gold_ring", () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 0);
                        map.put(ArmorItem.Type.LEGGINGS, 0);
                        map.put(ArmorItem.Type.CHESTPLATE, 0);
                        map.put(ArmorItem.Type.HELMET, 0);
                        map.put(ArmorItem.Type.BODY, 0);
                    }),
                    15,
                    ArmorMaterials.LEATHER.value().equipSound(),
                    () -> Ingredient.of(new ItemStack(Items.GOLD_INGOT)),
                    List.of(
                            new ArmorMaterial.Layer(
                                    ResourceLocation.withDefaultNamespace("gold_ring")
                            )
                    ),
                    ArmorMaterials.LEATHER.value().toughness(),
                    ArmorMaterials.LEATHER.value().knockbackResistance()
            ));

    public static void init() {
        ARMOR_MATERIALS.register();
    }
}
