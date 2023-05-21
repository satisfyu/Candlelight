package satisfyu.candlelight.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import satisfyu.candlelight.block.entity.TypeWriterEntity;
import satisfyu.candlelight.client.screen.NoteEditScreen;
import satisfyu.candlelight.client.screen.NotePaperScreen;
import satisfyu.candlelight.client.screen.SignedPaperScreen;
import satisfyu.candlelight.client.screen.TypeWriterScreen;

public class ClientUtil {

    public static void setNoteEditScreen(Player user, ItemStack stack, InteractionHand hand){
        Minecraft.getInstance().setScreen(new NoteEditScreen(user, stack, hand));
    }

    public static void setNotePaperScreen(Player user, ItemStack stack, InteractionHand hand){
        Minecraft.getInstance().setScreen(new NotePaperScreen(user, stack, hand));
    }


    public static void setTypeWriterScreen(Player user, TypeWriterEntity typeWriterEntity, InteractionHand hand, BlockPos pos){
        if(user instanceof LocalPlayer)
            Minecraft.getInstance().setScreen(new TypeWriterScreen(user, typeWriterEntity.getPaper(), hand, pos));
    }

    public static void setSignedPaperScreen(ItemStack stack){
        Minecraft.getInstance().setScreen(new SignedPaperScreen(new SignedPaperScreen.WrittenBookContents(stack)));
    }

    public static <T extends BlockEntity> void renderBlock(BlockState state, PoseStack matrices, MultiBufferSource vertexConsumers, T entity){
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrices, vertexConsumers, satisfyu.vinery.client.ClientUtil.getLightLevel(entity.getLevel(), entity.getBlockPos()), OverlayTexture.NO_OVERLAY);
    }



}
