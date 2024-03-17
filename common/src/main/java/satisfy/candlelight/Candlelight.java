package satisfy.candlelight;

import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfy.candlelight.event.CommonEvents;
import satisfy.candlelight.networking.CandlelightMessages;
import satisfy.candlelight.registry.*;

public class Candlelight {
    public static final String MOD_ID = "candlelight";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final CreativeModeTab CANDLELIGHT_TAB = CreativeTabRegistry.create(Component.translatable("creativetab.candlelight.tab"), () ->
            new ItemStack(ObjectRegistry.HEARTH.get()));
    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
    
    public static void init() {
        RecipeTypeRegistry.init();
        ObjectRegistry.init();
        ScreenHandlerTypeRegistry.init();
        BlockEntityRegistry.init();
        SoundEventsRegistry.init();
        CommonEvents.init();
        TabRegistry.init();
        CandlelightMessages.registerC2SPackets();
    }

    public static void commonInit(){
        FlammableBlockRegistry.init();
    }
}
