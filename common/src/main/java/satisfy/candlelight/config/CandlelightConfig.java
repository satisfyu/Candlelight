package satisfy.candlelight.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.cristelknight.doapi.config.jankson.config.CommentedConfig;

import java.util.HashMap;

public record CandlelightConfig(boolean enableChefSetBonus) implements CommentedConfig<CandlelightConfig> {
    private static CandlelightConfig INSTANCE;

    public static final Codec<CandlelightConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("enable_chef_armor_set_bonus").orElse(true).forGetter(CandlelightConfig::enableChefSetBonus)
            ).apply(instance, CandlelightConfig::new));

    public static CandlelightConfig getActiveInstance() {
        if (INSTANCE == null) {
            INSTANCE = loadConfig();
        }
        return INSTANCE;
    }

    private static CandlelightConfig loadConfig() {
        return new CandlelightConfig(true);
    }

    @Override
    public HashMap<String, String> getComments() {
        HashMap<String, String> comments = new HashMap<>();
        comments.put("enable_chef_armor_set_bonus", "Whether the chef armor should give a set bonus");
        comments.put("enable_candlelight_tomatoes", "Whether candlelight tomatoes should be enabled");
        comments.put("enable_candlelight_lettuce", "Whether candlelight tomatoes should be enabled");

        return comments;
    }
    @Override
    public String getHeader() {
        return """
                Candlelight Config
                               
                ===========
                Discord: https://discord.gg/Vqu6wYZwdZ
                Modrinth: https://modrinth.com/mod/lets-do-candlelight
                CurseForge: https://www.curseforge.com/minecraft/mc-mods/lets-do-candlelight
                """;
    }

    @Override
    public String getSubPath() {
        return "candlelight/config";
    }

    @Override
    public CandlelightConfig getInstance() {
        return getActiveInstance();
    }

    @Override
    public CandlelightConfig getDefault() {
        return new CandlelightConfig(true);
    }

    @Override
    public Codec<CandlelightConfig> getCodec() {
        return CODEC;
    }

    @Override
    public boolean isSorted() {
        return false;
    }

    @Override
    public void setInstance(CandlelightConfig instance) {
        INSTANCE = instance;
    }
}
