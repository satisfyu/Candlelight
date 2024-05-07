package satisfy.candlelight.forge.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import satisfy.candlelight.Candlelight;
import satisfy.candlelight.registry.ObjectRegistry;


import java.lang.reflect.InvocationTargetException;

public class CandlelightForgeVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Candlelight.MOD_ID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Candlelight.MOD_ID);

    public static final RegistryObject<PoiType> COOK_POI = POI_TYPES.register("cook_poi", () ->
            new PoiType(ImmutableSet.copyOf(ObjectRegistry.COOKING_POT.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final RegistryObject<VillagerProfession> COOK = VILLAGER_PROFESSIONS.register("cook", () ->
            new VillagerProfession("cook", x -> x.get() == COOK_POI.get(), x -> x.get() == COOK_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_FARMER));


    public static void registerPOIs(){
        try {
            ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class).invoke(null, COOK_POI.get());
        } catch (InvocationTargetException | IllegalAccessException exception){
            exception.printStackTrace();
        }
    }

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }


}