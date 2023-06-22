package satisfyu.candlelight.item;

import de.cristelknight.doapi.item.CustomHatItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import satisfyu.candlelight.registry.ArmorMaterialRegistry;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.List;

public class CookingHatItem extends CustomHatItem implements CookArmorItem {
    public CookingHatItem(ArmorMaterial material, Properties settings) {
        super(material, EquipmentSlot.HEAD, settings);
    }

    @Override
    public ResourceLocation getTexture() {
        return new CandlelightIdentifier("textures/models/armor/cook.png");
    }

    @Override
    public Float getOffset() {
        return -1.85f;
    }
}
