package satisfyu.candlelight.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.List;

public class CookingHatItem extends CustomModelArmorItem implements CookArmorItem {
    public CookingHatItem(Properties settings) {
        super(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, settings);
    }

    @Override
    public ResourceLocation getTexture() {
        return new CandlelightIdentifier("textures/item/cooking_hat.png");
    }

    @Override
    public Float getOffset() {
        return -1.78f;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip(tooltip);
    }
}
