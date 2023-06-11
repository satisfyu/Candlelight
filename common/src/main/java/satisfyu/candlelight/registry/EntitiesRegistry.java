package satisfyu.candlelight.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.entity.chair.ChairEntity;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.function.Supplier;

public class EntitiesRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<EntityType<ChairEntity>> CHAIR = create("chair", () -> EntityType.Builder.of(ChairEntity::new, MobCategory.MISC).sized(0.001F, 0.001F).build(new CandlelightIdentifier("chair").toString()));

    public static <T extends EntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return ENTITY_TYPES.register(new CandlelightIdentifier(path), type);
    }

    public static void init() {
        Candlelight.LOGGER.debug("Registering Entities for " + Candlelight.MOD_ID);
        ENTITY_TYPES.register();
    }
}
