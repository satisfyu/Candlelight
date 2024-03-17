package satisfy.candlelight.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import satisfy.candlelight.client.CandlelightClient;
import satisfy.candlelight.registry.ObjectRegistry;

import java.util.List;

public interface CookArmorItem {
	default void tooltip(List<Component> tooltip){
		Player player = CandlelightClient.getClientPlayer();
		if (player == null) return;
		ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
		ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
		ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
		ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

		boolean hasCompleteSet = helmet != null && helmet.getItem() instanceof CookArmorItem &&
				chestplate != null && chestplate.getItem() instanceof CookArmorItem &&
				leggings != null && leggings.getItem() instanceof CookArmorItem &&
				boots != null && boots.getItem() instanceof CookArmorItem;

		tooltip.add(Component.nullToEmpty(""));
		tooltip.add(Component.nullToEmpty(ChatFormatting.AQUA + I18n.get("candlelight.tooltip.cook_armor")));
		tooltip.add(Component.nullToEmpty((helmet != null && helmet.getItem() instanceof CookArmorItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.COOKING_HAT.get().getDescription().getString() + "]"));
		tooltip.add(Component.nullToEmpty((chestplate != null && chestplate.getItem() instanceof CookArmorItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.CHEFS_JACKET.get().getDescription().getString() + "]"));
		tooltip.add(Component.nullToEmpty((leggings != null && leggings.getItem() instanceof CookArmorItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.CHEFS_PANTS.get().getDescription().getString() + "]"));
		tooltip.add(Component.nullToEmpty((boots != null && boots.getItem() instanceof CookArmorItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.CHEFS_BOOTS.get().getDescription().getString() + "]"));
		tooltip.add(Component.nullToEmpty(""));

		tooltip.add(Component.nullToEmpty(ChatFormatting.GRAY + I18n.get("candlelight.tooltip.cook_armor2")));
		tooltip.add(Component.nullToEmpty((hasCompleteSet ? ChatFormatting.DARK_GREEN.toString() : ChatFormatting.GRAY.toString()) + I18n.get("candlelight.tooltip.cook_armor3")));

		if (hasCompleteSet) {
			player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 1));
		}
	}
}