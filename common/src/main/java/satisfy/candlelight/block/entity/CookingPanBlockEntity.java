package satisfy.candlelight.block.entity;

import de.cristelknight.doapi.common.world.ImplementedInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.candlelight.block.CookingPanBlock;
import satisfy.candlelight.registry.BlockEntityRegistry;
import satisfy.farm_and_charm.client.gui.handler.RoasterGuiHandler;
import satisfy.farm_and_charm.item.food.EffectFood;
import satisfy.farm_and_charm.item.food.EffectFoodHelper;
import satisfy.farm_and_charm.recipe.RoasterRecipe;
import satisfy.farm_and_charm.registry.RecipeTypeRegistry;
import satisfy.farm_and_charm.registry.TagRegistry;

import java.util.Objects;

import static net.minecraft.world.item.ItemStack.isSameItemSameTags;

@SuppressWarnings("unused")
public class CookingPanBlockEntity extends BlockEntity implements BlockEntityTicker<CookingPanBlockEntity>, ImplementedInventory, MenuProvider {
	private final NonNullList<ItemStack> inventory = NonNullList.withSize(MAX_CAPACITY, ItemStack.EMPTY);
	private static final int MAX_CAPACITY = 8, CONTAINER_SLOT = 6, OUTPUT_SLOT = 7, INGREDIENTS_AREA = 2 * 3;
	private static final int[] SLOTS_FOR_UP = new int[]{0, 1, 2, 3, 4, 5, 6};
	private int cookingTime;
	private boolean isBeingBurned;
	private static final int MAX_COOKING_TIME = 300;
	public static int getMaxCookingTime() {
		return MAX_COOKING_TIME;
	}
	private final ContainerData delegate = new ContainerData() {
		public int get(int index) {
			return switch (index) {
				case 0 -> cookingTime;
				case 1 -> isBeingBurned ? 1 : 0;
				default -> 0;
			};
		}

		public void set(int index, int value) {
			switch (index) {
				case 0 -> cookingTime = value;
				case 1 -> isBeingBurned = value != 0;
			}
		}

		public int getCount() {
			return 2;
		}
	};

