package net.satisfy.candlelight.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.satisfy.candlelight.client.ClientUtil;

public class WriteablePaperItem extends Item {
    public WriteablePaperItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        //user.useBook(itemStack, hand);
        if(world.isClient())
        {
            ClientUtil.setNoteEditScreen(user, itemStack, hand);
        }
        else
        {
            user.currentScreenHandler.sendContentUpdates();
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
