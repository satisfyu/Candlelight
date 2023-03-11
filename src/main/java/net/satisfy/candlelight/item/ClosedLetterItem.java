package net.satisfy.candlelight.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.StringHelper;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.satisfy.candlelight.registry.ObjectRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ClosedLetterItem extends Item {
    public ClosedLetterItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            NbtCompound nbtCompound = stack.getNbt();
            String string = nbtCompound.getString("letter_title");
            if (!StringHelper.isEmpty(string)) {
                tooltip.add(Text.literal(string).formatted(Formatting.GRAY));
            }

            // tooltip.add(Text.translatable("book.generation." + nbtCompound.getInt("generation")).formatted(Formatting.GRAY));
        }

    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
//        if(world.isClient())
//        {
//            MinecraftClient.getInstance().setScreen(new BookScreen(new BookScreen.WrittenBookContents(itemStack)));
//        }
//        else
//        {
//            user.currentScreenHandler.sendContentUpdates();
//        }
        ItemStack output = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITTEN);
        if(itemStack.hasNbt())
        {
            output.setNbt(itemStack.getNbt().copy());
            output.getNbt().remove("letter_title");
        }
        user.setStackInHand(hand, output);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
