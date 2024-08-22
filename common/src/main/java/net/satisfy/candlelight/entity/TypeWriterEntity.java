package net.satisfy.candlelight.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.registry.BlockEntityRegistry;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("all")
public class TypeWriterEntity extends BlockEntity {

    public static final String PAPER_KEY = "paper";

    @Nullable
    ItemStack paper = ItemStack.EMPTY;

    public TypeWriterEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TYPE_WRITER_BLOCK_ENTITY.get(), pos, state);
    }

    public ItemStack getPaper() {
        return paper != null ? paper : ItemStack.EMPTY;
    }

    public void addPaper(ItemStack itemStack) {
        paper = itemStack;
    }

    public void removePaper() {
        assert paper != null;
        ItemStack returnStack = paper.copy();
        this.paper = ItemStack.EMPTY;
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        writePaper(compoundTag, provider, paper);
        super.saveAdditional(compoundTag, provider);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        paper = readPaper(compoundTag, provider);
    }

    public void writePaper(CompoundTag nbt, HolderLookup.Provider provider, ItemStack flower) {
        CompoundTag nbtCompound = new CompoundTag();
        if (flower != null && !flower.isEmpty()) {
            nbt.put(PAPER_KEY, flower.save(provider));
        }
    }

    public ItemStack readPaper(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        if (nbt.contains(PAPER_KEY)) {
            CompoundTag nbtCompound = nbt.getCompound(PAPER_KEY);
            if (!nbtCompound.isEmpty()) {
                return ItemStack.parse(this.level.registryAccess(), nbt.getCompound("Item")).get();
            }
        }
        return null;
    }

    @Nullable
    public Packet<ClientGamePacketListener> toUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}

