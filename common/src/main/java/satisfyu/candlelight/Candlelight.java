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
import satisfyu.candlelight.villager.task.ModTaskListProvider;
import satisfyu.candlelight.world.feature.ConfiguredFeatures;

public class Candlelight {
    public static final String MOD_ID = "candlelight";
    // We can use this if we don't want to use DeferredRegister
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    // Registering a new creative tab
    public static final CreativeModeTab CANDLELIGHT_TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "candlelight_tab"), () ->
            new ItemStack(ObjectRegistry.HEARTH.get()));
    
    public static void init() {
        RecipeTypes.init();
        ObjectRegistry.init();
        StorageTypes.init();
        ScreenHandlerTypes.init();
        //ConfiguredFeatures.init();
        BlockEntityRegistry.init();
        ModMemoryModuleType.init();
        ModPointOfInterestTypes.initU();
        CandlelightMessages.registerC2SPackets();
    }

    public static void commonInit(){
        FlammableBlockRegistry.init();
    }
}
