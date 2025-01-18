package net.satisfy.candlelight.core.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ArmorMaterialRegistry {
    public static final ArmorMaterial COOK_ARMOR = new SimpleArmorMaterial(ArmorMaterials.LEATHER, 1, "cook");
    public static final ArmorMaterial RING_ARMOR = new SimpleArmorMaterial(ArmorMaterials.LEATHER, 0, "gold_ring", 15, Ingredient.of(new ItemStack(Items.GOLD_INGOT)));

    private static class SimpleArmorMaterial implements ArmorMaterial {
        private final ArmorMaterial base;
        private final int defense;
        private final String name;
        private final int enchantability;
        private final Ingredient repairIngredient;

        SimpleArmorMaterial(ArmorMaterial base, int defense, String name) {
            this(base, defense, name, base.getEnchantmentValue(), base.getRepairIngredient());
        }

        SimpleArmorMaterial(ArmorMaterial base, int defense, String name, int enchantability, Ingredient repairIngredient) {
            this.base = base;
            this.defense = defense;
            this.name = name;
            this.enchantability = enchantability;
            this.repairIngredient = repairIngredient;
        }

        public int getDurabilityForType(ArmorItem.Type type) {
            return base.getDurabilityForType(type);
        }

        public int getDefenseForType(ArmorItem.Type type) {
            return defense;
        }

        public int getEnchantmentValue() {
            return enchantability;
        }

        public @NotNull SoundEvent getEquipSound() {
            return base.getEquipSound();
        }

        public @NotNull Ingredient getRepairIngredient() {
            return repairIngredient;
        }

        public @NotNull String getName() {
            return name;
        }

        public float getToughness() {
            return base.getToughness();
        }

        public float getKnockbackResistance() {
            return base.getKnockbackResistance();
        }
    }
}
