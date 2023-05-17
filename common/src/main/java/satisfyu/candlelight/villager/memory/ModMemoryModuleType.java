package satisfyu.candlelight.villager.memory;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import satisfyu.candlelight.Candlelight;

import java.util.Optional;


public class ModMemoryModuleType {

    public static DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registry.MEMORY_MODULE_TYPE_REGISTRY);
    public static final RegistrySupplier<MemoryModuleType<GlobalPos>> SHOP = register("shop", GlobalPos.CODEC);

    public static final RegistrySupplier<MemoryModuleType<Long>> LAST_SHOPED = register("last_shoped", Codec.LONG);


    private static <U> RegistrySupplier<MemoryModuleType<U>> register(String name, Codec<U> codec) {
        return MEMORY_MODULE_TYPES.register(name, () -> new MemoryModuleType<>(Optional.of(codec)));
    }

    public static void init() {
        MEMORY_MODULE_TYPES.register();
        Candlelight.LOGGER.debug("Register " + ModMemoryModuleType.class);
    }
}
