package net.satisfy.candlelight.forge.core.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.satisfy.candlelight.core.item.DyeableCandlelightArmorItem;
import net.satisfy.candlelight.core.registry.ArmorRegistry;
import net.satisfy.candlelight.forge.core.model.DyedArmorModelWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import java.util.function.Consumer;

@Mixin(DyeableCandlelightArmorItem.class)
public abstract class DyeableArmorMixin extends DyeableArmorItem {

    @Shadow
    public abstract int getColor(ItemStack stack);

    @Shadow
    private ResourceLocation getTexture;

    private DyeableArmorMixin(Type type, Properties properties) {
        super(null, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Model getGenericArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
                Model baseModel;
                if (slot == EquipmentSlot.LEGS) {
                    baseModel = ArmorRegistry.getSuitModel(stack.getItem(), original.rightLeg, original.leftLeg);
                } else if (slot == EquipmentSlot.CHEST) {
                    baseModel = ArmorRegistry.getDressModel(stack.getItem(), original.body, original.leftArm, original.rightArm, original.leftLeg, original.rightLeg);
                } else {
                    baseModel = original;
                }
                int dyeColor = getColor(stack);
                return new DyedArmorModelWrapper<>((EntityModel<LivingEntity>) baseModel, dyeColor);
            }
        });
    }

    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return getTexture.toString();
    }
}
