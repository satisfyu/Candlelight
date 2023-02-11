package net.satisfy.candlelight.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import satisfyu.vinery.VineryIdentifier;

public class CookingHatModel<T extends Entity> extends EntityModel<T> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new VineryIdentifier("cooking_hat"), "main");
	private final ModelPart cookhat;
	public CookingHatModel(ModelPart root) {
		this.cookhat = root.getChild("cookhat");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData cookhat = modelPartData.addChild("cookhat", ModelPartBuilder.create(), ModelTransform.pivot(7.875F, 23.0F, -7.875F));

		ModelPartData top_part = cookhat.addChild("top_part", ModelPartBuilder.create().uv(1, 1).cuboid(-5.0F, -10.0F, -5.0F, 10.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.875F, 0.0F, 7.875F));

		ModelPartData bottom_part = cookhat.addChild("bottom_part", ModelPartBuilder.create().uv(1, 21).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F))
				.uv(4, 2).cuboid(-4.5F, -0.9F, -4.5F, 9.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.875F, 0.0F, 7.875F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		cookhat.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}