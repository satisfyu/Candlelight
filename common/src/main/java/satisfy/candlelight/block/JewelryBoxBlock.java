package satisfy.candlelight.block;

import de.cristelknight.doapi.common.block.StorageBlock;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import de.cristelknight.doapi.common.util.GeneralUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.candlelight.registry.StorageTypesRegistry;
import satisfy.candlelight.registry.TagsRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class JewelryBoxBlock extends StorageBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public JewelryBoxBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }


    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (world.isClientSide) return InteractionResult.SUCCESS;
        if (player.isShiftKeyDown() && blockEntity instanceof StorageBlockEntity) {
            world.setBlock(pos, state.setValue(OPEN, !state.getValue(OPEN)), Block.UPDATE_ALL);
            return InteractionResult.SUCCESS;
        }
        if (blockEntity instanceof StorageBlockEntity shelfBlockEntity) {
            Optional<Tuple<Float, Float>> optional = de.cristelknight.doapi.Util.getRelativeHitCoordinatesForBlockFace(hit, state.getValue(FACING), this.unAllowedDirections());
            if (optional.isEmpty()) {
                return InteractionResult.PASS;
            } else {
                Tuple<Float, Float> ff = optional.get();
                int i = this.getSection(ff.getA(), ff.getB());
                if (i == Integer.MIN_VALUE) {
                    return InteractionResult.PASS;
                } else {
                    ItemStack stack = player.getItemInHand(hand);
                    if (!shelfBlockEntity.getInventory().get(i).isEmpty()) {
                        this.remove(world, pos, player, shelfBlockEntity, i);
                        return InteractionResult.sidedSuccess(false);
                    } else if (!stack.isEmpty() && this.canInsertStack(stack)) {
                        this.add(world, pos, player, shelfBlockEntity, stack, i);
                        return InteractionResult.sidedSuccess(false);
                    } else {
                        return InteractionResult.CONSUME;
                    }
                }
            }
        } else {
            return InteractionResult.PASS;
        }
    }


    @Override
    public int size() {
        return 1;
    }

    @Override
    public ResourceLocation type() {
        return StorageTypesRegistry.JEWELRY_BOX;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[0];
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.is(TagsRegistry.RINGS);
    }

    @Override
    public int getSection(Float f, Float y) {
        float oneS = 1.0f;
        int nSection = (int) (f / oneS);
        return -nSection;
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
        builder.add(FACING, OPEN);
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
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("tooltip.candlelight.canbeplaced").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}
