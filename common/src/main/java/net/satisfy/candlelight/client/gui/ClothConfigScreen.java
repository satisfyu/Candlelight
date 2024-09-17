package net.satisfy.candlelight.client.gui;

import de.cristelknight.doapi.DoApiRL;
import de.cristelknight.doapi.config.cloth.CCUtil;
import de.cristelknight.doapi.config.cloth.LinkEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import me.shedaniel.clothconfig2.gui.entries.TextListEntry;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.config.CandlelightConfig;

@SuppressWarnings("all")
public class ClothConfigScreen {
    private static Screen lastScreen;
    private static WidgetSprites DISCORD = new WidgetSprites(DoApiRL.asResourceLocation("textures/gui/dc.png"), DoApiRL.asResourceLocation("textures/gui/dc.png"));
    private static WidgetSprites CF = new WidgetSprites(DoApiRL.asResourceLocation("textures/gui/cf.png"), DoApiRL.asResourceLocation("textures/gui/cf.png"));

    public static Screen create(Screen parent) {
        lastScreen = parent;
        CandlelightConfig config = CandlelightConfig.getActiveInstance().getConfig();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setDefaultBackgroundTexture(ResourceLocation.withDefaultNamespace("textures/block/stone.png"))
                .setTitle(Component.translatable(Candlelight.MOD_ID + ".config.title").withStyle(ChatFormatting.BOLD));


        ConfigEntries entries = new ConfigEntries(builder.entryBuilder(), config, builder.getOrCreateCategory(CCUtil.categoryName("main", Candlelight.MOD_ID)));
        builder.setSavingRunnable(() -> {
            CandlelightConfig.getActiveInstance().setInstance(entries.createConfig());
            CandlelightConfig.getActiveInstance().getConfig(true, true);
        });
        return builder.build();
    }

    public static void linkButtons(String MOD_ID, ConfigCategory category, ConfigEntryBuilder builder, String dcLink, String cfLink, Screen lastScreen) {
        if (lastScreen == null) lastScreen = Minecraft.getInstance().screen;

        TextListEntry tle = builder.startTextDescription(Component.literal(" ")).build();
        category.addEntry(tle);
        Screen finalLastScreen = lastScreen;
        category.addEntry(new LinkEntry(CCUtil.entryName(MOD_ID, "dc"), buttonWidget -> Minecraft.getInstance().setScreen(new ConfirmLinkScreen(confirmed -> {
            if (confirmed) {
                Util.getPlatform().openUri(dcLink);
            }
            Minecraft.getInstance().setScreen(create(finalLastScreen));
        }, dcLink, true)), DISCORD, 3));
        category.addEntry(tle);
        category.addEntry(new LinkEntry(CCUtil.entryName(MOD_ID, "h"), buttonWidget -> Minecraft.getInstance().setScreen(new ConfirmLinkScreen(confirmed -> {
            if (confirmed) {
                Util.getPlatform().openUri(cfLink);
            }
            Minecraft.getInstance().setScreen(create(finalLastScreen));
        }, cfLink, true)), CF, 10));
    }

    private static class ConfigEntries {
        private final ConfigEntryBuilder builder;
        private final ConfigCategory category;
        private final BooleanListEntry enableChefSetBonus;


        public ConfigEntries(ConfigEntryBuilder builder, CandlelightConfig config, ConfigCategory category) {
            this.builder = builder;
            this.category = category;

            SubCategoryBuilder Chef = new SubCategoryBuilder(Component.empty(), Component.translatable("config.candlelight.subCategory.chef"));
            enableChefSetBonus = createBooleanField("enableChefSetBonus", config.enableChefSetBonus(), "Whether the chef armor should give a set bonus", Chef);

            category.addEntry(Chef.build());
            linkButtons(Candlelight.MOD_ID, category, builder, "https://discord.gg/Vqu6wYZwdZ", "https://www.curseforge.com/minecraft/mc-mods/lets-do-candlelight", lastScreen);
        }


        public CandlelightConfig createConfig() {
            return new CandlelightConfig(enableChefSetBonus.getValue());
        }


        private BooleanListEntry createBooleanField(String key, boolean value, String tooltip, SubCategoryBuilder subCategoryBuilder) {
            BooleanListEntry entry = builder.startBooleanToggle(Component.translatable(Candlelight.MOD_ID + ".config." + key), value)
                    .setDefaultValue(() -> true)
                    .setTooltip(Component.literal(tooltip))
                    .build();
            subCategoryBuilder.add(entry);
            return entry;
        }


        public IntegerListEntry createIntField(String id, int value, int defaultValue, SubCategoryBuilder subCategoryBuilder, int min, int max) {
            IntegerListEntry e = CCUtil.createIntField(Candlelight.MOD_ID, id, value, defaultValue, builder).setMaximum(max).setMinimum(min);

            if (subCategoryBuilder == null) category.addEntry(e);
            else subCategoryBuilder.add(e);

            return e;
        }
    }
}
