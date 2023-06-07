package satisfyu.candlelight.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SideTableBlock extends Block {
    public SideTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.6875, 0, 0.1875, 0.8125, 0.5, 0.3125));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0, 0.1875, 0.3125, 0.5, 0.3125));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0, 0.6875, 0.3125, 0.5, 0.8125));
        shape = Shapes.or(shape, Shapes.box(0.125, 0.5, 0.125, 0.875, 0.625, 0.875));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.125, 0.25, 0.75, 0.1875, 0.75));

        return shape;
    }
}
