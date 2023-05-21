package satisfyu.candlelight;

import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.candlelight.networking.CandlelightMessages;
import satisfyu.candlelight.registry.*;
import satisfyu.candlelight.villager.memory.ModMemoryModuleType;
import satisfyu.candlelight.villager.poi.ModPointOfInterestTypes;

public class Candlelight {
    public static final String MOD_ID = "candlelight";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final CreativeModeTab CANDLELIGHT_TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "candlelight_tab"), () ->
            new ItemStack(ObjectRegistry.HEARTH.get()));
    
    public static void init() {
        RecipeTypes.init();
        ObjectRegistry.init();
        ScreenHandlerTypes.init();
        BlockEntityRegistry.init();
        ModMemoryModuleType.init();
        ModPointOfInterestTypes.initU();
        CandlelightMessages.registerC2SPackets();
    }

    public static void commonInit(){
        FlammableBlockRegistry.init();
    }
}