	public CookingPanBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.COOKING_PAN_BLOCK_ENTITY.get(), pos, state);
	}

	public int @NotNull [] getSlotsForFace(Direction side) {
		return switch (side) {
			case UP -> SLOTS_FOR_UP;
			case DOWN -> new int[]{OUTPUT_SLOT};
			default -> new int[]{CONTAINER_SLOT};
		};
	}

	public void load(CompoundTag nbt) {
		super.load(nbt);
		ContainerHelper.loadAllItems(nbt, inventory);
		cookingTime = nbt.getInt("CookingTime");
	}

	protected void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		ContainerHelper.saveAllItems(nbt, inventory);
		nbt.putInt("CookingTime", cookingTime);
	}

	public boolean isBeingBurned() {
		if (level == null) throw new IllegalStateException("Null world not allowed");
		BlockState belowState = level.getBlockState(worldPosition.below());
		return belowState.is(TagRegistry.ALLOWS_COOKING);
	}

	private boolean canCraft(Recipe<?> recipe, RegistryAccess access) {
		if (recipe == null || recipe.getResultItem(access).isEmpty()) return false;
		if (recipe instanceof RoasterRecipe cookingRecipe) {
			ItemStack outputSlotStack = getItem(OUTPUT_SLOT), containerSlotStack = getItem(CONTAINER_SLOT);
			boolean isContainerCorrect = containerSlotStack.is(cookingRecipe.getContainer().getItem()), isOutputSlotCompatible = outputSlotStack.isEmpty() || isSameItemSameTags(outputSlotStack, generateOutputItem(recipe, access)) && outputSlotStack.getCount() < outputSlotStack.getMaxStackSize();
			return isContainerCorrect && isOutputSlotCompatible;
		}
		return false;
	}

	private void craft(Recipe<?> recipe, RegistryAccess access) {
		if (!canCraft(recipe, access)) return;
		ItemStack recipeOutput = generateOutputItem(recipe, access), outputSlotStack = getItem(OUTPUT_SLOT);
		if (outputSlotStack.isEmpty()) {
			setItem(OUTPUT_SLOT, recipeOutput);
		} else {
			outputSlotStack.grow(recipeOutput.getCount());
		}
		recipe.getIngredients().forEach(ingredient -> {
			for (int slot = 0; slot < INGREDIENTS_AREA; slot++) {
				ItemStack stack = getItem(slot);
				if (ingredient.test(stack)) {
					ItemStack remainderStack = stack.getItem().hasCraftingRemainingItem() ? new ItemStack(Objects.requireNonNull(stack.getItem().getCraftingRemainingItem())) : ItemStack.EMPTY;
					stack.shrink(1);
					if (!remainderStack.isEmpty()) setItem(slot, remainderStack);
					break;
				}
			}
		});
		ItemStack containerSlotStack = getItem(CONTAINER_SLOT);
		if (!containerSlotStack.isEmpty()) {
			containerSlotStack.shrink(1);
			if (containerSlotStack.isEmpty()) setItem(CONTAINER_SLOT, ItemStack.EMPTY);
		}
	}

	private ItemStack generateOutputItem(Recipe<?> recipe, RegistryAccess access) {
		ItemStack outputStack = recipe.getResultItem(access);
		if (outputStack.getItem() instanceof EffectFood) {
			recipe.getIngredients().forEach(ingredient -> {
				for (int slot = 0; slot < INGREDIENTS_AREA; slot++) {
					ItemStack stack = getItem(slot);
					if (ingredient.test(stack)) {
						EffectFoodHelper.getEffects(stack).forEach(effect -> EffectFoodHelper.addEffect(outputStack, effect));
						break;
					}
				}
			});
		}
		return outputStack;
	}

	public void tick(Level world, BlockPos pos, BlockState state, CookingPanBlockEntity blockEntity) {
		if (world.isClientSide()) return;
		boolean wasBeingBurned = isBeingBurned;
		isBeingBurned = isBeingBurned();
		if (wasBeingBurned != isBeingBurned || state.getValue(CookingPanBlock.LIT) != isBeingBurned) {
			world.setBlock(pos, state.setValue(CookingPanBlock.LIT, isBeingBurned), Block.UPDATE_ALL);
		}

		if (!isBeingBurned) {
			return;
		}

		Recipe<?> recipe = world.getRecipeManager().getRecipeFor(RecipeTypeRegistry.ROASTER_RECIPE_TYPE.get(), this, world).orElse(null);
		if (level == null) throw new IllegalStateException("Null world not allowed");
		RegistryAccess access = level.registryAccess();
		if (canCraft(recipe, access)) {
			if (++cookingTime >= MAX_COOKING_TIME) {
				cookingTime = 0;
				craft(recipe, access);
			}
			if (!state.getValue(CookingPanBlock.COOKING)) {
				world.setBlock(pos, state.setValue(CookingPanBlock.COOKING, true), Block.UPDATE_ALL);
			}
		} else {
			cookingTime = 0;
			if (state.getValue(CookingPanBlock.COOKING)) {
				world.setBlock(pos, state.setValue(CookingPanBlock.COOKING, false), Block.UPDATE_ALL);
			}
		}
	}

	@Override
	public void setChanged() {
		super.setChanged();
		if (this.level != null)
			level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag() {
		CompoundTag compoundTag = new CompoundTag();
		this.saveAdditional(compoundTag);
		return compoundTag;
	}

	public NonNullList<ItemStack> getItems() {
		return inventory;
	}

	public boolean stillValid(Player player) {
		if (level == null || level.getBlockEntity(worldPosition) != this) return false;
		return player.distanceToSqr(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D) <= 64.0D;
	}

	public @NotNull Component getDisplayName() {
		return Component.translatable(getBlockState().getBlock().getDescriptionId());
	}

	@Nullable
	public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
		return new RoasterGuiHandler(syncId, inv, this, delegate);
	}
}
