package net.satisfy.candlelight.block;

import com.mojang.serialization.MapCodec;
import de.cristelknight.doapi.common.registry.DoApiSoundEventRegistry;
import de.cristelknight.doapi.common.util.GeneralUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.farm_and_charm.block.CookingPotBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.satisfy.candlelight.entity.CookingPanBlockEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class CookingPanBlock extends BaseEntityBlock {
    public static final MapCodec<CookingPanBlock> CODEC = simpleCodec(CookingPanBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public static final IntegerProperty DAMAGE = IntegerProperty.create("damage", 0, 25);
    public static final BooleanProperty COOKING = BooleanProperty.create("cooking");
    public static final BooleanProperty NEEDS_SUPPORT = BooleanProperty.create("needs_support");
    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.1875, 0, 0.1875, 0.8125, 0.0625, 0.8125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.25, 0.0625, 0.75, 0.75, 0.25, 0.8125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.25, 0.0625, 0.1875, 0.75, 0.25, 0.25), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.75, 0.0625, 0.1875, 0.8125, 0.25, 0.8125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.1875, 0.0625, 0.1875, 0.25, 0.25, 0.8125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(-0.3125, 0.0625, 0.4375, 0.1875, 0.1875, 0.5625), BooleanOp.OR);
        return shape;
    };
    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    public CookingPanBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false).setValue(COOKING, false).setValue(NEEDS_SUPPORT, false));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT, COOKING, NEEDS_SUPPORT, DAMAGE);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!world.isClientSide) {
            if (state.getValue(NEEDS_SUPPORT)) {
                boolean isSupported = world.getBlockState(pos.below()).isSolidRender(world, pos.below()) || world.getBlockState(pos.below()).is(BlockTags.CAMPFIRES);
                if (!isSupported) {
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        BlockPos neighborPos = pos.relative(direction);
                        BlockState neighborState = world.getBlockState(neighborPos);
                        if (neighborState.getBlock() instanceof CookingPotBlock && neighborState.getValue(NEEDS_SUPPORT)) {
                            isSupported = true;
                            break;
                        }
                    }
                }
                if (!isSupported) {
                    world.destroyBlock(pos, true);
                }
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState belowState = world.getBlockState(pos.below());
        boolean needsSupport = belowState.is(BlockTags.CAMPFIRES);
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite()).setValue(NEEDS_SUPPORT, needsSupport);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = world.getBlockState(belowPos);
        boolean isCampfireBelow = belowState.is(BlockTags.CAMPFIRES);
        boolean isSolidBelow = belowState.isFaceSturdy(world, belowPos, Direction.UP);
        return isCampfireBelow || isSolidBelow;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public BlockState playerWillDestroy(@NotNull Level level, BlockPos blockPos, @NotNull BlockState blockState, @NotNull Player player) {
        ItemStack stack = new ItemStack(this);
        stack.setDamageValue(blockState.getValue(DAMAGE));
        ItemEntity itemEntity = new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
        return super.playerWillDestroy(level, blockPos, blockState, player);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        boolean hasSupport = world.getBlockState(pos.below()).is(BlockTags.CAMPFIRES);
        if (!hasSupport) {
            return state.setValue(NEEDS_SUPPORT, false);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }


    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof MenuProvider) {
                player.openMenu((MenuProvider) entity);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(COOKING) || state.getValue(LIT)) {
            double d = (double) pos.getX() + 0.5;
            double e = pos.getY() + 0.7;
            double f = (double) pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                world.playLocalSound(d, e, f, DoApiSoundEventRegistry.ROASTER_COOKING.get(), SoundSource.BLOCKS, 0.05F, 1.0F, false);
            }
            Direction direction = state.getValue(FACING);
            Direction.Axis axis = direction.getAxis();
            double h = random.nextDouble() * 0.6 - 0.3;
            double i = axis == Direction.Axis.X ? (double) direction.getStepX() * 0.0 : h;
            double j = random.nextDouble() * 9.0 / 16.0;
            double k = axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.0 : h;
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new CookingPanBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CookingPanBlockEntity) {
                Containers.dropContents(world, pos, ((CookingPanBlockEntity) blockEntity).getItems());
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClientSide) {
            return (lvl, pos, blkState, t) -> {
                if (t instanceof CookingPanBlockEntity cookingPot) {
                    cookingPot.tick(lvl, pos, blkState, cookingPot);
                }
            };
        }
        return null;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("tooltip.farm_and_charm.canbeplaced").withStyle(ChatFormatting.GRAY));
    }
}
