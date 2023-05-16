package satisfyu.candlelight.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RingItem extends ArmorItem {
    public RingItem(ArmorMaterial material, EquipmentSlot slot, Properties settings) {
        super(material, slot, settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable(  "item.candlelight.ring1.tooltip").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.translatable(  "item.candlelight.ring2.tooltip").withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.translatable(  "item.candlelight.ring3.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));

    }
}
