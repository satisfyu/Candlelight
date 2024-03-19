package satisfy.candlelight.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cristelknight.doapi.client.render.block.storage.StorageTypeRenderer;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import satisfy.candlelight.client.ClientUtil;

@Environment(EnvType.CLIENT)
public class JewelryRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        if (!itemStacks.isEmpty()) {
            ItemStack stack = itemStacks.get(0);
            if (!stack.isEmpty()) {
                matrices.pushPose();
                matrices.translate(-0.01, 0.15, 0);
                matrices.scale(0.3f, 0.3f, 0.3f);
                ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
                matrices.popPose();
            }
        }
    }
}
