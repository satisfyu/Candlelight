package net.satisfy.candlelight.client.render.block;

import daniking.vinery.block.entity.StorageBlockEntity;
import daniking.vinery.client.render.block.StorageTypeRenderer;
import daniking.vinery.util.ClientUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3f;

public class CakeStandRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks) {


        matrices.translate(-0.4, 0.5, 0.25);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90f));
        matrices.scale(0.5f, 0.5f, 0.5f);


        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if(!stack.isEmpty()) {
                matrices.push();
                matrices.translate(0f, 0f, 0.2f * i);
                if (stack.getItem() instanceof BlockItem blockItem) {
                    ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                } else {
                    ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
                }
                matrices.pop();
            }
        }
    }
}
