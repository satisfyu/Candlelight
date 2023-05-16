package satisfyu.candlelight.fabric;

import satisfyu.candlelight.CandlelightExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class CandlelightExpectPlatformImpl {
    /**
     * This is our actual method to {@link CandlelightExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
