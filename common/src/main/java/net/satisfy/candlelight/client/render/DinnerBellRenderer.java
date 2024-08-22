package net.satisfy.candlelight.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.model.DinnerBellModel;
import net.satisfy.candlelight.entity.DinnerBellBlockEntity;

public class DinnerBellRenderer implements BlockEntityRenderer<DinnerBellBlockEntity> {
    private static final ResourceLocation BELL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Candlelight.MOD_ID, "textures/entity/dinner_bell.png");

    private final ModelPart dinner_bell_base;
    private final ModelPart dinner_bell_button;

    public DinnerBellRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(DinnerBellModel.LAYER_LOCATION);
        this.dinner_bell_base = root.getChild("dinner_bell_base");
        this.dinner_bell_button = root.getChild("dinner_bell_button");
    }


    @Override
    public void render(DinnerBellBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        poseStack.pushPose();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(BELL_TEXTURE));
        dinner_bell_base.render(poseStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY);
        float yOffset = blockEntity.getYOffset();
        poseStack.translate(0.0f, yOffset, 0.0f);
        dinner_bell_button.render(poseStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
