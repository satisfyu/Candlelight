package net.satisfy.candlelight.villager.memory;

import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.registry.Registry;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.util.CandlelightIdentifier;

import java.util.Optional;


public class ModMemoryModuleType<U> extends MemoryModuleType<U>{
    public static final MemoryModuleType<GlobalPos> SHOP = register("shop", GlobalPos.CODEC);

    public static final MemoryModuleType<Long> LAST_SHOPED = register("last_shoped", Codec.LONG);

    public ModMemoryModuleType(Optional<Codec<U>> uCodec) {
        super(uCodec);
    }

    private static <U> MemoryModuleType register(String name, Codec<U> codec) {
        return Registry.register(Registry.MEMORY_MODULE_TYPE, new CandlelightIdentifier(name), new MemoryModuleType(Optional.of(codec)));
    }
    private static MemoryModuleType register(String name) {
        return Registry.register(Registry.MEMORY_MODULE_TYPE, new CandlelightIdentifier(name), new MemoryModuleType(Optional.empty()));
    }

    public static void init() {
        Candlelight.LOGGER.debug("Register " + ModMemoryModuleType.class);
    }
}
