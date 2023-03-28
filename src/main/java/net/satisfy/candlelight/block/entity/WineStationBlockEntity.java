package net.satisfy.candlelight.block.entity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.satisfy.candlelight.client.gui.handler.WineStationGuiHandler;
import net.satisfy.candlelight.networking.CandlelightMessages;
import net.satisfy.candlelight.registry.CandlelightEntityTypes;
import net.satisfy.candlelight.util.CandlelightTags;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.List;

public class WineStationBlockEntity extends BlockEntity implements BlockEntityTicker<WineStationBlockEntity>, Inventory, NamedScreenHandlerFactory {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(CAPACITY, ItemStack.EMPTY);;
    protected static final int[] SELL_SLOTS = {0, 1, 2};
    protected static final int[] OUTPUT_SLOTS = {3, 4};
    public static final int CAPACITY = 5;

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                default -> {
                }
            }
        }

        @Override
        public int size() {
            return 1;
        }
    };

    public WineStationBlockEntity(BlockPos pos, BlockState state) {
        super(CandlelightEntityTypes.WINE_STATION_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, net.satisfy.candlelight.block.entity.WineStationBlockEntity blockEntity) {

    }

    public boolean hasWine() {
        return this.inventory.stream().anyMatch(itemStack -> itemStack.isIn(CandlelightTags.WINE));
    }

    public boolean buyWine(VillagerEntity villager) {

        List<ItemStack> wineStacks = inventory.stream().filter(possibleItemStack -> possibleItemStack.isIn(CandlelightTags.WINE)).toList();
        ItemStack wineItemStack = wineStacks.get(Random.create().nextInt(wineStacks.size()));
        ItemStack itemStack = payWine(wineItemStack);

        if (!itemStack.isEmpty()) {
            for (int outputSlot: OUTPUT_SLOTS) {
                final ItemStack outputSlotStack = this.getStack(outputSlot);
                if (outputSlotStack.isEmpty()) {
                    setStack(outputSlot, itemStack.copy());
                    break;
                } else if (outputSlotStack.isOf(itemStack.getItem())) {
                    if (itemStack.getCount() >= outputSlotStack.getMaxCount()) {
                        villager.playSound(SoundEvents.ENTITY_VILLAGER_NO, 1.0F, villager.getSoundPitch());
                        return false;
                    }
                    outputSlotStack.increment(itemStack.getCount());
                    break;
                }
            }
        }

        wineItemStack.decrement(1);

        if (itemStack == ItemStack.EMPTY) {

        }
        villager.playSound(SoundEvents.ENTITY_VILLAGER_TRADE, 1.0F, villager.getSoundPitch());
        return true;
    }

    private ItemStack payWine(ItemStack wineItemStack) {
        int random = Random.create().nextInt(10);
        if (wineItemStack.getItem() == ObjectRegistry.KING_DANIS_WINE.asItem()) {
            ItemStack emeraldItemStack = Items.EMERALD.getDefaultStack();
            switch (random) {
                case 5, 6, 7 -> {
                    emeraldItemStack.setCount(2);
                }
                case 8, 9 -> {
                    emeraldItemStack.setCount(3);
                }
                default -> {

                }
            }
            return emeraldItemStack;
        } else {
            switch (random) {
                case 5, 6, 7, 8 -> {
                    return Items.COAL.getDefaultStack();
                }
                case 9 -> {
                    return ItemStack.EMPTY;
                }
                default -> {
                    return Items.EMERALD.getDefaultStack();
                }
            }
        }
    }

    public List<ItemStack> getWine() {
        return this.inventory.stream().toList();
        //return this.inventory.stream().filter((itemstack) -> itemstack.isIn(CandlelightTags.WINE)).toList();
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public int size() {
        return CAPACITY;
    }

    @Override
    public boolean isEmpty() {
        return inventory.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        final ItemStack stackInSlot = this.inventory.get(slot);
        boolean dirty = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(stackInSlot) && ItemStack.areNbtEqual(stack, stackInSlot);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        if (slot == SELL_SLOTS[0] || slot == SELL_SLOTS[1] || slot == SELL_SLOTS[2] && !dirty) {
            this.markDirty();
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) <= 64.0;
        }
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(this.getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new WineStationGuiHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public void markDirty() {
        sendInventory();
        super.markDirty();
    }

    private void sendInventory() {
        System.out.println(world);
        if (world != null && !world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for (int i = 0; i < inventory.size(); i++) {
                data.writeItemStack(inventory.get(i));
            }
            data.writeBlockPos(getPos());

            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, CandlelightMessages.ITEM_SYNC, data);
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(CAPACITY, ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
    }
}
