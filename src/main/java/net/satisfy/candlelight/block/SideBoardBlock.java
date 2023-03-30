package net.satisfy.candlelight.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.satisfy.candlelight.block.entity.SideBoardBlockEntity;
import net.satisfy.candlelight.registry.CandlelightEntityTypes;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SideBoardBlock extends ChestBlock {

    public static final Map<Direction, VoxelShape> SHAPES = Util.make(new HashMap<>(), map -> {
        map.put(Direction.NORTH, Block.createCuboidShape(0,0,4, 16, 16, 16));
        map.put(Direction.SOUTH, Block.createCuboidShape(0,0,0, 16, 16, 12));
        map.put(Direction.WEST, Block.createCuboidShape(4,0,0, 16, 16, 16));
        map.put(Direction.EAST, Block.createCuboidShape(0,0,0, 12, 16, 16));

    });

    public SideBoardBlock(Settings settings) {
        super(settings, () -> CandlelightEntityTypes.SIDEBOARD);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SideBoardBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(FACING));
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null;
    }
    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.candlelight.expandable.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
    }
}
