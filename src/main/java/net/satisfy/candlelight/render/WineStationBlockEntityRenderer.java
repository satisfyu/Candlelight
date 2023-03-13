package net.satisfy.candlelight.render;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
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
            List<ItemStack> wines = entity.getWine();
            matrices.push();
            for (ItemStack wineItem : wines) {
                if (wineItem.getItem() instanceof BlockItem && wineItem.isIn(CandlelightTags.WINE)) {
                    BlockState state = ((BlockItem) wineItem.getItem()).getBlock().getDefaultState();
                    matrices.translate(0f, 1f, 0f);
                    renderBlock(state, matrices, vertexConsumers, entity);
                }
            }

        }
        matrices.pop();
    }
}