package satisfyu.candlelight.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import de.cristelknight.doapi.client.render.block.storage.StorageTypeRenderer;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import satisfyu.candlelight.client.ClientUtil;
import satisfyu.candlelight.registry.ObjectRegistry;

@Environment(EnvType.CLIENT)
public class ToolRackRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        for (int i = 0; i < Math.min(itemStacks.size(), 4); i++) {
            ItemStack stack = itemStacks.get(i);

            if (!stack.isEmpty()) {
                Item item = stack.getItem();

                matrices.pushPose();
                if (item instanceof BlockItem blockItem && blockItem.getBlock() == ObjectRegistry.COOKING_PAN.get()) {
                    matrices.translate(0f, 0.8f, 0.45f);
                    matrices.scale(0.6f, 0.6f, 0.6f);
                    matrices.mulPose(Vector3f.ZN.rotationDegrees(-85f));
                    matrices.mulPose(Vector3f.XN.rotationDegrees(90f));
                    matrices.mulPose(Vector3f.YN.rotationDegrees(180f));
                    ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                } else {
                    matrices.translate(0f, 0.6f, 0.38f);
                    matrices.scale(0.6f, 0.6f, 0.6f);
                    matrices.mulPose(Vector3f.ZN.rotationDegrees(135f));
                    matrices.mulPose(Vector3f.YN.rotationDegrees(0f));
                    ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
                }
                matrices.popPose();
            }
        }
    }
}