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

public class CakeStandRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks) {
        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if(!stack.isEmpty()) {
                matrices.push();

                if (stack.getItem() instanceof BlockItem blockItem) {
                    matrices.scale(0.65f, 0.65f, 0.65f);
                    matrices.translate(-0.5f, 0.8f, -0.5);
                    ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                } else {
                    matrices.scale(0.4f, 0.4f, 0.4f);
                    if(i == 0) matrices.translate(-0.4f, 1.3f, 0.4f);
                    else if(i == 1) matrices.translate(-0.2f, 1.3f, -0.4f);
                    else matrices.translate(0.4f, 1.3f, 0.2f);
                    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90f));
                    ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
                }
                matrices.pop();
            }
        }
    }
}
