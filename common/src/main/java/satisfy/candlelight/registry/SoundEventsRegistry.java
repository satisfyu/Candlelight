package satisfy.candlelight.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import satisfy.candlelight.Candlelight;
import satisfy.candlelight.util.CandlelightIdentifier;

public class SoundEventsRegistry {

    private static final Registrar<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Candlelight.MOD_ID, Registries.SOUND_EVENT).getRegistrar();
    public static final RegistrySupplier<SoundEvent> COOKING_POT_BOILING = create("cooking_pot_boiling");
    public static final RegistrySupplier<SoundEvent> COOKING_PAN_COOKING = create("cooking_pan_cooking");
    public static final RegistrySupplier<SoundEvent> COOKING_PAN_HIT = create("cooking_pan_hit");
    public static final RegistrySupplier<SoundEvent> DRAWER_OPEN = create("drawer_open");
    public static final RegistrySupplier<SoundEvent> DRAWER_CLOSE = create("drawer_close");
    public static final RegistrySupplier<SoundEvent> CABINET_OPEN = create("cabinet_open");
    public static final RegistrySupplier<SoundEvent> CABINET_CLOSE = create("cabinet_close");


    private static RegistrySupplier<SoundEvent> create(String name) {
        final ResourceLocation id = new CandlelightIdentifier(name);
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void init() {
        Candlelight.LOGGER.debug("Registering Sounds for " + Candlelight.MOD_ID);
    }
}
