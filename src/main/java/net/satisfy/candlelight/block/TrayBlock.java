package net.satisfy.candlelight.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.satisfy.candlelight.registry.StorageTypes;
import satisfyu.vinery.util.VineryUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TrayBlock extends CakeStandBlock {
    public TrayBlock(Settings settings) {
        super(settings);
    }

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0, 0.15625, 0.75, 0.0625, 0.84375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.0625, 0.125, 0.78125, 0.1875, 0.875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.0625, 0.125, 0.28125, 0.1875, 0.875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.0625, 0.8125, 0.71875, 0.1875, 0.875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.0625, 0.125, 0.71875, 0.1875, 0.1875), BooleanBiFunction.OR);
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


    @Override
    public int size() {
        return 6;
    }

    @Override
    public Identifier type() {
        return StorageTypes.TRAY;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN};
    }

    @Override
    public int getSection(Float f, Float y) {
        return 0;
        /*
        int nSection;
        float oneS = (float) 1 / 6;

        if (f < oneS) {
            nSection = 0;
        }
        else if(f < oneS*2){
            nSection = 1;
        }
        else if(f < oneS*3){
            nSection = 2;
        }
        else if(f < oneS*4){
            nSection = 3;
        }
        else if(f < oneS*5){
            nSection = 4;
        }
        else nSection = 5;

        return 5 - nSection;
         */
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.candlelight.canbeplaced.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
        tooltip.add(Text.translatable("block.candlelight.cakestand.tooltip").formatted(Formatting.ITALIC, Formatting.WHITE));
        tooltip.add(Text.translatable("block.candlelight.tray.tooltip").formatted(Formatting.ITALIC, Formatting.WHITE));
    }
}
