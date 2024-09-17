package net.satisfy.candlelight.event;

import de.cristelknight.doapi.common.registry.DoApiSoundEventRegistry;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import org.jetbrains.annotations.Nullable;
import net.satisfy.candlelight.registry.ObjectRegistry;

public class CommonEvents {
    private static final ResourceLocation ATTACK_SPEED_MODIFIER_ID = CandlelightIdentifier.of("attack_speed_modifier");

    public static void init() {
        PlayerEvent.ATTACK_ENTITY.register(CommonEvents::attack);
    }

    public static EventResult attack(Player player, Level level, Entity target, InteractionHand hand, @Nullable EntityHitResult result) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(ObjectRegistry.COOKING_PAN_ITEM.get())) {
            level.playSound(null, target.getX(), target.getY(), target.getZ(), DoApiSoundEventRegistry.COOKING_POT_HIT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            target.hurt(level.damageSources().generic(), 5.0F);
            itemStack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemStack));

            ItemAttributeModifiers attributeModifiers = itemStack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder().build());
            attributeModifiers.withModifierAdded(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER_ID, -2.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);

            itemStack.set(DataComponents.ATTRIBUTE_MODIFIERS, attributeModifiers);

            if (target instanceof Mob mob) {
                mob.setTarget(player);
            }

            return EventResult.interruptTrue();
        }
        return EventResult.pass();
    }
}
