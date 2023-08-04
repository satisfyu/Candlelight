package satisfyu.candlelight.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import satisfyu.candlelight.block.CookingPanBlock;
import satisfyu.candlelight.client.gui.handler.CookingPanGuiHandler;
import satisfyu.candlelight.item.food.EffectFood;
import satisfyu.candlelight.item.food.EffectFoodHelper;
import satisfyu.candlelight.recipe.CookingPanRecipe;
import satisfyu.candlelight.registry.BlockEntityRegistry;
import satisfyu.candlelight.registry.RecipeTypeRegistry;
import satisfyu.candlelight.registry.TagsRegistry;
import satisfyu.candlelight.util.ImplementedInventory;

import static net.minecraft.world.item.ItemStack.isSameItemSameTags;

public class CookingPanBlockEntity extends BlockEntity implements BlockEntityTicker<CookingPanBlockEntity>, ImplementedInventory, MenuProvider {

	private final NonNullList<ItemStack> inventory = NonNullList.withSize(MAX_CAPACITY, ItemStack.EMPTY);
	private static final int MAX_CAPACITY = 8;
	public static final int MAX_COOKING_TIME = 600; // Time in ticks (30s)
	private int cookingTime;

	private static final int[] SLOTS_FOR_UP = new int[]{0, 1, 2, 3, 4, 5, 6};
	public static final int BOTTLE_INPUT_SLOT = 6;
	public static final int OUTPUT_SLOT = 7;
	private static final int INGREDIENTS_AREA = 2 * 3;

	private boolean isBeingBurned;

	private final ContainerData delegate;

