package net.satisfy.candlelight.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.block.TableSetBlock;
import org.jetbrains.annotations.Nullable;

public class TableSetBlockItem extends BlockItem {
    private final TableSetBlock.PlateType type;

    public TableSetBlockItem(Block block, Properties settings, TableSetBlock.PlateType type) {
        super(block, settings);
        this.type = type;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext blockPlaceContext) {
        BlockState state = super.getPlacementState(blockPlaceContext);
        if (state == null) return null;
        state = state.setValue(TableSetBlock.PLATE_TYPE, type);
        return state;
    }

}