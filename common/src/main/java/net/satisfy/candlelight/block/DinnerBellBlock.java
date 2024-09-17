package net.satisfy.candlelight.block;

import com.mojang.serialization.MapCodec;
import de.cristelknight.doapi.common.registry.DoApiSoundEventRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.satisfy.candlelight.entity.DinnerBellBlockEntity;
import net.satisfy.candlelight.registry.BlockEntityRegistry;

import java.util.List;

@SuppressWarnings("deprecation")
public class DinnerBellBlock extends BaseEntityBlock {
    public static final MapCodec<DinnerBellBlock> CODEC = simpleCodec(DinnerBellBlock::new);
    public DinnerBellBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @NotNull
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        VoxelShape bottom = Block.box(4.0, 0.0, 4.0, 12.0, 2.0, 12.0);
        VoxelShape top = Block.box(5.0, 2.0, 5.0, 11.0, 5.0, 11.0);
        return Shapes.or(bottom, top);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide) {
            level.playSound(null, pos, DoApiSoundEventRegistry.DINNER_BELL.get(), SoundSource.BLOCKS, 0.25f, 1.0f);
        }
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof DinnerBellBlockEntity dinnerBell) {
            dinnerBell.onHit();
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DinnerBellBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, BlockEntityRegistry.DINNER_BELL_BLOCK_ENTITY.get(), level.isClientSide ? DinnerBellBlockEntity::clientTick : null);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("tooltip.farm_and_charm.canbeplaced").withStyle(ChatFormatting.GRAY));
    }
}
