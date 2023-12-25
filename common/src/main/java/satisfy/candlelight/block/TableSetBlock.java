package satisfy.candlelight.block;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.common.block.StorageBlock;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import satisfy.candlelight.registry.StorageTypesRegistry;
import satisfy.candlelight.registry.ObjectRegistry;
import satisfy.candlelight.util.CandlelightGeneralUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TableSetBlock extends StorageBlock {
    public static final EnumProperty<PlateType> PLATE_TYPE = EnumProperty.create("plate", PlateType.class);
    private static final Supplier<VoxelShape> voxelShapeSupplier;

    public static final Map<Direction, VoxelShape> SHAPE;

    public TableSetBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(super.defaultBlockState().setValue(PLATE_TYPE, PlateType.PLATE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        PlateType type = state.getValue(PLATE_TYPE);
        if(stack.isEmpty() && player.isShiftKeyDown()) world.destroyBlock(pos, true, player);
        else if(type == PlateType.ALL) return super.use(state, world, pos, player, hand, hit);
        else if(type != PlateType.GLASS && item.equals(ObjectRegistry.GLASS.get())){
            if(!world.isClientSide()){
                if(type == PlateType.PLATE) world.setBlockAndUpdate(pos, state.setValue(PLATE_TYPE, PlateType.GLASS));
                else world.setBlockAndUpdate(pos, state.setValue(PLATE_TYPE, PlateType.ALL));
                if (!player.isCreative()) stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(world.isClientSide());
        }
        else if(type != PlateType.NAPKIN && item.equals(ObjectRegistry.NAPKIN.get())){
            if(!world.isClientSide()){
                if(type == PlateType.PLATE) world.setBlockAndUpdate(pos, state.setValue(PLATE_TYPE, PlateType.NAPKIN));
                else world.setBlockAndUpdate(pos, state.setValue(PLATE_TYPE, PlateType.ALL));
                if (!player.isCreative()) stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(world.isClientSide());
        }
        return super.use(state, world, pos, player, hand, hit);
    }


    @Override
    public void remove(Level world, BlockPos blockPos, Player player, StorageBlockEntity shelfBlockEntity, int i) {
        if (!world.isClientSide()) {
            ItemStack itemStack = shelfBlockEntity.removeStack(i);
            SoundEvent soundEvent = SoundEvents.GENERIC_EAT;
            world.playSound(null, blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (itemStack.isEdible()) {
                FoodProperties foodComponent = itemStack.getItem().getFoodProperties();
                player.getFoodData().eat(Math.round(foodComponent.getNutrition() * 1.3f), foodComponent.getSaturationModifier() * 1.3f);
                List<Pair<MobEffectInstance, Float>> list = foodComponent.getEffects();
                for (Pair<MobEffectInstance, Float> pair : list) {
                    if (pair.getFirst() == null || !(world.random.nextFloat() < pair.getSecond()))
                        continue;
                    player.addEffect(new MobEffectInstance(pair.getFirst()));
                }
            }
        }
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public ResourceLocation type() {
        return StorageTypesRegistry.TABLE_SET;
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PLATE_TYPE);
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
        Direction direction = Direction.UP;
        return Block.isFaceFull(shape, direction);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    private enum PlateType implements StringRepresentable {
        PLATE("plate"),
        GLASS("glass"),
        NAPKIN("napkin"),
        ALL("all");
        private final String name;
        PlateType(String name) {
            this.name = name;
        }
        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.candlelight.canbeplaced.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        tooltip.add(Component.translatable("block.candlelight.table_set.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GOLD));
        tooltip.add(Component.translatable("block.candlelight.table_set2.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GOLD));
    }

    static {
        voxelShapeSupplier = () -> {
            VoxelShape shape = Shapes.empty();
            shape = Shapes.joinUnoptimized(shape, Shapes.box(0.25, 0, 0.0625, 0.9375, 0.0625, 0.75), BooleanOp.OR);

            return shape;
        };
        SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                map.put(direction, CandlelightGeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
            }
        });
    }
}
