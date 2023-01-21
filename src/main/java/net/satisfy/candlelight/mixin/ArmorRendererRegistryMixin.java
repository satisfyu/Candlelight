package net.satisfy.candlelight.mixin;

import daniking.vinery.registry.CustomArmorRegistry;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.satisfy.candlelight.client.model.CookingHatModel;
import net.satisfy.candlelight.registry.ObjectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(CustomArmorRegistry.class)
public class ArmorRendererRegistryMixin {
    @Inject(method = "registerArmor", at = @At("HEAD"))
    private static <T extends LivingEntity> void checkCannotConnect(Map<Item, EntityModel<T>> models, EntityModelLoader modelLoader, CallbackInfo ci) {
        models.put(ObjectRegistry.COOKING_HAT, new CookingHatModel<>(modelLoader.getModelPart(CookingHatModel.LAYER_LOCATION)));
    }
}