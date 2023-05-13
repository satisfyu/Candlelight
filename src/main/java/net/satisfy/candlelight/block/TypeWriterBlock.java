package net.satisfy.candlelight.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.filter.FilteredMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
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
import net.satisfy.candlelight.block.entity.TypeWriterEntity;
import net.satisfy.candlelight.client.screen.NotePaperScreen;
import net.satisfy.candlelight.client.screen.TypeWriterScreen;
import net.satisfy.candlelight.registry.ObjectRegistry;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.util.VineryUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TypeWriterBlock extends BlockWithEntity {
    public static final DirectionProperty FACING;

    public static final IntProperty FULL = IntProperty.of("full", 0, 2);

    public static final Map<Direction, VoxelShape> SHAPE;

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

    public TypeWriterBlock(Settings settings) {
        super(settings);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() == ObjectRegistry.NOTE_PAPER && state.get(FULL) == 0) {
            world.setBlockState(pos, state.with(FULL, 1), 2);
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof TypeWriterEntity typeWriterEntity)
            {
                typeWriterEntity.addPaper(new ItemStack(ObjectRegistry.NOTE_PAPER_WRITEABLE));
                stack.setCount(stack.getCount() - 1);
            }
            //stack.decrement(1);
            //player.giveItemStack(new ItemStack(ObjectRegistry.NOTE_PAPER_WRITEABLE));
            return ActionResult.SUCCESS;
        }
        else if (state.get(FULL) == 1) {
            //world.setBlockState(pos, state.with(FULL, 1), 2);
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof TypeWriterEntity typeWriterEntity)
            {
                if(player instanceof ClientPlayerEntity)
                    MinecraftClient.getInstance().setScreen(new TypeWriterScreen(player, typeWriterEntity.getPaper(), hand, pos));
                //typeWriterEntity.addPaper(new ItemStack(ObjectRegistry.NOTE_PAPER_WRITEABLE));
            }

            //stack.decrement(1);
            //player.giveItemStack(new ItemStack(ObjectRegistry.NOTE_PAPER_WRITEABLE));
            return ActionResult.SUCCESS;
        }
        else if (state.get(FULL) == 2) {
            world.setBlockState(pos, state.with(FULL, 0), 2);
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof TypeWriterEntity typeWriterEntity)
            {
                ItemStack paper = typeWriterEntity.getPaper();
                ItemStack result = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITTEN);

                result.setNbt(paper.getNbt().copy());
                result.setSubNbt("author", NbtString.of(player.getName().getString()));
                player.giveItemStack(result);

                typeWriterEntity.removePaper();
            }
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE.get(state.get(FACING));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(FULL, 0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(FULL);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.candlelight.canbeplaced.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
        tooltip.add(Text.translatable("block.candlelight.typewriter.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));

    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Type.HORIZONTAL.stream().toList()) {
                map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
            }
        });
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TypeWriterEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
