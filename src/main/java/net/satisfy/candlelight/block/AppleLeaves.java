package net.satisfy.candlelight.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AppleLeaves extends LeavesBlock {

    public static final BooleanProperty VARIANT = BooleanProperty.of("can_have_apples");

    public static final BooleanProperty HAS_APPLES = BooleanProperty.of("has_apples");
    public AppleLeaves(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PERSISTENT, false).with(DISTANCE, 7).with(VARIANT, false).with(HAS_APPLES, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if(state.get(VARIANT) && state.get(HAS_APPLES) && stack.getItem() instanceof ShearsItem) {
            if(!world.isClient()) {
                stack.damage(1, player, playerEntity ->  playerEntity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                AppleLeaves.dropStack(world, pos, hit.getSide(), new ItemStack(Items.APPLE, world.getRandom().nextBetween(1, 3)));
                world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1F, 1F);
                world.setBlockState(pos, state.with(HAS_APPLES, false));
            }
            return ActionResult.success(world.isClient());
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        if(state.get(VARIANT) && !state.get(HAS_APPLES)) return true;
        return super.hasRandomTicks(state);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        PlayerEntity p = ctx.getPlayer();
        boolean b = p != null && p.isSneaking();
        return super.getPlacementState(ctx).with(VARIANT, ctx.getPlayer().getAbilities().creativeMode && b);
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(VARIANT, HAS_APPLES);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(state.get(VARIANT) && !state.get(HAS_APPLES)) world.setBlockState(pos, state.with(HAS_APPLES, true));
        super.randomTick(state, world, pos, random);
    }
}
