package net.satisfy.candlelight.item.armor;

import de.cristelknight.doapi.common.item.ICustomArmor;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;
import net.satisfy.candlelight.registry.ArmorRegistry;

import java.util.List;

public class CookingHatItem extends ArmorItem implements ICustomArmor {
    public CookingHatItem(Holder<ArmorMaterial> holder, Properties properties) {
        super(holder, Type.HELMET, properties.component(DataComponents.DYED_COLOR, new DyedItemColor(0xFFFFFFFF, false)));
    }

    @Override
    public Float getYOffset() {
        return -1.9f;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        ArmorRegistry.appendCookTooltip(list);
    }
}