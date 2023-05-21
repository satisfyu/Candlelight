package satisfyu.candlelight.block.entity;

import de.cristelknight.doapi.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import satisfyu.candlelight.client.gui.handler.WineStationGuiHandler;
import satisfyu.candlelight.registry.BlockEntityRegistry;
import satisfyu.candlelight.util.CandlelightTags;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.List;

public class WineStationBlockEntity extends BlockEntity implements BlockEntityTicker<WineStationBlockEntity>, Container, MenuProvider { //TODO
    private NonNullList<ItemStack> inventory = NonNullList.withSize(CAPACITY, ItemStack.EMPTY);
    protected static final int[] SELL_SLOTS = {0, 1, 2};
    protected static final int[] OUTPUT_SLOTS = {3, 4};
    public static final int CAPACITY = 5;

    private final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return 0;
        }

        @Override
        public void set(int index, int value) {
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public WineStationBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WINE_STATION_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick(Level world, BlockPos pos, BlockState state, WineStationBlockEntity blockEntity) {

    }

    public boolean hasWine() {
        return this.inventory.stream().anyMatch(itemStack -> itemStack.is(CandlelightTags.WINE));
    }

    public boolean buyWine(Villager villager) {

        List<ItemStack> wineStacks = inventory.stream().filter(possibleItemStack -> possibleItemStack.is(CandlelightTags.WINE)).toList();
        ItemStack wineItemStack = wineStacks.get(RandomSource.create().nextInt(wineStacks.size()));
        ItemStack itemStack = payWine(wineItemStack);

        if (!itemStack.isEmpty()) {
            for (int outputSlot: OUTPUT_SLOTS) {
                final ItemStack outputSlotStack = this.getItem(outputSlot);
                if (outputSlotStack.isEmpty()) {
                    setItem(outputSlot, itemStack.copy());
                    break;
                } else if (outputSlotStack.is(itemStack.getItem())) {
                    if (itemStack.getCount() >= outputSlotStack.getMaxStackSize()) {
                        villager.playSound(SoundEvents.VILLAGER_NO, 1.0F, villager.getVoicePitch());
                        return false;
                    }
                    outputSlotStack.grow(itemStack.getCount());
                    break;
                }
            }
        }

        wineItemStack.shrink(1);

        if (itemStack == ItemStack.EMPTY) {

        }
        villager.playSound(SoundEvents.VILLAGER_TRADE, 1.0F, villager.getVoicePitch());
        return true;
    }

    private ItemStack payWine(ItemStack wineItemStack) {
        int random = RandomSource.create().nextInt(10);
        if (wineItemStack.getItem() == ObjectRegistry.KING_DANIS_WINE.get().asItem()) {
            ItemStack emeraldItemStack = Items.EMERALD.getDefaultInstance();
            switch (random) {
                case 5, 6, 7 -> emeraldItemStack.setCount(2);
                case 8, 9 -> emeraldItemStack.setCount(3);
                default -> {

                }
            }
            return emeraldItemStack;
        } else {
            switch (random) {
                case 5, 6, 7, 8 -> {
                    return Items.COAL.getDefaultInstance();
                }
                case 9 -> {
                    return ItemStack.EMPTY;
                }
                default -> {
                    return Items.EMERALD.getDefaultInstance();
                }
            }
        }
    }

    public List<ItemStack> getWine() {
        return this.inventory.stream().toList();
        //return this.inventory.stream().filter((itemstack) -> itemstack.isIn(CandlelightTags.WINE)).toList();
    }

    public void setInventory(NonNullList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }

    @Override
    public void setLevel(Level world) {
        this.level = world;
    }

    @Override
    public int getContainerSize() {
        return CAPACITY;
    }

    @Override
    public boolean isEmpty() {
        return inventory.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.inventory, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        final ItemStack stackInSlot = this.inventory.get(slot);
        boolean dirty = !stack.isEmpty() && stack.sameItem(stackInSlot) && ItemStack.tagMatches(stack, stackInSlot);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        if (slot == SELL_SLOTS[0] || slot == SELL_SLOTS[1] || slot == SELL_SLOTS[2] && !dirty) {
            this.setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double)this.worldPosition.getY() + 0.5, (double)this.worldPosition.getZ() + 0.5) <= 64.0;
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new WineStationGuiHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    public void clearContent() {
        inventory.clear();
    }

    @Override
    public void setChanged() {
        if(level != null && !level.isClientSide()) {
            Packet<ClientGamePacketListener> updatePacket = getUpdatePacket();

            for (ServerPlayer player : Util.tracking((ServerLevel) level, getBlockPos())) {
                player.connection.send(updatePacket);
            }
        }
        super.setChanged();
    }


    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.inventory = NonNullList.withSize(CAPACITY, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, this.inventory);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
}
