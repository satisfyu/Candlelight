package net.satisfy.candlelight.fabric.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.core.item.DyeableCandlelightArmorItem;
import net.satisfy.candlelight.core.registry.ArmorRegistry;
import net.satisfy.candlelight.core.util.CandlelightIdentifier;

public class DyeableCandlelightChestplateRenderer implements ArmorRenderer {
    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (!(stack.getItem() instanceof DyeableCandlelightArmorItem armorItem)) return;
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("Visible") && !tag.getBoolean("Visible")) return;
        Model model = ArmorRegistry.getDressModel(armorItem, contextModel.body, contextModel.leftArm, contextModel.rightArm, contextModel.leftLeg, contextModel.rightLeg);
        int color = armorItem.getColor(stack);
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        model.renderToBuffer(matrices, vertexConsumers.getBuffer(model.renderType(armorItem.getTexture())), light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
        ResourceLocation overlayTexture = new CandlelightIdentifier("textures/models/armor/dress_overlay.png");
        model.renderToBuffer(matrices, vertexConsumers.getBuffer(model.renderType(overlayTexture)), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
