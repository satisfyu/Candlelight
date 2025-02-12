package net.satisfy.candlelight.forge.core.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class DyedArmorModelWrapper<T extends LivingEntity> extends EntityModel<T> {
    private final EntityModel<T> original;
    private final int color;

    public DyedArmorModelWrapper(EntityModel<T> original, int color) {
        super(RenderType::entityCutoutNoCull);
        this.original = original;
        this.color = color;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        float r = ((color >> 16) & 255) / 255.0F;
        float g = ((color >> 8) & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        original.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, r, g, b, alpha);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        original.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }
}
