package satisfyu.candlelight.forge;

import satisfyu.candlelight.CandlelightExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class CandlelightExpectPlatformImpl {
    /**
     * This is our actual method to {@link CandlelightExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
