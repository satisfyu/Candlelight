package satisfy.candlelight.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import satisfy.candlelight.client.model.DinnerBellModel;
import satisfy.candlelight.entity.DinnerBellBlockEntity;

public class DinnerBellRenderer implements BlockEntityRenderer<DinnerBellBlockEntity> {
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
        float yOffset = blockEntity.getYOffset();
        poseStack.translate(0.0f, yOffset, 0.0f);
        dinner_bell_button.render(poseStack, bufferSource.getBuffer(RenderType.solid()), combinedLight, combinedOverlay);
        poseStack.popPose();
        dinner_bell_base.render(poseStack, bufferSource.getBuffer(RenderType.solid()), combinedLight, combinedOverlay);
    }
}


