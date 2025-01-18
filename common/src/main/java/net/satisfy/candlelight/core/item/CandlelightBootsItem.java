package net.satisfy.candlelight.core.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import org.jetbrains.annotations.NotNull;

public class CandlelightBootsItem extends ArmorItem {
    private final ResourceLocation bootsTexture;

    public CandlelightBootsItem(ArmorMaterial armorMaterial, Type type, Properties properties, ResourceLocation bootsTexture) {
        super(armorMaterial, type, properties);
        this.bootsTexture = bootsTexture;
    }

    public ResourceLocation getBootsTexture() {
        return bootsTexture;
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.FEET;
    }

}
