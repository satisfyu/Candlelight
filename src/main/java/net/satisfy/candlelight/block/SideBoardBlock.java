package net.satisfy.candlelight.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.satisfy.candlelight.block.entity.SideBoardBlockEntity;
import net.satisfy.candlelight.registry.CBlockEntityTypes;

public class SideBoardBlock extends ChestBlock {
    public SideBoardBlock(Settings settings) {
        super(settings, () -> CBlockEntityTypes.SIDEBOARD);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SideBoardBlockEntity(pos, state);
    }
}
