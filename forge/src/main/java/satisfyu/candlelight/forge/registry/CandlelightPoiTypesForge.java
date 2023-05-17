package satisfyu.candlelight.forge.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.lang.reflect.InvocationTargetException;

public class CandlelightPoiTypesForge {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Candlelight.MOD_ID);


    public static final RegistryObject<PoiType> SHOP = POI_TYPES.register("shop", () ->
            new PoiType(ImmutableSet.copyOf(ObjectRegistry.WINE_STATION.get().getStateDefinition().getPossibleStates()), 100, 1));


    public static void registerPOIs(){
        try {
            ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class).invoke(null, SHOP.get());
        } catch (InvocationTargetException | IllegalAccessException exception){
            exception.printStackTrace();
        }
    }

    public static void init(IEventBus eventBus){
        POI_TYPES.register(eventBus);
    }

}
