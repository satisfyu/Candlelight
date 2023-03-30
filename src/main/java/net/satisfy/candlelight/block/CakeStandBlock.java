package net.satisfy.candlelight.block;


import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.satisfy.candlelight.registry.StorageTypes;
import satisfyu.vinery.block.StorageBlock;
import satisfyu.vinery.block.entity.StorageBlockEntity;

import java.util.LinkedList;
import java.util.List;

public class CakeStandBlock extends StorageBlock {

    public static VoxelShape SHAPE = makeShape();

    public static VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.5000625, 0.125, 0.125, 1.0000625, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.5000625, 0.125, 0.875, 1.0000625, 0.125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.5000625, 0.875, 0.875, 1.0000625, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 1.0000625, 0.125, 0.875, 1.0000625, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.5000625, 0.125, 0.875, 1.0000625, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 1, 0.4375, 0.5625, 1.0625, 0.5625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.125, 0.4375, 0.5625, 0.4375, 0.5625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.375, 0.25, 0.75, 0.4375, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.125, 0.875, 0.5, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.34375, 0.0625, 0.34375, 0.65625, 0.1875, 0.65625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.4375, 0.125, 0.9375, 0.5, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.4375, 0.125, 0.125, 0.5, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.0625, 0.875, 0.5, 0.125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.875, 0.875, 0.5, 0.9375));
        return shape;
    }

    public CakeStandBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public int findFirstEmpty(DefaultedList<ItemStack> inv){
        for(int i = 0; i < size(); i++){
            ItemStack stack = inv.get(i);
            if (stack.isEmpty()) return i;
        }
        return Integer.MIN_VALUE;
    }

    public int findFirstFull(DefaultedList<ItemStack> inv){
        for(int i = 0; i < size(); i++){
            ItemStack stack = inv.get(i);
            if (!stack.isEmpty()) return i;
        }
        return Integer.MIN_VALUE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof StorageBlockEntity shelfBlockEntity) {
            List<Item> items = new LinkedList<>();
            shelfBlockEntity.getInventory().forEach(stack -> {
                if (!stack.isEmpty()) items.add(stack.getItem());
            });
            if (player.isSneaking()) {
                boolean cCake = false;
                for (Item item : items) {
                    if (item instanceof BlockItem) {
                        cCake = true;
                        break;
                    }
                }
                if (cCake) {
                    remove(world, pos, player, shelfBlockEntity, 0);
                    return ActionResult.success(world.isClient());
                } else {
                    int i = findFirstFull(shelfBlockEntity.getInventory());
                    if (i != Integer.MIN_VALUE) {
                        remove(world, pos, player, shelfBlockEntity, i);
                        return ActionResult.success(world.isClient());
                    }
                }
            } else {
                ItemStack stack = player.getStackInHand(hand);
                if(!stack.isEmpty() && canInsertStack(stack)) {
                    if (stack.getItem() instanceof BlockItem) {
                        if (items.isEmpty()) {
                            add(world, pos, player, shelfBlockEntity, stack, 0);
                            return ActionResult.success(world.isClient());
                        }
                    } else {
                        if(!(shelfBlockEntity.getInventory().get(0).getItem() instanceof BlockItem)){
                            int i = findFirstEmpty(shelfBlockEntity.getInventory());
                            if (i != Integer.MIN_VALUE) {
                                add(world, pos, player, shelfBlockEntity, stack, i);
                                return ActionResult.success(world.isClient());
                            }
                        }
                    }
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public Identifier type() {
        return StorageTypes.CAKE_STAND;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[0];
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.isFood() || stack.getItem() instanceof BlockItem;
    }

    @Override
    public int getSection(Float x, Float y) {
        return 0;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.candlelight.canbeplaced.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
        tooltip.add(Text.translatable("block.candlelight.cakestand.tooltip").formatted(Formatting.ITALIC, Formatting.WHITE));
        tooltip.add(Text.translatable("block.candlelight.cakestand2.tooltip").formatted(Formatting.ITALIC, Formatting.WHITE));
    }
}
