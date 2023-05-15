package net.satisfy.candlelight;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.satisfy.candlelight.networking.CandlelightMessages;
import net.satisfy.candlelight.registry.*;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import net.satisfy.candlelight.villager.memory.ModMemoryModuleType;
import net.satisfy.candlelight.villager.poi.ModPointOfInterestTypes;
import net.satisfy.candlelight.villager.task.ModTaskListProvider;
import net.satisfy.candlelight.world.feature.ConfiguredFeatures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Candlelight implements ModInitializer {
    public static final String MOD_ID = "candlelight";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final ItemGroup CANDLELIGHT_TAB = FabricItemGroupBuilder.build(new CandlelightIdentifier("candlelight_tab"), () -> new ItemStack(ObjectRegistry.HEARTH));


    @Override
    public void onInitialize() {
        RecipeTypes.init();
        ObjectRegistry.init();
        StorageTypes.init();
        ScreenHandlerTypes.init();
        ConfiguredFeatures.init();
        CandlelightEntityTypes.init();
        ModTaskListProvider.init();
        ModMemoryModuleType.init();
        ModPointOfInterestTypes.init();
        ModTaskListProvider.init();
        CandlelightMessages.registerC2SPackets();

        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new CandlelightIdentifier("apple_leaves"), container, ResourcePackActivationType.DEFAULT_ENABLED);
        });
    }
}