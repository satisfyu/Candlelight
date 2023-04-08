package net.satisfy.candlelight.client.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public class SignedPaperScreen extends Screen{
    public static final Contents EMPTY_PROVIDER = new Contents() {
        public int getPageCount() {
            return 0;
        }

        public StringVisitable getPageUnchecked(int index) {
            return StringVisitable.EMPTY;
        }
    };
    public static final Identifier BOOK_TEXTURE = new CandlelightIdentifier("textures/gui/note_paper_gui.png");
    private final Contents contents;
    private int pageIndex;
    private List<OrderedText> cachedPage;
    private int cachedPageIndex;

    public SignedPaperScreen(Contents contents) {
        super(NarratorManager.EMPTY);
        this.cachedPage = Collections.emptyList();
        this.cachedPageIndex = -1;
        this.contents = contents;
    }

    public boolean setPage(int index) {
        int i = MathHelper.clamp(index, 0, this.contents.getPageCount() - 1);
        if (i != this.pageIndex) {
            this.pageIndex = i;
            this.cachedPageIndex = -1;
            return true;
        } else {
            return false;
        }
    }

    protected boolean jumpToPage(int page) {
        return this.setPage(page);
    }

    protected void init() {
        this.addCloseButton();
    }

    protected void addCloseButton() {
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, 196, 200, 20, ScreenTexts.DONE, (button) -> {
            this.client.setScreen(null);
        }));
    }
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);
        int i = (this.width - 192) / 2;
        this.drawTexture(matrices, i, 2, 0, 0, 192, 192);
        if (this.cachedPageIndex != this.pageIndex) {
            StringVisitable stringVisitable = this.contents.getPage(this.pageIndex);
            this.cachedPage = this.textRenderer.wrapLines(stringVisitable, 114);
        }

        this.cachedPageIndex = this.pageIndex;
        Objects.requireNonNull(this.textRenderer);
        int l = Math.min(128 / 9, this.cachedPage.size());

        for(int m = 0; m < l; ++m) {
            OrderedText orderedText = this.cachedPage.get(m);
            TextRenderer var10000 = this.textRenderer;
            float var10003 = (float)(i + 36);
            Objects.requireNonNull(this.textRenderer);
            var10000.draw(matrices, orderedText, var10003, (float)(32 + m * 9), 0);
        }

        Style style = this.getTextStyleAt(mouseX, mouseY);
        if (style != null) {
            this.renderTextHoverEffect(matrices, style, mouseX, mouseY);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            Style style = this.getTextStyleAt(mouseX, mouseY);
            if (style != null && this.handleTextClick(style)) {
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean handleTextClick(Style style) {
        ClickEvent clickEvent = style.getClickEvent();
        if (clickEvent == null) {
            return false;
        } else if (clickEvent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            String string = clickEvent.getValue();

            try {
                int i = Integer.parseInt(string) - 1;
                return this.jumpToPage(i);
            } catch (Exception var5) {
                return false;
            }
        } else {
            boolean bl = super.handleTextClick(style);
            if (bl && clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.closeScreen();
            }

            return bl;
        }
    }

    protected void closeScreen() {
        this.client.setScreen(null);
    }

    @Nullable
    public Style getTextStyleAt(double x, double y) {
        if (this.cachedPage.isEmpty()) {
            return null;
        } else {
            int i = MathHelper.floor(x - (double)((this.width - 192) / 2) - 36.0);
            int j = MathHelper.floor(y - 2.0 - 30.0);
            if (i >= 0 && j >= 0) {
                Objects.requireNonNull(this.textRenderer);
                int k = Math.min(128 / 9, this.cachedPage.size());
                if (i <= 114) {
                    Objects.requireNonNull(this.client.textRenderer);
                    if (j < 9 * k + k) {
                        Objects.requireNonNull(this.client.textRenderer);
                        int l = j / 9;
                        if (l < this.cachedPage.size()) {
                            OrderedText orderedText = this.cachedPage.get(l);
                            return this.client.textRenderer.getTextHandler().getStyleAt(orderedText, i);
                        }

                        return null;
                    }
                }

                return null;
            } else {
                return null;
            }
        }
    }

    static List<String> readPages(NbtCompound nbt) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        Objects.requireNonNull(builder);
        filterPages(nbt, builder::add);
        return builder.build();
    }

    public static void filterPages(NbtCompound nbt, Consumer<String> pageConsumer) {
        NbtList nbtList = nbt.getList("pages", 8).copy();
        IntFunction intFunction;
        if (MinecraftClient.getInstance().shouldFilterText() && nbt.contains("filtered_pages", 10)) {
            NbtCompound nbtCompound = nbt.getCompound("filtered_pages");
            intFunction = (page) -> {
                String string = String.valueOf(page);
                return nbtCompound.contains(string) ? nbtCompound.getString(string) : nbtList.getString(page);
            };
        } else {
            Objects.requireNonNull(nbtList);
            intFunction = nbtList::getString;
        }

        for(int i = 0; i < nbtList.size(); ++i) {
            pageConsumer.accept((String)intFunction.apply(i));
        }

    }

    @Environment(EnvType.CLIENT)
    public interface Contents {
        int getPageCount();

        StringVisitable getPageUnchecked(int index);

        default StringVisitable getPage(int index) {
            return index >= 0 && index < this.getPageCount() ? this.getPageUnchecked(index) : StringVisitable.EMPTY;
        }

        static Contents create(ItemStack stack) {
            if (stack.isOf(Items.WRITTEN_BOOK)) {
                return new WrittenBookContents(stack);
            } else {
                return stack.isOf(Items.WRITABLE_BOOK) ? new WritableBookContents(stack) : EMPTY_PROVIDER;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class WritableBookContents implements Contents {
        private final List<String> pages;

        public WritableBookContents(ItemStack stack) {
            this.pages = getPages(stack);
        }

        private static List<String> getPages(ItemStack stack) {
            NbtCompound nbtCompound = stack.getNbt();
            return nbtCompound != null ? readPages(nbtCompound) : ImmutableList.of();
        }

        public int getPageCount() {
            return this.pages.size();
        }

        public StringVisitable getPageUnchecked(int index) {
            return StringVisitable.plain((String)this.pages.get(index));
        }
    }

    @Environment(EnvType.CLIENT)
    public static class WrittenBookContents implements Contents {
        private final List<String> pages;

        public WrittenBookContents(ItemStack stack) {
            this.pages = getPages(stack);
        }

        private static List<String> getPages(ItemStack stack) {
            NbtCompound nbtCompound = stack.getNbt();
            return WrittenBookItem.isValid(nbtCompound) ? readPages(nbtCompound) : ImmutableList.of(Text.Serializer.toJson(Text.translatable("book.invalid.tag").formatted(Formatting.DARK_RED)));
        }

        public int getPageCount() {
            return this.pages.size();
        }

        public StringVisitable getPageUnchecked(int index) {
            String string = this.pages.get(index);

            try {
                StringVisitable stringVisitable = Text.Serializer.fromJson(string);
                if (stringVisitable != null) {
                    return stringVisitable;
                }
            } catch (Exception ignored) {
            }

            return StringVisitable.plain(string);
        }
    }
}
