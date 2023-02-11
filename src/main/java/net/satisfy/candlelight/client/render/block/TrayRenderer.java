package net.satisfy.candlelight.client.render.block;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3f;
import satisfyu.vinery.block.entity.StorageBlockEntity;
import satisfyu.vinery.client.render.block.StorageTypeRenderer;
import satisfyu.vinery.util.ClientUtil;

public class TrayRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks) {
        matrices.translate(0, 0.3, -0.25);
        matrices.scale(0.5f, 0.5f, 0.5f);
        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if(!stack.isEmpty()) {
                matrices.push();

                if (stack.getItem() instanceof BlockItem blockItem) {
                    matrices.translate(-0.5f, -0.5f, 0f);
                    ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                } else {
                    matrices.translate(0f, 0f, 0.2f * i);
                    matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(22.5f));
                    ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
                }
                matrices.pop();
            }
        }
    }
}
