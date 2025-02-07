package net.satisfy.candlelight.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.satisfy.candlelight.core.util.CandlelightIdentifier;

public class CookingHatModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new CandlelightIdentifier("cooking_hat"), "main");
    private final ModelPart cooking_hat;

    public CookingHatModel(ModelPart root) {
        this.cooking_hat = root.getChild("cooking_hat");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition cooking_hat = partdefinition.addOrReplaceChild("cooking_hat", CubeListBuilder.create().texOffs(1, 16).addBox(-4.0F, -9.75F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-5.0F, -15.75F, -5.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.75F, 0.0F));

        return LayerDefinition.create(meshdefinition, 48, 48);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.scale(1.05F, 1.05F, 1.05F);
        cooking_hat.render(poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public void setupAnim(T entity, float f, float g, float h, float i, float j) {

    }

    public void copyHead(ModelPart model) {
        cooking_hat.copyFrom(model);
    }
}
