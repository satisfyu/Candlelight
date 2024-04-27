package satisfy.candlelight.registry;

import de.cristelknight.doapi.client.render.feature.CustomArmorManager;
import de.cristelknight.doapi.client.render.feature.CustomArmorSet;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import satisfy.candlelight.client.CandlelightClient;
import satisfy.candlelight.client.model.CookOuter;
import satisfy.candlelight.client.model.CookInner;
import satisfy.candlelight.client.model.CookingHatModel;
import satisfy.candlelight.config.CandlelightConfig;
import satisfy.candlelight.item.armor.*;
import satisfy.candlelight.util.CandlelightIdentifier;

import java.util.List;
import java.util.Map;

public class ArmorRegistry {
    public static void registerArmorModelLayers(){
        EntityModelLayerRegistry.register(CookingHatModel.LAYER_LOCATION, CookingHatModel::getTexturedModelData);
        EntityModelLayerRegistry.register(CookInner.LAYER_LOCATION, CookInner::createBodyLayer);
        EntityModelLayerRegistry.register(CookOuter.LAYER_LOCATION, CookOuter::createBodyLayer);
    }


    public static <T extends LivingEntity> void registerArmorModels(CustomArmorManager<T> armors, EntityModelSet modelLoader) {
        armors.addArmor(new CustomArmorSet<T>(ObjectRegistry.COOKING_HAT.get(), ObjectRegistry.CHEFS_JACKET.get(), ObjectRegistry.CHEFS_PANTS.get(), ObjectRegistry.CHEFS_BOOTS.get())
                .setTexture(new CandlelightIdentifier("cook"))
                .setOuterModel(new CookOuter<>(modelLoader.bakeLayer(CookOuter.LAYER_LOCATION)))
                .setInnerModel(new CookInner<>(modelLoader.bakeLayer(CookInner.LAYER_LOCATION)))
                .setHatModel(new CookingHatModel<>(modelLoader.bakeLayer(CookingHatModel.LAYER_LOCATION))));

    }

        public static  <T extends LivingEntity> void registerHatModels(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        models.put(ObjectRegistry.COOKING_HAT.get(), new CookingHatModel<>(modelLoader.bakeLayer(CookingHatModel.LAYER_LOCATION)));
    }

    @SuppressWarnings("all")
    public static void appendCookTooltip(List<Component> tooltip) {
        CandlelightConfig config = CandlelightConfig.getActiveInstance();

        if (!config.enableChefSetBonus()) {
            return;
        }
        Player player = CandlelightClient.getClientPlayer();
        if (player == null) {
            return;
        }

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        boolean hasCompleteSet = helmet != null && helmet.getItem() instanceof CookingHatItem &&
                chestplate != null && chestplate.getItem() instanceof CookChestplateItem &&
                leggings != null && leggings.getItem() instanceof CookLeggingsItem &&
                boots != null && boots.getItem() instanceof CookBootsItem;

        tooltip.add(Component.nullToEmpty(""));
        tooltip.add(Component.nullToEmpty(ChatFormatting.AQUA + I18n.get("tooltip.candlelight.cook_armor.")));
        tooltip.add(Component.nullToEmpty((helmet != null && helmet.getItem() instanceof CookingHatItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.COOKING_HAT.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((chestplate != null && chestplate.getItem() instanceof CookChestplateItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.CHEFS_JACKET.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((leggings != null && leggings.getItem() instanceof CookLeggingsItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.CHEFS_PANTS.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((boots != null && boots.getItem() instanceof CookBootsItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.CHEFS_BOOTS.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty(""));

        tooltip.add(Component.nullToEmpty(ChatFormatting.GRAY + I18n.get("tooltip.candlelight.cook_armor.2")));
        tooltip.add(Component.nullToEmpty((hasCompleteSet ? ChatFormatting.DARK_GREEN.toString() : ChatFormatting.GRAY.toString()) + I18n.get("tooltip.candlelight.cook_armor.3")));
        if (hasCompleteSet) {
            player.addEffect(new MobEffectInstance(MobEffects.LUCK, 200, 1));
        }
    }

    @SuppressWarnings("all")
    public static void appendSuitTooltip(List<Component> tooltip) {
        CandlelightConfig config = CandlelightConfig.getActiveInstance();

        if (!config.enableChefSetBonus()) {
            return;
        }
        Player player = CandlelightClient.getClientPlayer();
        if (player == null) {
            return;
        }

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);

        boolean hasCompleteSet = helmet != null && helmet.getItem() instanceof SuitNecktieItem &&
                chestplate != null && chestplate.getItem() instanceof SuitShirtChestplateItem &&
                leggings != null && leggings.getItem() instanceof SuitLeggingsItem &&

        tooltip.add(Component.nullToEmpty(""));
        tooltip.add(Component.nullToEmpty(ChatFormatting.AQUA + I18n.get("tooltip.candlelight.suit_armor.")));
        tooltip.add(Component.nullToEmpty((helmet != null && helmet.getItem() instanceof SuitNecktieItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.NECKTIE.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((chestplate != null && chestplate.getItem() instanceof SuitShirtChestplateItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.SHIRT.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((leggings != null && leggings.getItem() instanceof SuitLeggingsItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.SUIT.get().getDescription().getString() + "]"));
    }

    @SuppressWarnings("all")
    public static void appendDressTooltip(List<Component> tooltip) {
        CandlelightConfig config = CandlelightConfig.getActiveInstance();

        if (!config.enableChefSetBonus()) {
            return;
        }
        Player player = CandlelightClient.getClientPlayer();
        if (player == null) {
            return;
        }

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);

        boolean hasCompleteSet = helmet != null && helmet.getItem() instanceof DressHelmetItem &&
                chestplate != null && chestplate.getItem() instanceof DressChestplateItem &&

                tooltip.add(Component.nullToEmpty(""));
        tooltip.add(Component.nullToEmpty(ChatFormatting.AQUA + I18n.get("tooltip.candlelight.dress_armor.")));
        tooltip.add(Component.nullToEmpty((helmet != null && helmet.getItem() instanceof DressHelmetItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.FLOWER_CROWN.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((chestplate != null && chestplate.getItem() instanceof DressChestplateItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.DRESS.get().getDescription().getString() + "]"));
    }
}