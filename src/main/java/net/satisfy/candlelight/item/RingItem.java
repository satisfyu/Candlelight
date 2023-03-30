package net.satisfy.candlelight.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RingItem extends ArmorItem {
    public RingItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(  "item.candlelight.ring1.tooltip").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable(  "item.candlelight.ring2.tooltip").formatted(Formatting.WHITE));
        tooltip.add(Text.translatable(  "item.candlelight.ring3.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));

    }
}
