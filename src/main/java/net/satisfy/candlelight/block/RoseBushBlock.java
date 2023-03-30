package net.satisfy.candlelight.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class RoseBushBlock extends FlowerBlock {
    public RoseBushBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.damage(DamageSource.CACTUS, 1.0f);
            entity.slowMovement(state, new Vec3d(0.800000011920929, 0.75, 0.800000011920929));
        }
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.candlelight.rose.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
    }
}
