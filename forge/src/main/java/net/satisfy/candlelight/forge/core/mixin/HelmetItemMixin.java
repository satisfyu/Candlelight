package net.satisfy.candlelight.forge.core.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.satisfy.candlelight.core.item.CandlelightHatItem;
import net.satisfy.candlelight.core.registry.ArmorRegistry;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Consumer;

@Mixin(CandlelightHatItem.class)
public abstract class HelmetItemMixin extends ArmorItem {
    @Shadow
    @Final
    private ResourceLocation hatTexture;

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                Item item = itemStack.getItem();
                if (item == ObjectRegistry.NECKTIE.get()) {
                    return ArmorRegistry.getTieModel(item, original.getHead(), original.body);
                } else if (item == ObjectRegistry.FLOWER_CROWN.get()) {
                    return ArmorRegistry.getCrownModel(item, original.getHead());
                }
                return ArmorRegistry.getHatModel(item, original.getHead());
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return hatTexture.toString();
    }

    private HelmetItemMixin(ArmorMaterial armorMaterial, Type armorType, Properties itemProperties) {
        super(armorMaterial, armorType, itemProperties);
    }
}

