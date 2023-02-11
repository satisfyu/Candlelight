package net.satisfy.candlelight.item;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.satisfy.candlelight.registry.ObjectRegistry;
import satisfyu.vinery.client.ClientSetup;

import java.util.List;

public interface CookArmorItem {

	default void tooltip(List<Text> tooltip){
		PlayerEntity player = ClientSetup.getClientPlayer();
		if (player == null) return;
		ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
		ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
		ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
		ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
		tooltip.add(Text.of(""));
		tooltip.add(Text.of(Formatting.AQUA + I18n.translate("candlelight.tooltip.cook_armor")));
		tooltip.add(Text.of((helmet != null && helmet.getItem() instanceof CookArmorItem ? Formatting.GREEN.toString() : Formatting.GRAY.toString()) + "- [" + ObjectRegistry.COOKING_HAT.getName().getString() + "]"));
		tooltip.add(Text.of((chestplate != null && chestplate.getItem() instanceof CookArmorItem ? Formatting.GREEN.toString() : Formatting.GRAY.toString()) + "- [" + ObjectRegistry.CHEFS_JACKET.getName().getString() + "]"));
		tooltip.add(Text.of((leggings != null && leggings.getItem() instanceof CookArmorItem ? Formatting.GREEN.toString() : Formatting.GRAY.toString()) + "- [" + ObjectRegistry.CHEFS_PANTS.getName().getString() + "]"));
		tooltip.add(Text.of((boots != null && boots.getItem() instanceof CookArmorItem ? Formatting.GREEN.toString() : Formatting.GRAY.toString()) + "- [" + ObjectRegistry.CHEFS_BOOTS.getName().getString() + "]"));
		tooltip.add(Text.of(""));
		tooltip.add(Text.of(Formatting.GRAY + I18n.translate("candlelight.tooltip.cook_armor2")));
		tooltip.add(Text.of(((helmet != null && helmet.getItem() instanceof CookArmorItem &&
				chestplate != null && chestplate.getItem() instanceof CookArmorItem &&
				leggings != null && leggings.getItem() instanceof CookArmorItem &&
				boots != null && boots.getItem() instanceof CookArmorItem) ? Formatting.DARK_GREEN.toString() : Formatting.GRAY.toString()) + I18n.translate("candlelight.tooltip.cook_armor3")));
	}

}