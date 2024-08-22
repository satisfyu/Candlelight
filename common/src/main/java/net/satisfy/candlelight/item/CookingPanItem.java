package net.satisfy.candlelight.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.block.CookingPanBlock;
import net.satisfy.candlelight.util.CandlelightTiers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CookingPanItem extends BlockItem {
    public static final Tier COOKING_PAN_TIER = CandlelightTiers.COPPER;

    public CookingPanItem(Block block, Item.Properties properties) {
        super(block, properties.durability(COOKING_PAN_TIER.getUses()));
        float attackDamage = 2.0F + COOKING_PAN_TIER.getAttackDamageBonus();
        ItemAttributeModifiers.Builder attributeModifiers = ItemAttributeModifiers.builder();
        attributeModifiers.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        attributeModifiers.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);

        properties.attributes(attributeModifiers.build());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, attacker.getEquipmentSlotForItem(stack));
        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return COOKING_PAN_TIER.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(1, entity, entity.getEquipmentSlotForItem(stack));
        }

        return true;
    }

    @Override
    public @NotNull InteractionResult place(BlockPlaceContext context) {
        Player player = context.getPlayer();
        if (player != null && player.isShiftKeyDown()) {
            return super.place(context);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        super.inventoryTick(itemStack, level, entity, i, bl);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext blockPlaceContext) {
        BlockState state = super.getPlacementState(blockPlaceContext);
        if (state != null)
            state = state.setValue(CookingPanBlock.DAMAGE, blockPlaceContext.getItemInHand().getDamageValue());
        return state;
    }

    @Override
    public int getEnchantmentValue() {
        return COOKING_PAN_TIER.getEnchantmentValue();
    }
}