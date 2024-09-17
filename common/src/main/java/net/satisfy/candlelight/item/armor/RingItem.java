package net.satisfy.candlelight.item.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.satisfy.candlelight.registry.ArmorMaterialRegistry;

import java.util.List;

public class RingItem extends ArmorItem {
    public RingItem(Holder<ArmorMaterial> material, Type type, Properties settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide()) {
            if (entity instanceof Player player) {
                this.checkForSet(player);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void checkForSet(Player player) {
        if (this.hasRing(player)) {
            this.addStatusEffect(player, new MobEffectInstance(MobEffects.LUCK, 14 * 20, 1));
        }
    }

    private boolean hasRing(Player player) {
        if (player.getInventory().getArmor(2).isEmpty()) return false;
        Item item = player.getInventory().getArmor(2).getItem();
        if (item instanceof ArmorItem armorItem) {
            return armorItem == ArmorMaterialRegistry.RING_ARMOR;
        }
        return false;
    }

    private void addStatusEffect(Player player, MobEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.hasEffect(mapStatusEffect.getEffect());
        MobEffectInstance effect = player.getEffect(mapStatusEffect.getEffect());
        if (!hasPlayerEffect || effect != null && effect.getDuration() < 11 * 20) {
            player.addEffect(new MobEffectInstance(mapStatusEffect.getEffect(),
                    mapStatusEffect.getDuration(), mapStatusEffect.getAmplifier(), true, false, true));
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("tooltip.candlelight.ring").withStyle(ChatFormatting.GREEN));
    }
}
