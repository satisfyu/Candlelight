package satisfyu.candlelight.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class LanternBlock extends net.minecraft.world.level.block.LanternBlock {
    public static final BooleanProperty LUMINANCE = BooleanProperty.create("luminance");
    protected static final VoxelShape HANGING_SHAPE = makeShapeHS();
    protected static final VoxelShape AABB = makeShapeSS();

    public static VoxelShape makeShapeHS() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3125, 0.875, 0.3125, 0.6875, 1, 0.6875), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.4375, 0.625, 0.4375, 0.5625, 0.875, 0.5625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.1875, 0.125, 0.1875, 0.8125, 0.625, 0.8125), BooleanOp.OR);
        return shape;
    }



    public static VoxelShape makeShapeSS() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.1875, 0.5, 0.1875, 0.8125, 1, 0.8125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.4375, 0.3125, 0.4375, 0.5625, 0.9375, 0.5625), BooleanOp.OR);
        return shape;
    }

    public LanternBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(HANGING, false).setValue(WATERLOGGED, false).setValue(LUMINANCE, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_AABB : AABB;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LUMINANCE);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.getItemInHand(hand).isEmpty()) {
            if (!world.isClientSide()) {
                BlockState newState = state.setValue(LUMINANCE, !state.getValue(LUMINANCE));
                world.setBlockAndUpdate(pos, newState);
                world.playSound(null, pos, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(world.isClientSide());
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.candlelight.lamp.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}
