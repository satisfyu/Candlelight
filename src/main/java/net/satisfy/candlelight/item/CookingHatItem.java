package net.satisfy.candlelight.item;

import daniking.vinery.item.CustomModelArmorItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CookingHatItem extends CustomModelArmorItem implements CookArmorItem{
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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip(tooltip);
    }
}
