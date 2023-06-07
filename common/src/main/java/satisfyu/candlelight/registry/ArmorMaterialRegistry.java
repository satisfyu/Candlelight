package satisfyu.candlelight.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class ArmorMaterialRegistry {

    public static final ArmorMaterial COOK_ARMOR = new ArmorMaterial() {
        @Override
        public int getDurabilityForSlot(EquipmentSlot slot) {
            return ArmorMaterials.LEATHER.getDurabilityForSlot(slot);
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot slot) {
            return ArmorMaterials.LEATHER.getDefenseForSlot(slot);
        }

        @Override
        public int getEnchantmentValue() {
            return ArmorMaterials.LEATHER.getEnchantmentValue();
        }

        @Override
        public SoundEvent getEquipSound() {
            return ArmorMaterials.LEATHER.getEquipSound();
        }

        @Override
        public Ingredient getRepairIngredient() {
            return ArmorMaterials.LEATHER.getRepairIngredient();
        }

        @Override
        public String getName() {
            return "cook";
        }

        @Override
        public float getToughness() {
            return ArmorMaterials.LEATHER.getToughness();
        }

        @Override
        public float getKnockbackResistance() {
            return ArmorMaterials.LEATHER.getKnockbackResistance();
        }
    };

    public static final ArmorMaterial RING_ARMOR = new ArmorMaterial() {
        private static final int[] BASE_DURABILITY = new int[] {1, 1, 1, 1};
        private static final int[] PROTECTION_VALUES = new int[] {1, 1, 1, 1};

        @Override
        public int getDurabilityForSlot(EquipmentSlot slot) {
            return BASE_DURABILITY[slot.getIndex()] * 1;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot slot) {
            return PROTECTION_VALUES[slot.getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_GOLD;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(Items.GOLD_INGOT);
        }

        @Override
        public String getName() {
            return "gold_ring";
        }

        @Override
        public float getToughness() {
            return 0.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
    };
}
