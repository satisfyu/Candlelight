package net.satisfy.candlelight.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.satisfy.candlelight.registry.ModBlockEntityTypes;

public class SideBoardBlockEntity extends ChestBlockEntity {
    public SideBoardBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.SIDEBOARD, blockPos, blockState);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.sideboard");
    }
}
