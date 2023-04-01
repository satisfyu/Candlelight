package net.satisfy.candlelight.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.satisfy.candlelight.registry.ObjectRegistry;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.util.GeneralUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JewelryBoxBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final BooleanProperty RING = BooleanProperty.of("ring");

    public JewelryBoxBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(OPEN, false).with(RING, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        final ItemStack stack = player.getStackInHand(hand);
        if (player.isSneaking()) {
            world.setBlockState(pos, state.with(OPEN, !state.get(OPEN)), Block.NOTIFY_ALL);
        } else if (stack.getItem() == ObjectRegistry.GOLD_RING && !state.get(RING) && state.get(OPEN)) {
            world.setBlockState(pos, state.with(RING, true), Block.NOTIFY_ALL);
            if (!player.isCreative()) stack.decrement(1);
            return ActionResult.SUCCESS;
        } else if (state.get(RING) && state.get(OPEN)) {
            world.setBlockState(pos, state.with(RING, false), Block.NOTIFY_ALL);
            player.giveItemStack(new ItemStack(ObjectRegistry.GOLD_RING));
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }


    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, RING);
    }

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.125, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.125, 0.3125, 0.6875, 0.1875, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.125, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.125, 0.3125, 0.625, 0.1875, 0.375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.125, 0.3125, 0.625, 0.1875, 0.375));

        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Type.HORIZONTAL.stream().toList()) {
            map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE.get(state.get(FACING));
    }



    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.candlelight.canbeplaced.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
        tooltip.add(Text.translatable("block.candlelight.jewelry_box.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
    }
}
