package net.satisfy.candlelight.block;

import daniking.vinery.block.StorageBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.registry.StorageTypes;

public class CakeStandBlock extends StorageBlock {

    public static VoxelShape SHAPE = makeShape();

    public static VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.5000625, 0.125, 0.875, 1.0000625, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 1, 0.4375, 0.5625, 1.0625, 0.5625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.1875, 0.4375, 0.5625, 0.5, 0.5625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.4375, 0.25, 0.75, 0.5, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.34375, 0.0625, 0.34375, 0.65625, 0.1875, 0.65625));

        return shape;
    }

    public CakeStandBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public Identifier type() {
        return StorageTypes.CAKE_STAND;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN};
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.isFood();
    }

    @Override
    public int getSection(Float x, Float y) {
        Candlelight.LOGGER.error(x);
        if (x < 0.375F) {
            return 0;
        } else {
            return x < 0.6875F ? 1 : 2;
        }
    }
}
