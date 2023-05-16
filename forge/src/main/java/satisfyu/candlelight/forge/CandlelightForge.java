package satisfyu.candlelight.forge;

import dev.architectury.platform.forge.EventBuses;
import satisfyu.candlelight.Candlelight;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Candlelight.MOD_ID)
public class CandlelightForge {
    public CandlelightForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Candlelight.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Candlelight.init();
    }
}
