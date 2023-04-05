package net.satisfy.candlelight.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import satisfyu.vinery.block.entity.chair.ChairUtil;
import satisfyu.vinery.util.GeneralUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SofaBlock extends LineConnectingBlock {
    public static final Map<Direction, VoxelShape> SHAPE;
    public static final Map<Direction, VoxelShape> MIDDLE_SHAPE;
    public static final Map<Direction, VoxelShape> LEFT_SHAPE;
    public static final Map<Direction, VoxelShape> RIGHT_SHAPE;

    private static final Supplier<VoxelShape> noneShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.1875, 0, 0.9375, 0.4375, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0, 0.125, 0.875, 0.1875, 0.3125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0, 0.6875, 0.875, 0.1875, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.125, 0.3125, 0.1875, 0.3125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.6875, 0.3125, 0.1875, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.1875625, 0.0625, 0.125, 0.8125625, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.1875625, 0.0625, 1, 0.8125625, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.4375, 0.875, 0.9375, 1, 1));

        return shape;
    };

    private static final Supplier<VoxelShape> middleShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.1875, 0, 1, 0.4375, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.4375, 0.875, 1, 1, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.1875, 0, 0.0625, 0.4375, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.4375, 0.875, 0.0625, 1, 1));
        return shape;
    };

    private static final Supplier<VoxelShape> leftShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.1875, 0, 1, 0.4375, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.125, 0.3125, 0.1875, 0.3125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.6875, 0.3125, 0.1875, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.1875625, 0.0625, 0.125, 0.8125625, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.4375, 0.875, 1, 1, 1));

        return shape;
    };

    private static final Supplier<VoxelShape> rightShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.1875, 0, 0.9375, 0.4375, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0, 0.125, 0.875, 0.1875, 0.3125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0, 0.6875, 0.875, 0.1875, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.1875625, 0.0625, 1, 0.8125625, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.4375, 0.875, 0.9375, 1, 1));

        return shape;
    };

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Map<Direction, VoxelShape> voxelShape;
        switch (state.get(TYPE)) {
            case MIDDLE -> voxelShape = MIDDLE_SHAPE;
            case LEFT -> voxelShape = LEFT_SHAPE;
            case RIGHT -> voxelShape = RIGHT_SHAPE;
            default -> voxelShape = SHAPE;
        }
        return voxelShape.get(state.get(FACING));
    }


    public SofaBlock(AbstractBlock.Settings settings) {
        super(settings);
    }



    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return ChairUtil.onUse(world, player, hand, hit, 0);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        ChairUtil.onStateReplaced(world, pos);
    }
    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.candlelight.expandable.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    static {
        SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Type.HORIZONTAL.stream().toList()) {
                map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, noneShapeSupplier.get()));
            }
        });
        MIDDLE_SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Type.HORIZONTAL.stream().toList()) {
                map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, middleShapeSupplier.get()));
            }
        });
        LEFT_SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Type.HORIZONTAL.stream().toList()) {
                map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, leftShapeSupplier.get()));
            }
        });
        RIGHT_SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Type.HORIZONTAL.stream().toList()) {
                map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, rightShapeSupplier.get()));
            }
        });
    }
}
