package net.satisfy.candlelight.core.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DyeableCandlelightArmorItem extends DyeableArmorItem {
    private final ResourceLocation getTexture;
    private final int defaultColor;

    public DyeableCandlelightArmorItem(ArmorMaterial armorMaterial, Type type, int color, Properties properties, ResourceLocation getTexture) {
        super(armorMaterial, type, properties);
        this.defaultColor = color;
        this.getTexture = getTexture;
    }

    @Override
    public int getColor(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTagElement("display");
        return (compoundTag != null && compoundTag.contains("color", 99)) ? compoundTag.getInt("color") : this.defaultColor;
    }

    public ResourceLocation getTexture() {
        return getTexture;
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return this.type.getSlot();
    }
}
