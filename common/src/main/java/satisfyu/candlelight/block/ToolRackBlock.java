package satisfyu.candlelight.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.registry.StorageTypesRegistry;

public class ToolRackBlock extends ShelfBlock {


    public ToolRackBlock(Properties settings) {
        super(settings);
    }


    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.getItem() instanceof TieredItem || stack.getItem() == ObjectRegistry.COOKING_PAN.get().asItem();
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
}
