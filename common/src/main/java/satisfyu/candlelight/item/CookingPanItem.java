package satisfyu.candlelight.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import satisfyu.candlelight.util.CopperTiers;

@SuppressWarnings({"deprecation"})
public class CookingPanItem extends BlockItem {
    private final Multimap<Attribute, AttributeModifier> toolAttributes;
    private final SoundEvent panHit;

    public CookingPanItem(Block block, Item.Properties properties, SoundEvent panHit) {
        super(block, properties.defaultDurability(COOKING_PAN_TIER.getUses()));
        float attackDamage = 2.0F + COOKING_PAN_TIER.getAttackDamageBonus();
        this.panHit = panHit;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2F, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamage, AttributeModifier.Operation.ADDITION));
        this.toolAttributes = builder.build();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        Level world = attacker.level;
        world.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), panHit, SoundSource.PLAYERS, 5.0F, 5.0F);
        return true;
    }

    public static final CopperTiers COOKING_PAN_TIER = CopperTiers.COPPER;

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return COOKING_PAN_TIER.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(1, entity, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        Player player = context.getPlayer();
        if (player != null && player.isShiftKeyDown()) {
            return super.place(context);
        }
        return InteractionResult.PASS;
    }


    @Override
    public int getEnchantmentValue() {
        return COOKING_PAN_TIER.getEnchantmentValue();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.toolAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }
}