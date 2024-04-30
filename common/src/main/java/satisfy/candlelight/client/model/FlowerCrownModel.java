package satisfy.candlelight.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import satisfy.candlelight.util.CandlelightIdentifier;

@Environment(EnvType.CLIENT)
public class FlowerCrownModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new CandlelightIdentifier("flower_crown"), "main");
	private final ModelPart flower_crown;

	public FlowerCrownModel(ModelPart root) {
		this.flower_crown = root.getChild("flower_crown");
	}

	@SuppressWarnings("unused")
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition ModelPartData = meshdefinition.getRoot();
		PartDefinition flower_crown = ModelPartData.addOrReplaceChild("flower_crown", CubeListBuilder.create().texOffs(0, 29).addBox(-4.0F, -33.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition,  64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		this.flower_crown.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}