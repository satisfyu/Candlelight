package net.satisfy.candlelight.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.satisfy.candlelight.registry.CandlelightEntityTypes;
import org.jetbrains.annotations.Nullable;

public class TypeWriterEntity extends BlockEntity {

    public static final String PAPER_KEY ="paper";

    @Nullable
    ItemStack paper;

    public TypeWriterEntity(BlockPos pos, BlockState state) {
        super(CandlelightEntityTypes.TYPE_WRITER_BLOCK_ENTITY, pos, state);
    }

    public ItemStack getPaper() {
        return paper;
    }

    public void addPaper(ItemStack itemStack) {
        paper = itemStack;
    }

    public ItemStack removePaper() {
        ItemStack returnStack = paper.copy();
        this.paper = null;
        return returnStack;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        writePaper(nbt, paper);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        paper = readPaper(nbt);
    }

    public void writePaper(NbtCompound nbt, ItemStack flower) {
        NbtCompound nbtCompound = new NbtCompound();
        if (flower != null) {
            flower.writeNbt(nbtCompound);
        }
        nbt.put(PAPER_KEY, nbtCompound);
    }

    public ItemStack readPaper(NbtCompound nbt) {
        super.readNbt(nbt);
        if(nbt.contains(PAPER_KEY)) {
            NbtCompound nbtCompound = nbt.getCompound(PAPER_KEY);
            if (!nbtCompound.isEmpty()) {
                return ItemStack.fromNbt(nbtCompound);
            }
        }
        return null;
    }
}
