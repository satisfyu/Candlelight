package satisfyu.candlelight.block;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.vinery.util.GeneralUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JewelryBoxBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty RING = BooleanProperty.create("ring");

    public JewelryBoxBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(RING, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) return InteractionResult.SUCCESS;
        final ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            world.setBlock(pos, state.setValue(OPEN, !state.getValue(OPEN)), Block.UPDATE_ALL);
        } else if (stack.getItem() == ObjectRegistry.GOLD_RING && !state.getValue(RING) && state.getValue(OPEN)) {
            world.setBlock(pos, state.setValue(RING, true), Block.UPDATE_ALL);
            if (!player.isCreative()) stack.shrink(1);
            return InteractionResult.SUCCESS;
        } else if (state.getValue(RING) && state.getValue(OPEN)) {
            world.setBlock(pos, state.setValue(RING, false), Block.UPDATE_ALL);
            player.addItem(new ItemStack(ObjectRegistry.GOLD_RING.get()));
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }


    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, RING);
    }

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.125, 0.6875));
        shape = Shapes.or(shape, Shapes.box(0.625, 0.125, 0.3125, 0.6875, 0.1875, 0.6875));
        shape = Shapes.or(shape, Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.125, 0.6875));
        shape = Shapes.or(shape, Shapes.box(0.375, 0.125, 0.3125, 0.625, 0.1875, 0.375));
        shape = Shapes.or(shape, Shapes.box(0.375, 0.125, 0.3125, 0.625, 0.1875, 0.375));

        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }



    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.candlelight.canbeplaced.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        tooltip.add(Component.translatable("block.candlelight.jewelry_box.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}
