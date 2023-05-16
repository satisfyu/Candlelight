package satisfyu.candlelight.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TypeWriterEntity extends BlockEntity {

    public static final String PAPER_KEY ="paper";

    @Nullable
    ItemStack paper = ItemStack.EMPTY;

    public TypeWriterEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.TYPE_WRITER_BLOCK_ENTITY, pos, state);
    }

    public ItemStack getPaper() {
        return paper;
    }

    public void addPaper(ItemStack itemStack) {
        paper = itemStack;
    }

    public ItemStack removePaper() {
        ItemStack returnStack = paper.copy();
        this.paper = ItemStack.EMPTY;
        return returnStack;
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        writePaper(nbt, paper);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        paper = readPaper(nbt);
    }

    public void writePaper(CompoundTag nbt, ItemStack flower) {
        CompoundTag nbtCompound = new CompoundTag();
        if (flower != null) {
            flower.save(nbtCompound);
        }
        nbt.put(PAPER_KEY, nbtCompound);
    }

    public ItemStack readPaper(CompoundTag nbt) {
        super.load(nbt);
        if(nbt.contains(PAPER_KEY)) {
            CompoundTag nbtCompound = nbt.getCompound(PAPER_KEY);
            if (!nbtCompound.isEmpty()) {
                return ItemStack.of(nbtCompound);
            }
        }
        return null;
    }
}
