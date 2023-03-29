package net.satisfy.candlelight.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.FoodComponent;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import satisfyu.vinery.util.VineryUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EffectFoodTrayBlock extends EffectFoodBlock {

    public EffectFoodTrayBlock(Settings settings, int maxBites, FoodComponent foodComponent) {
        super(settings, maxBites, foodComponent);
    }

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0, 0.15625, 0.75, 0.0625, 0.84375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.0625, 0.125, 0.78125, 0.1875, 0.875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.0625, 0.125, 0.28125, 0.1875, 0.875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.0625, 0.8125, 0.71875, 0.1875, 0.875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.0625, 0.125, 0.71875, 0.1875, 0.1875), BooleanBiFunction.OR);
        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Type.HORIZONTAL.stream().toList()) {
            map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE.get(state.get(FACING));
    }
}
