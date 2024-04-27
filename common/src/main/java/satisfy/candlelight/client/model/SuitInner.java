package satisfy.candlelight.client.model;

import de.cristelknight.doapi.DoApiRL;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;


public class SuitInner<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new DoApiRL("suit_inner"), "main");

    public SuitInner(ModelPart root) {
        super(root);
    }

    //TODO
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition ModelPartData = meshdefinition.getRoot();

        ModelPartData.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        ModelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        ModelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 80).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        ModelPartData.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        ModelPartData.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
        ModelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        ModelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 96, 96);
    }
}