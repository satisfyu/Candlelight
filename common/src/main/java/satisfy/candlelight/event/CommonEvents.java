package satisfy.candlelight.event;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import satisfy.candlelight.registry.ObjectRegistry;
import satisfy.candlelight.registry.SoundEventsRegistry;

public class CommonEvents {
    public static void init() {
        PlayerEvent.ATTACK_ENTITY.register(CommonEvents::attack);
    }

    public static EventResult attack(Player player, Level level, Entity target, InteractionHand hand, @Nullable EntityHitResult result) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(ObjectRegistry.COOKING_PAN_ITEM.get())) {
            level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEventsRegistry.COOKING_PAN_HIT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            target.hurt(level.damageSources().generic(), 5.0F);
            itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            return EventResult.interruptTrue();
        }
        return EventResult.pass();
    }
}
