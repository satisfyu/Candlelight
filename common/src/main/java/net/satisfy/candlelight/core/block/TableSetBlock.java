package net.satisfy.candlelight.core.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.candlelight.core.block.entity.StorageBlockEntity;
import net.satisfy.candlelight.core.block.entity.TableSetBlockEntity;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import net.satisfy.candlelight.core.registry.StorageTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class TableSetBlock extends StorageBlock {
    public static final EnumProperty<PlateType> PLATE_TYPE = EnumProperty.create("plate_tye", PlateType.class);
    public static final BooleanProperty WINE_GLASS = BooleanProperty.create("wine_glass");
    public static final BooleanProperty GLASS = BooleanProperty.create("glass");
    public static final BooleanProperty CLOCHE = BooleanProperty.create("cloche");
    public static final BooleanProperty NAPKIN = BooleanProperty.create("napkin");
    public static final BooleanProperty GLASS_DRINK = BooleanProperty.create("glass_drink");
    public static final BooleanProperty WINE_GLASS_DRINK = BooleanProperty.create("wine_glass_drink");
    private static final TagKey<Item> ALL_EFFECTS = TagKey.create(Registries.ITEM, new ResourceLocation("candlelight", "all_effects"));

    public TableSetBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(super.defaultBlockState()
                .setValue(WINE_GLASS, false)
                .setValue(GLASS, false)
                .setValue(CLOCHE, false)
                .setValue(NAPKIN, false)
                .setValue(GLASS_DRINK, false)
                .setValue(WINE_GLASS_DRINK, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TableSetBlockEntity(pos, state, this.size());
    }

    private static HashMap<Item, BooleanProperty> itemHashMap() {
        return Util.make(new HashMap<>(), map -> {
            map.put(ObjectRegistry.WINE_GLASS.get(), WINE_GLASS);
            map.put(ObjectRegistry.GLASS.get(), GLASS);
            map.put(ObjectRegistry.CLOCHE.get(), CLOCHE);
            map.put(ObjectRegistry.NAPKIN.get(), NAPKIN);
        });
    }

    private static Item getItemFromProperty(BooleanProperty property) {
        return itemHashMap().entrySet().stream()
                .filter(entry -> entry.getValue().equals(property))
                .map(Map.Entry::getKey)
                .findFirst().orElse(null);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.isEmpty()) {
            if (state.getValue(GLASS_DRINK)) {
                if (!world.isClientSide()) {
                    TableSetBlockEntity sbe = (TableSetBlockEntity) world.getBlockEntity(pos);
                    if (sbe != null) {
                        world.setBlockAndUpdate(pos, state.setValue(GLASS_DRINK, false));
                        ItemStack effectStack = sbe.getEffectStack();
                        if (!effectStack.isEmpty()) {
                            List<MobEffectInstance> effects = PotionUtils.getMobEffects(effectStack);
                            for (MobEffectInstance effect : effects) {
                                player.addEffect(new MobEffectInstance(effect.getEffect(), 6000, effect.getAmplifier()));
                            }
                            sbe.setEffectStack(ItemStack.EMPTY);
                        }
                    }
                }
                return InteractionResult.sidedSuccess(world.isClientSide());
            } else if (state.getValue(WINE_GLASS_DRINK)) {
                if (!world.isClientSide()) {
                    TableSetBlockEntity sbe = (TableSetBlockEntity) world.getBlockEntity(pos);
                    if (sbe != null) {
                        world.setBlockAndUpdate(pos, state.setValue(WINE_GLASS_DRINK, false));
                        ItemStack effectStack = sbe.getEffectStack();
                        if (!effectStack.isEmpty()) {
                            List<MobEffectInstance> effects = PotionUtils.getMobEffects(effectStack);
                            for (MobEffectInstance effect : effects) {
                                player.addEffect(new MobEffectInstance(effect.getEffect(), 6000, effect.getAmplifier()));
                            }
                            sbe.setEffectStack(ItemStack.EMPTY);
                        }
                    }
                }
                return InteractionResult.sidedSuccess(world.isClientSide());
            }
        }
        if (!stack.isEmpty() && stack.getItem().builtInRegistryHolder().is(ALL_EFFECTS)) {
            if (state.getValue(GLASS) && !state.getValue(GLASS_DRINK)) {
                if (!world.isClientSide()) {
                    TableSetBlockEntity sbe = (TableSetBlockEntity) world.getBlockEntity(pos);
                    if (sbe != null) {
                        world.setBlockAndUpdate(pos, state.setValue(GLASS_DRINK, true));
                        if (stack.hasTag()) {
                            sbe.setEffectStack(stack.copy());
                        }
                        if (!player.isCreative()) {
                            stack.shrink(1);
                        }
                    }
                }
                return InteractionResult.sidedSuccess(world.isClientSide());
            } else if (state.getValue(WINE_GLASS) && !state.getValue(WINE_GLASS_DRINK)) {
                if (!world.isClientSide()) {
                    TableSetBlockEntity sbe = (TableSetBlockEntity) world.getBlockEntity(pos);
                    if (sbe != null) {
                        world.setBlockAndUpdate(pos, state.setValue(WINE_GLASS_DRINK, true));
                        if (stack.hasTag()) {
                            sbe.setEffectStack(stack.copy());
                        }
                        if (!player.isCreative()) {
                            stack.shrink(1);
                        }
                    }
                }
                return InteractionResult.sidedSuccess(world.isClientSide());
            }
        }
        HashMap<Item, BooleanProperty> items = itemHashMap();
        if (player.isShiftKeyDown() && state.getValue(CLOCHE)) {
            if (!world.isClientSide()) {
                world.setBlockAndUpdate(pos, state.setValue(CLOCHE, false));
                ItemStack clocheItem = new ItemStack(getItemFromProperty(CLOCHE));
                if (!player.getInventory().add(clocheItem)) {
                    player.drop(clocheItem, false);
                }
            }
            return InteractionResult.sidedSuccess(world.isClientSide());
        }
        Item item = stack.getItem();
        if (!items.containsKey(item)) return super.use(state, world, pos, player, hand, hit);
        BooleanProperty property = items.get(item);
        if (state.getValue(property)) return InteractionResult.PASS;
        if (!world.isClientSide()) {
            world.setBlockAndUpdate(pos, state.setValue(property, true));
            if (!player.isCreative()) stack.shrink(1);
        }
        return InteractionResult.sidedSuccess(world.isClientSide());
    }

    @Override
    public void remove(Level world, BlockPos blockPos, Player player, StorageBlockEntity shelfBlockEntity, int i) {
        TableSetBlockEntity tsbe = (TableSetBlockEntity) shelfBlockEntity;
        BlockState state = world.getBlockState(blockPos);
        if (!state.getValue(CLOCHE) && !world.isClientSide()) {
            ItemStack itemStack = tsbe.removeStack(i);
            SoundEvent soundEvent = SoundEvents.GENERIC_EAT;
            world.playSound(null, blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (itemStack.isEdible()) {
                FoodProperties foodComponent = itemStack.getItem().getFoodProperties();
                assert foodComponent != null;
                player.getFoodData().eat(Math.round(foodComponent.getNutrition() * 1.3f), foodComponent.getSaturationModifier() * 1.3f);
                List<Pair<MobEffectInstance, Float>> list = foodComponent.getEffects();
                for (Pair<MobEffectInstance, Float> pair : list) {
                    if (pair.getFirst() == null || !(world.random.nextFloat() < pair.getSecond()))
                        continue;
                    player.addEffect(new MobEffectInstance(pair.getFirst()));
                }
            }
            world.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
        }
    }

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        List<ItemStack> list = super.getDrops(blockState, builder);
        PlateType type = blockState.getValue(PLATE_TYPE);
        switch (type) {
            case PLATE:
                list.add(new ItemStack(ObjectRegistry.PLATE.get()));
                break;
            case BOWL:
                list.add(new ItemStack(ObjectRegistry.BOWL.get()));
                break;
        }
        for (BooleanProperty property : itemHashMap().values()) {
            if (!blockState.getValue(property)) continue;
            Item item = getItemFromProperty(property);
            if (item == null) continue;
            list.add(new ItemStack(item));
        }
        return list;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public ResourceLocation type() {
        return StorageTypeRegistry.TABLE_SET;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[0];
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.isEdible();
    }

    @Override
    public int getSection(Float x, Float y) {
        return 0;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        VoxelShape shape = world.getBlockState(pos.below()).getShape(world, pos.below());
        return net.minecraft.world.level.block.Block.isFaceFull(shape, Direction.UP);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("tooltip.farm_and_charm.canbeplaced").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PLATE_TYPE);
        builder.add(WINE_GLASS, GLASS, CLOCHE, NAPKIN, GLASS_DRINK, WINE_GLASS_DRINK);
    }

    private VoxelShape makeBowlShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.25, 0, 0.125, 0.3125, 0.1875, 0.75), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.8125, 0, 0.125, 0.875, 0.1875, 0.75), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3125, 0, 0.125, 0.8125, 0.1875, 0.1875), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3125, 0, 0.6875, 0.8125, 0.1875, 0.75), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3125, 0, 0.1875, 0.8125, 0.0625, 0.6875), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.875, 0.1875, 0.3125, 1, 0.1875, 0.5625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.125, 0.1875, 0.3125, 0.25, 0.1875, 0.5625), BooleanOp.OR);
        return shape;
    }

    private VoxelShape makePlateShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.25, 0, 0.0625, 0.9375, 0.0625, 0.75), BooleanOp.OR);
        return shape;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        PlateType type = state.getValue(PLATE_TYPE);
        if (type.equals(PlateType.BOWL)) {
            return rotateShape(direction, makeBowlShape());
        } else {
            return rotateShape(direction, makePlateShape());
        }
    }

    private VoxelShape rotateShape(Direction direction, VoxelShape shape) {
        if (direction == Direction.NORTH) return shape;
        VoxelShape[] rotatedShapes = new VoxelShape[]{shape};
        for (int i = 0; i < (direction.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4; i++) {
            rotatedShapes[0] = rotateShapeClockwise(rotatedShapes[0]);
        }
        return rotatedShapes[0];
    }

    private VoxelShape rotateShapeClockwise(VoxelShape shape) {
        VoxelShape[] result = {Shapes.empty()};
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            double newMinX = 1 - maxZ;
            double newMaxX = 1 - minZ;
            result[0] = Shapes.or(result[0], Shapes.box(newMinX, minY, minX, newMaxX, maxY, maxX));
        });
        return result[0];
    }

    public enum PlateType implements StringRepresentable {
        PLATE("plate"),
        BOWL("bowl");
        private final String name;
        PlateType(String name) {
            this.name = name;
        }
        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
