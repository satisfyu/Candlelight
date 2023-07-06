package satisfyu.candlelight.item;

import de.cristelknight.doapi.common.item.CustomArmorItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public abstract class CustomModelArmorItem extends CustomArmorItem {
    public CustomModelArmorItem(ArmorMaterial material, Type type, Properties settings) {
        super(material, type, settings);
    }

    public abstract ResourceLocation getTexture();

    public abstract Float getOffset();
}
