package satisfyu.candlelight.block;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.registry.StorageTypesRegistry;
import satisfyu.candlelight.util.CandlelightGeneralUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ToolRackBlock extends ShelfBlock {
    public ToolRackBlock(Properties settings) {
        super(settings);
    }

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.8125, 0.875, 1, 0.9375, 1), BooleanOp.OR);
        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            map.put(direction, CandlelightGeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    private ItemStack[] slots = new ItemStack[3];

    @Override
    public boolean canInsertStack(ItemStack stack) {
        // Check if the item being placed is a cooking pan
        if (stack.getItem() == ObjectRegistry.COOKING_PAN.get().asItem()) {
            // Check if any of the slots already have a cooking pan
            for (ItemStack slotStack : slots) {
                if (slotStack != null && slotStack.getItem() == ObjectRegistry.COOKING_PAN.get().asItem()) {
                    return false; // Prevent placing another cooking pan
                }
            }
        } else {
            // Check if any of the slots already have a cooking pan
            for (ItemStack slotStack : slots) {
                if (slotStack != null && slotStack.getItem() == ObjectRegistry.COOKING_PAN.get().asItem()) {
                    return false; // Prevent placing other items if a cooking pan is present
                }
            }
        }

        // Update the slot information
        int slot = getEmptySlot();
        if (slot >= 0 && slot < 3) {
            slots[slot] = stack;
        }

        return true;
    }

    // Helper method to find an empty slot
    private int getEmptySlot() {
        for (int i = 0; i < 3; i++) {
            if (slots[i] == null) {
                return i;
            }
        }
        return -1; // No empty slots
    }



    @Override
    public ResourceLocation type() {
        return StorageTypesRegistry.TOOL_RACK;
    }

    @Override
    public int getSection(Float f, Float y) {
        float oneS = 1.0f / 3;
        int nSection = (int) (f / oneS);
        return 2 - nSection;
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.candlelight.decoration.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        tooltip.add(Component.translatable("block.candlelight.decoration.tool_rack").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));

    }
}