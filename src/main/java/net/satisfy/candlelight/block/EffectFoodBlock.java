package net.satisfy.candlelight.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.satisfy.candlelight.block.entity.CookingPanEntity;
import net.satisfy.candlelight.block.entity.EffectFoodBlockEntity;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.util.GeneralUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static net.satisfy.candlelight.item.EffectFoodHelper.getEffects;

public class EffectFoodBlock extends BlockWithEntity {
    public static final DirectionProperty FACING;
    public static final IntProperty BITES;
    private final int maxBites;
    private final FoodComponent foodComponent;

    public EffectFoodBlock(Settings settings, int maxBites, FoodComponent foodComponent) {
        super(settings);
        this.maxBites = maxBites;
        this.foodComponent = foodComponent;
        setDefaultState(this.getDefaultState().with(BITES, 0).with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        ctx.getStack().getNbt();
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof EffectFoodBlockEntity effectFoodBlockEntity) {
            effectFoodBlockEntity.addEffects(getEffects(itemStack));
        }

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (world.isClient) {
            if (tryEat(world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }

            if (itemStack.isEmpty()) {
                return ActionResult.CONSUME;
            }
        }
        return tryEat(world, pos, state, player);
    }



    private ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
        world.playSound(null, pos, SoundEvents.ENTITY_FOX_EAT, SoundCategory.PLAYERS, 0.5f, world.getRandom().nextFloat() * 0.1f + 0.9f);
        player.getHungerManager().add(1, 0.4f);
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        } else {
            player.getHungerManager().add(foodComponent.getHunger(), foodComponent.getSaturationModifier());
            int bites = state.get(BITES);
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof EffectFoodBlockEntity effectFoodBlockEntity) {
                for (Pair<StatusEffectInstance, Float> effect : effectFoodBlockEntity.getEffects()) {
                    player.addStatusEffect(effect.getFirst());
                }
            }
            world.emitGameEvent(player, GameEvent.EAT, pos);
            if (bites < maxBites) {
                world.setBlockState(pos, state.with(BITES, bites + 1), 3);
            } else {
                world.breakBlock(pos, false);
                world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }
            return ActionResult.SUCCESS;
        }
    }


    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, BITES);
    }


    static {
        FACING = Properties.HORIZONTAL_FACING;
        BITES = IntProperty.of("bites", 0, 3);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.0625, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.0625, 0.1875, 0.8125, 0.4375, 0.8125));


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


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CookingPanEntity(pos, state);
    }
}
