package net.satisfy.candlelight.block.crops;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class WildBroccoli extends Block {
    public WildBroccoli(Settings settings) {
        super(settings);
    }

    public static VoxelShape SHAPE = makeShape();

    public static VoxelShape makeShape() {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.25, 0.6875));
        return shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
