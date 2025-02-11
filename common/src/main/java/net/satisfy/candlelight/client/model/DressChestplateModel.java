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
import org.jetbrains.annotations.NotNull;

public class DressChestplateModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new CandlelightIdentifier("dress"), "main");
    private final ModelPart body;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart right_leg;
    private final ModelPart left_leg;

    public DressChestplateModel(ModelPart root) {
        this.body = root.getChild("body");
        this.left_arm = root.getChild("left_arm");
        this.right_arm = root.getChild("right_arm");
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(3, 36).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.325F)).texOffs(16, 16).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(10, 32).addBox(-2.5F, -1.0F, -2.5F, 5.0F, 12.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 37).mirror().addBox(-2.5F, -1.0F, -2.5F, 5.0F, 12.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -3.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -3.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        body.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        right_arm.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        left_arm.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        right_leg.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        left_leg.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }

    @Override
    public void setupAnim(@NotNull T entity, float f, float g, float h, float i, float j) {
    }

    @SuppressWarnings("unused")
    public void copyBody(ModelPart baseBody, ModelPart leftArm, ModelPart rightArm, ModelPart leftLeg, ModelPart rightLeg) {
        this.body.copyFrom(baseBody);
        this.left_arm.copyFrom(leftArm);
        this.right_arm.copyFrom(rightArm);
        this.left_leg.copyFrom(leftLeg);
        this.right_leg.copyFrom(rightLeg);
    }
}

