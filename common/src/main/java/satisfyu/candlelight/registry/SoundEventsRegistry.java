package satisfyu.candlelight.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.util.CandlelightIdentifier;

public class SoundEventsRegistry {

    private static final Registrar<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Candlelight.MOD_ID, Registry.SOUND_EVENT_REGISTRY).getRegistrar();
    public static final RegistrySupplier<SoundEvent> BLOCK_COOKING_POT_JUICE_BOILING = create("cooking_pot.juice_boiling");
    public static final RegistrySupplier<SoundEvent> BLOCK_COOKING_PAN_FRYING = create("cooking_pan.pan_frying");
    public static final RegistrySupplier<SoundEvent> BLOCK_FAUCET = create("kitchen_sink.faucet");
    public static final RegistrySupplier<SoundEvent> DRAWER_OPEN = create("drawer.open");
    public static final RegistrySupplier<SoundEvent> DRAWER_CLOSE = create("drawer.close");
    public static final RegistrySupplier<SoundEvent> CABINET_OPEN = create("cabinet.open");
    public static final RegistrySupplier<SoundEvent> CABINET_CLOSE = create("cabinet.close");


    private static RegistrySupplier<SoundEvent> create(String name) {
        final ResourceLocation id = new CandlelightIdentifier(name);
        return SOUND_EVENTS.register(id, () -> new SoundEvent(id));
    }

    public static void init() {
        Candlelight.LOGGER.debug("Registering Sounds for " + Candlelight.MOD_ID);
    }
}
