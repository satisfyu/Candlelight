package satisfy.candlelight.item.armor;

import de.cristelknight.doapi.common.item.ICustomArmor;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;

public class HelmetItem extends DyeableArmorItem implements ICustomArmor {
    public HelmetItem(ArmorMaterial material, Properties settings) {
        super(material, Type.HELMET, settings);
    }

    @Override
    public int getColor(ItemStack stack) {
        if (this.hasCustomColor(stack)) {
            return super.getColor(stack);
        }
        return 0xFFFFFFFF;
    }
}