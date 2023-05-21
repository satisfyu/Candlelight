package satisfyu.candlelight.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import satisfyu.candlelight.block.WineStationBlock;
import satisfyu.candlelight.block.entity.WineStationBlockEntity;
import satisfyu.candlelight.util.CandlelightTags;
import satisfyu.vinery.client.ClientUtil;

import java.util.List;


public class WineStationBlockEntityRenderer implements BlockEntityRenderer<WineStationBlockEntity> {

    public WineStationBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(WineStationBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (!entity.hasLevel()) {
            return;
        }
        BlockState selfState = entity.getBlockState();
        if (selfState.getBlock() instanceof WineStationBlock) {
            applyBlockAngle(matrices, selfState);
            matrices.translate(0f, 1f, 0f);
            matrices.scale(0.5f, 0.5f , 0.5f);
            List<ItemStack> wines = entity.getWine();
            matrices.pushPose();
            for (int i = 0; i < wines.size(); i++) {
                applyBlockPlace(matrices, i);
                if (wines.get(i).getItem() instanceof BlockItem && wines.get(i).is(CandlelightTags.WINE)) {
                    BlockState state = ((BlockItem) wines.get(i).getItem()).getBlock().defaultBlockState();
                    ClientUtil.renderBlock(state, matrices, vertexConsumers, entity);
                }
            }
            matrices.popPose();
        }
    }

    public static void applyBlockPlace(PoseStack matrices, int slot) {
        switch (slot) {
            case 0 -> {
                matrices.translate(.4f, 0f, 1f);
            }
            case 1 -> {
                matrices.translate(.6f, 0f, -.8f);
            }
            case 2 -> {
                matrices.translate(-1f, 0f, -.2f);
            }
        }
    }

    public static void applyBlockAngle(PoseStack matrices, BlockState state) {
        switch (state.getValue(WineStationBlock.FACING)) {
            case NORTH -> {
                matrices.mulPose(Vector3f.YP.rotationDegrees(180));
                matrices.translate(-1f, 0f, -1f);
            }
            case EAST -> {
                matrices.mulPose(Vector3f.YP.rotationDegrees(90));
                matrices.translate(-1f, 0f, 0f);
            }
            case WEST -> {
                matrices.mulPose(Vector3f.YP.rotationDegrees(270));
                matrices.translate(0f, 0f, -1f);
            }
        }
    }
}