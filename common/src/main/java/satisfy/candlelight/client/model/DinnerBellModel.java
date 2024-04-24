package satisfy.candlelight.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import satisfy.candlelight.Candlelight;

public class DinnerBellModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Candlelight.MOD_ID, "dinner_bell"), "main");
    private final ModelPart dinner_bell_base;
    private final ModelPart dinner_bell_button;

    public DinnerBellModel(ModelPart root) {
        this.dinner_bell_base = root.getChild("dinner_bell_base");
        this.dinner_bell_button = root.getChild("dinner_bell_button");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition dinner_bell_base = partdefinition.addOrReplaceChild("dinner_bell_base", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(4.0F, -24.0F, 4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 10).mirror().addBox(5.0F, -22.0F, 5.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition dinner_bell_button = partdefinition.addOrReplaceChild("dinner_bell_button", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(7.0F, -19.0F, 7.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        dinner_bell_base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        dinner_bell_button.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

}