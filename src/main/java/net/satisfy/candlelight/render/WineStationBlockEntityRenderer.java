package net.satisfy.candlelight.render;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import net.satisfy.candlelight.block.WineStationBlock;
import net.satisfy.candlelight.block.entity.WineStationBlockEntity;
import net.satisfy.candlelight.util.CandlelightTags;

import java.util.List;

import static satisfyu.vinery.util.ClientUtil.renderBlock;

public class WineStationBlockEntityRenderer implements BlockEntityRenderer<WineStationBlockEntity> {

    public WineStationBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

    }

    @Override
    public void render(WineStationBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.hasWorld()) {
            return;
        }
        BlockState selfState = entity.getCachedState();
        if (selfState.getBlock() instanceof WineStationBlock) {
            applyBlockAngle(matrices, selfState);
            matrices.translate(0f, 1f, 0f);
            matrices.scale(0.5f, 0.5f , 0.5f);
            List<ItemStack> wines = entity.getWine();
            matrices.push();
            for (int i = 0; i < wines.size(); i++) {
                applyBlockPlace(matrices, i);
                if (wines.get(i).getItem() instanceof BlockItem && wines.get(i).isIn(CandlelightTags.WINE)) {
                    BlockState state = ((BlockItem) wines.get(i).getItem()).getBlock().getDefaultState();
                    renderBlock(state, matrices, vertexConsumers, entity);
                }
            }
            matrices.pop();
        }
    }

    public static void applyBlockPlace(MatrixStack matrices, int slot) {
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

    public static void applyBlockAngle(MatrixStack matrices, BlockState state) {
        switch (state.get(WineStationBlock.FACING)) {
            case NORTH -> {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
                matrices.translate(-1f, 0f, -1f);
            }
            case EAST -> {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
                matrices.translate(-1f, 0f, 0f);
            }
            case WEST -> {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270));
                matrices.translate(0f, 0f, -1f);
            }
        }
    }
}