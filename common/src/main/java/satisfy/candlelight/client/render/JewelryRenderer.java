package satisfy.candlelight.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.cristelknight.doapi.client.ClientUtil;
import de.cristelknight.doapi.client.render.block.storage.StorageTypeRenderer;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import satisfy.candlelight.util.TypewriterUtil;

@Environment(EnvType.CLIENT)
public class JewelryRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        if (!itemStacks.isEmpty()) {
            ItemStack stack = itemStacks.get(0);
            if (!stack.isEmpty()) {
                matrices.pushPose();
                matrices.mulPose(Axis.ZN.rotationDegrees(45f));
                matrices.translate(-0.15, 0.15, 0);
                matrices.scale(0.25f, 0.25f, 0.25f);
                ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
                matrices.popPose();
            }
        }
    }
}
