package net.satisfy.candlelight.fabric.api;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("unused")
public class RingItem extends TrinketItem {
    public RingItem(Properties settings) {
        super(settings);
    }

    public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, ResourceLocation resourceLocation) {
        var modifiers = super.getModifiers(stack, slot, entity, resourceLocation);
        modifiers.put(Attributes.LUCK, new AttributeModifier(resourceLocation, 2, AttributeModifier.Operation.ADD_VALUE));
        SlotAttributes.addSlotModifier(modifiers, "hand/ring", resourceLocation, 1, AttributeModifier.Operation.ADD_VALUE);
        return modifiers;
    }
}