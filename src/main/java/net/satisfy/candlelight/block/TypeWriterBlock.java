package net.satisfy.candlelight.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
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
import satisfyu.vinery.util.VineryUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TypeWriterBlock extends Block {
    public TypeWriterBlock(Settings settings) {
        super(settings);
    }
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if(stack.getItem() == ObjectRegistry.NOTE_PAPER)
        {
            stack.setCount(stack.getCount() - 1);
            player.giveItemStack(new ItemStack(ObjectRegistry.NOTE_PAPER_WRITEABLE));
            return ActionResult.SUCCESS;
        }


        return super.onUse(state, world, pos, player, hand, hit);
    }
    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.125, 0.9375, 0.125, 0.875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.125, 0.4375, 0.25, 0.3125, 0.875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.75, 0.125, 0.4375, 0.9375, 0.3125, 0.875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.125, 0.5, 0.75, 0.25, 0.75), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.125, 0.75, 0.75, 0.3125, 0.875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.3125, 0.6875, 0.25, 0.5, 0.875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.75, 0.3125, 0.6875, 0.9375, 0.5, 0.875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.34375, 0.71875, 1, 0.46875, 0.84375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.34375, 0.69375, 0.8125, 0.40625, 0.69375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.125, 0.1875, 0.8125, 0.1875, 0.4375), BooleanBiFunction.OR);
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

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.candlelight.canbeplaced.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
        tooltip.add(Text.translatable("block.candlelight.typewriter.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));

    }
}
