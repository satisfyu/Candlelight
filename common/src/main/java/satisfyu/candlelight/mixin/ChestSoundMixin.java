package satisfyu.candlelight.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.candlelight.block.entity.SideBoardBlockEntity;

@Mixin(ChestBlockEntity.class)
public class ChestSoundMixin {



    @Inject(method = "playSound", at = @At("HEAD"), cancellable = true)
    private static void updateBookContent(Level world, BlockPos pos, BlockState state, SoundEvent soundEvent, CallbackInfo ci) {
        if(world.getBlockEntity(pos) instanceof SideBoardBlockEntity){
            soundEvent = SoundEvents.BASALT_HIT;
            ci.cancel();
        }
        else return;
        ChestType chestType = state.getValue(ChestBlock.TYPE);
        if (chestType == ChestType.LEFT) {
            return;
        }
        double d = (double)pos.getX() + 0.5;
        double e = (double)pos.getY() + 0.5;
        double f = (double)pos.getZ() + 0.5;
        if (chestType == ChestType.RIGHT) {
            Direction direction = ChestBlock.getConnectedDirection(state);
            d += (double)direction.getStepX() * 0.5;
            f += (double)direction.getStepZ() * 0.5;
        }
        world.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5f, world.random.nextFloat() * 0.1f + 0.9f);
    }
}