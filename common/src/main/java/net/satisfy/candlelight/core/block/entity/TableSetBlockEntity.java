package net.satisfy.candlelight.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TableSetBlockEntity extends StorageBlockEntity {
    private ItemStack effectStack = ItemStack.EMPTY;
    private int effectDuration = 0;

    public TableSetBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
    }

    public TableSetBlockEntity(BlockPos pos, BlockState state, int size) {
        super(pos, state, size);
    }

    public ItemStack getEffectStack() {
        return this.effectStack;
    }

    public int getEffectDuration() {
        return this.effectDuration;
    }

    public void setEffectStack(ItemStack stack, int duration) {
        this.effectStack = stack;
        this.effectDuration = duration;
        setChanged();
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.effectStack = ItemStack.of(nbt.getCompound("EffectStack"));
        this.effectDuration = nbt.getInt("EffectDuration");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("EffectStack", this.effectStack.save(new CompoundTag()));
        nbt.putInt("EffectDuration", this.effectDuration);
    }
}