	public CookingPanBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.COOKING_PAN_BLOCK_ENTITY.get(), pos, state);
		this.delegate = new ContainerData() {
			@Override
			public int get(int index) {
				return switch (index) {
					case 0 -> CookingPanBlockEntity.this.cookingTime;
					case 1 -> CookingPanBlockEntity.this.isBeingBurned ? 1 : 0;
					default -> 0;
				};
			}

			@Override
			public void set(int index, int value) {
				switch (index) {
					case 0 -> CookingPanBlockEntity.this.cookingTime = value;
					case 1 -> CookingPanBlockEntity.this.isBeingBurned = value != 0;
				}
			}

			@Override
			public int getCount() {
				return 2;
			}
		};
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if(side.equals(Direction.UP)){
			return SLOTS_FOR_UP;
		} else if (side.equals(Direction.DOWN)){
			return new int[]{OUTPUT_SLOT};
		} else return new int[]{BOTTLE_INPUT_SLOT};
	}
	
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
			ContainerHelper.loadAllItems(nbt, this.inventory);
			this.cookingTime = nbt.getInt("CookingTime");
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		ContainerHelper.saveAllItems(nbt, this.inventory);
		nbt.putInt("CookingTime", this.cookingTime);
	}
	
	public boolean isBeingBurned() {
		if (this.level == null)
			throw new NullPointerException("Null world invoked");
		final BlockState belowState = this.level.getBlockState(this.getBlockPos().below());
		if (belowState.is(TagsRegistry.ALLOWS_COOKING)) {
			try {
				return belowState.getValue(BlockStateProperties.LIT);
			} catch (IllegalArgumentException e) {
				return true;
			}
		}
		return false;
	}

	private boolean canCraft(Recipe<?> recipe, RegistryAccess access) {
		if (recipe == null || recipe.getResultItem(access).isEmpty()) {
			return false;
		}
		if(recipe instanceof CookingPanRecipe cookingPanRecipe){
			if (!this.getItem(BOTTLE_INPUT_SLOT).is(cookingPanRecipe.getContainer().getItem())) {
				return false;
			} else if (this.getItem(OUTPUT_SLOT).isEmpty()) {
				return true;
			} else {
				if (this.getItem(OUTPUT_SLOT).isEmpty()) {
					return true;
				}
				final ItemStack recipeOutput = this.generateOutputItem(recipe, access);
				final ItemStack outputSlotStack = this.getItem(OUTPUT_SLOT);
				final int outputSlotCount = outputSlotStack.getCount();
				if (this.getItem(OUTPUT_SLOT).isEmpty()) {
					return true;
				}
				else if (!isSameItemSameTags(outputSlotStack, recipeOutput)) {
					return false;
				} else if (outputSlotCount < this.getMaxStackSize() && outputSlotCount < outputSlotStack.getMaxStackSize()) {
					return true;
				} else {
					return outputSlotCount < recipeOutput.getMaxStackSize();
				}
			}
		}
		return false;
	}

	private void craft(Recipe<?> recipe, RegistryAccess access) {
		if (!canCraft(recipe, access)) {
			return;
		}
		final ItemStack recipeOutput = this.generateOutputItem(recipe, access);
		final ItemStack outputSlotStack = this.getItem(OUTPUT_SLOT);
		if (outputSlotStack.isEmpty()) {
			this.setItem(OUTPUT_SLOT, recipeOutput);
		} else if (outputSlotStack.is(recipeOutput.getItem())) {
			outputSlotStack.grow(recipeOutput.getCount());
		}
		final NonNullList<Ingredient> ingredients = recipe.getIngredients();
		// each slot can only be used once because in canMake we only checked if decrement by 1 still retains the recipe
		// otherwise recipes can break when an ingredient is used multiple times
		boolean[] slotUsed = new boolean[INGREDIENTS_AREA];
		for (int i = 0; i < recipe.getIngredients().size(); i++) {
			Ingredient ingredient = ingredients.get(i);
			// Looks for the best slot to take it from
			final ItemStack bestSlot = this.getItem(i);
			if (ingredient.test(bestSlot) && !slotUsed[i]) {
				slotUsed[i] = true;
				bestSlot.shrink(1);
			} else {
				// check all slots in search of the ingredient
				for (int j = 0; j < INGREDIENTS_AREA; j++) {
					ItemStack stack = this.getItem(j);
					if (ingredient.test(stack) && !slotUsed[j]) {
						slotUsed[j] = true;
						stack.shrink(1);
					}
				}
			}
		}
		this.getItem(BOTTLE_INPUT_SLOT).shrink(1);
	}

	private ItemStack generateOutputItem(Recipe<?> recipe, RegistryAccess access) {
		ItemStack outputStack = recipe.getResultItem(access);

		if (outputStack.getItem() instanceof EffectFood) {
			for (Ingredient ingredient : recipe.getIngredients()) {
				for (int slot = 0; slot < 6; slot++) {
					ItemStack stack = this.getItem(slot);
					if (ingredient.test(stack)) {
						EffectFoodHelper.getEffects(stack).forEach(effect -> EffectFoodHelper.addEffect(outputStack, effect));
					}
				}
			}
		}
		return outputStack;
	}


	@Override
	public void tick(Level world, BlockPos pos, BlockState state, CookingPanBlockEntity blockEntity) {
		if (world.isClientSide()) {
			return;
		}
		this.isBeingBurned = this.isBeingBurned();
		if (!this.isBeingBurned){
			if(state.getValue(CookingPanBlock.LIT)) {
				world.setBlock(pos, state.setValue(CookingPanBlock.LIT, false), Block.UPDATE_ALL);
			}
			return;
		}
		Recipe<?> recipe = world.getRecipeManager().getRecipeFor(RecipeTypeRegistry.COOKING_PAN_RECIPE_TYPE.get(), this, world).orElse(null);
		RegistryAccess access = level.registryAccess();
		boolean canCraft = this.canCraft(recipe, access);
		if (canCraft) {
			this.cookingTime++;
			if (this.cookingTime >= MAX_COOKING_TIME) {
				this.cookingTime = 0;
				this.craft(recipe, access);
			}
		} else if (!this.canCraft(recipe, access)) {
			this.cookingTime = 0;
		}
		if (canCraft) {
			world.setBlock(pos, this.getBlockState().getBlock().defaultBlockState().setValue(CookingPanBlock.COOKING, true).setValue(CookingPanBlock.LIT, true), Block.UPDATE_ALL);
		} else if (state.getValue(CookingPanBlock.COOKING)) {
			world.setBlock(pos, this.getBlockState().getBlock().defaultBlockState().setValue(CookingPanBlock.COOKING, false).setValue(CookingPanBlock.LIT, true), Block.UPDATE_ALL);
		}
		else if(state.getValue(CookingPanBlock.LIT) != this.isBeingBurned){
			world.setBlock(pos, state.setValue(CookingPanBlock.LIT, this.isBeingBurned), Block.UPDATE_ALL);
		}
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return inventory;
	}


	@Override
	public boolean stillValid(Player player) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return player.distanceToSqr((double) this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 0.5, (double) this.worldPosition.getZ() + 0.5) <= 64.0;
		}
	}
	
	@Override
	public Component getDisplayName() {
		return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
	}
	
	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
		return new CookingPanGuiHandler(syncId, inv, this, this.delegate);
	}
}

