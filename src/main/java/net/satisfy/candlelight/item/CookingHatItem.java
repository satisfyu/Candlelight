package net.satisfy.candlelight.item;

import daniking.vinery.item.CustomModelArmorItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.util.Identifier;
import net.satisfy.candlelight.util.CandlelightIdentifier;

public class CookingHatItem extends CustomModelArmorItem {
    public CookingHatItem(Settings settings) {
        super(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, settings);
    }

    @Override
    public Identifier getTexture() {
        return new CandlelightIdentifier("textures/item/cooking_hat.png");
    }

    @Override
    public Float getOffset() {
        return -1.8f;
    }
}
