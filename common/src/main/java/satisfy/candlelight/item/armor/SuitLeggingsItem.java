package satisfy.candlelight.item.armor;

import de.cristelknight.doapi.common.item.ICustomArmor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.candlelight.registry.ArmorRegistry;

import java.util.List;

public class SuitLeggingsItem extends DyeableArmorItem implements ICustomArmor {
    public SuitLeggingsItem(ArmorMaterial material, Properties settings) {
        super(material, Type.LEGGINGS, settings);
    }

    @Override
    public int getColor(ItemStack stack) {
        if (this.hasCustomColor(stack)) {
            return super.getColor(stack);
        }
        return 0xB0E0E6FF;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        if (null != world && world.isClientSide()) {
            ArmorRegistry.appendSuitTooltip(tooltip);
        }
    }
}