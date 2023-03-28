package net.satisfy.candlelight.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.satisfy.candlelight.item.EffectFoodHelper.getEffects;

public interface EffectFood {
    default void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        List<Pair<StatusEffectInstance, Float>> effects = getEffects(stack);
        if (effects.isEmpty()) {
            tooltip.add(Text.translatable("effect.none").formatted(Formatting.GRAY));
        } else {
            for (Pair<StatusEffectInstance, Float> statusEffectInstance : effects) {
                MutableText mutableText = Text.translatable(statusEffectInstance.getFirst().getTranslationKey());
                StatusEffect statusEffect = statusEffectInstance.getFirst().getEffectType();

                if (statusEffectInstance.getFirst().getDuration() > 20) {
                    mutableText = Text.translatable(
                            "potion.withDuration",
                            mutableText, StatusEffectUtil.durationToString(statusEffectInstance.getFirst(), statusEffectInstance.getSecond()));
                }

                tooltip.add(mutableText.formatted(statusEffect.getCategory().getFormatting()));
            }
        }
    }
}
