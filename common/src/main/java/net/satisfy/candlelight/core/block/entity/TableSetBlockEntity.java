package net.satisfy.candlelight.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TableSetBlockEntity extends StorageBlockEntity {
    private ItemStack effectStack = ItemStack.EMPTY;

    public TableSetBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
    }

    public TableSetBlockEntity(BlockPos pos, BlockState state, int size) {
        super(pos, state, size);
    }

    public ItemStack getEffectStack() {
        return this.effectStack;
    }

    public void setEffectStack(ItemStack stack) {
        this.effectStack = stack;
        setChanged();
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.effectStack = ItemStack.of(nbt.getCompound("EffectStack"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("EffectStack", this.effectStack.save(new CompoundTag()));
    }

}
