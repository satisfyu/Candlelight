package net.satisfy.candlelight.fabric.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.core.item.CandlelightHatItem;
import net.satisfy.candlelight.core.registry.ArmorRegistry;

public class CandlelightHatRenderer implements ArmorRenderer {
    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (!(stack.getItem() instanceof CandlelightHatItem hat)) return;

        Model hatModel = ArmorRegistry.getHatModel(hat, contextModel.getHead());
        if (hatModel != null) {
            hatModel.renderToBuffer(matrices, vertexConsumers.getBuffer(hatModel.renderType(hat.getHatTexture())), light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
        }
        Model crownModel = ArmorRegistry.getCrownModel(hat, contextModel.getHead());
        if (crownModel != null) {
            crownModel.renderToBuffer(matrices, vertexConsumers.getBuffer(crownModel.renderType(hat.getHatTexture())), light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
        }
        Model tieModel = ArmorRegistry.getTieModel(hat, contextModel.getHead(), contextModel.body);
        if (tieModel != null) {
            tieModel.renderToBuffer(matrices, vertexConsumers.getBuffer(tieModel.renderType(hat.getHatTexture())), light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
        }
    }
}
