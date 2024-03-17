package satisfy.candlelight.fabric.api;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

@SuppressWarnings("unused")
public class RingItem extends TrinketItem {
    public RingItem(Properties settings) {
        super(settings);
    }

    public Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);
        modifiers.put(Attributes.LUCK, new AttributeModifier(uuid, "candlelight:luck_addition", 2, AttributeModifier.Operation.ADDITION));
        SlotAttributes.addSlotModifier(modifiers, "hand/ring", uuid, 1, AttributeModifier.Operation.ADDITION);
        return modifiers;
    }
}