package satisfy.candlelight.block;

import de.cristelknight.doapi.common.block.LineConnectingBlock;
import de.cristelknight.doapi.common.util.ChairUtil;
import de.cristelknight.doapi.common.util.GeneralUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class SofaBlock extends LineConnectingBlock {
    public static final Map<Direction, VoxelShape> SHAPE;
    public static final Map<Direction, VoxelShape> MIDDLE_SHAPE;
    public static final Map<Direction, VoxelShape> LEFT_SHAPE;
    public static final Map<Direction, VoxelShape> RIGHT_SHAPE;

    private static final Supplier<VoxelShape> noneShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.1875, 0, 0.9375, 0.4375, 1));
        shape = Shapes.or(shape, Shapes.box(0.6875, 0, 0.125, 0.875, 0.1875, 0.3125));
        shape = Shapes.or(shape, Shapes.box(0.6875, 0, 0.6875, 0.875, 0.1875, 0.875));
        shape = Shapes.or(shape, Shapes.box(0.125, 0, 0.125, 0.3125, 0.1875, 0.3125));
        shape = Shapes.or(shape, Shapes.box(0.125, 0, 0.6875, 0.3125, 0.1875, 0.875));
        shape = Shapes.or(shape, Shapes.box(0, 0.1875625, 0.0625, 0.125, 0.8125625, 0.9375));
        shape = Shapes.or(shape, Shapes.box(0.875, 0.1875625, 0.0625, 1, 0.8125625, 0.9375));
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.4375, 0.875, 0.9375, 1, 1));

        return shape;
    };

    private static final Supplier<VoxelShape> middleShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.1875, 0, 1, 0.4375, 1));
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.4375, 0.875, 1, 1, 1));
        shape = Shapes.or(shape, Shapes.box(0, 0.1875, 0, 0.0625, 0.4375, 1));
        shape = Shapes.or(shape, Shapes.box(0, 0.4375, 0.875, 0.0625, 1, 1));
        return shape;
    };

    private static final Supplier<VoxelShape> leftShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.1875, 0, 1, 0.4375, 1));
        shape = Shapes.or(shape, Shapes.box(0.125, 0, 0.125, 0.3125, 0.1875, 0.3125));
        shape = Shapes.or(shape, Shapes.box(0.125, 0, 0.6875, 0.3125, 0.1875, 0.875));
        shape = Shapes.or(shape, Shapes.box(0, 0.1875625, 0.0625, 0.125, 0.8125625, 0.9375));
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.4375, 0.875, 1, 1, 1));

        return shape;
    };

    private static final Supplier<VoxelShape> rightShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0, 0.1875, 0, 0.9375, 0.4375, 1));
        shape = Shapes.or(shape, Shapes.box(0.6875, 0, 0.125, 0.875, 0.1875, 0.3125));
        shape = Shapes.or(shape, Shapes.box(0.6875, 0, 0.6875, 0.875, 0.1875, 0.875));
        shape = Shapes.or(shape, Shapes.box(0.875, 0.1875625, 0.0625, 1, 0.8125625, 0.9375));
        shape = Shapes.or(shape, Shapes.box(0, 0.4375, 0.875, 0.9375, 1, 1));

        return shape;
    };

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Map<Direction, VoxelShape> voxelShape;
        switch (state.getValue(TYPE)) {
            case MIDDLE -> voxelShape = MIDDLE_SHAPE;
            case LEFT -> voxelShape = LEFT_SHAPE;
            case RIGHT -> voxelShape = RIGHT_SHAPE;
            default -> voxelShape = SHAPE;
        }
        return voxelShape.get(state.getValue(FACING));
    }

    public SofaBlock(Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return ChairUtil.onUse(world, player, hand, hit, 0);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        ChairUtil.onStateReplaced(world, pos);
    }

    static {
        SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, noneShapeSupplier.get()));
            }
        });
        MIDDLE_SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, middleShapeSupplier.get()));
            }
        });
        LEFT_SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, leftShapeSupplier.get()));
            }
        });
        RIGHT_SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, rightShapeSupplier.get()));
            }
        });
    }
}
