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

public class TieModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new CandlelightIdentifier("tie"), "main");
    private final ModelPart body;

    public TieModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 2)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.225F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.scale(1.04F, 1.04F, 1.04F);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public void setupAnim(T entity, float f, float g, float h, float i, float j) {

    }

    @SuppressWarnings("unused")
    public void copyHead(ModelPart model, ModelPart baseBody) {
        this.body.copyFrom(baseBody);
    }
}
