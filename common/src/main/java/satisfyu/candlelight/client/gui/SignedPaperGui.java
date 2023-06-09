package satisfyu.candlelight.client.gui;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import org.jetbrains.annotations.Nullable;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
@Environment(EnvType.CLIENT)
public class SignedPaperGui extends Screen{
    public static final Contents EMPTY_PROVIDER = new Contents() {
        public int getPageCount() {
            return 0;
        }

        public FormattedText getPageUnchecked(int index) {
            return FormattedText.EMPTY;
        }
    };
    public static final ResourceLocation BOOK_TEXTURE = new CandlelightIdentifier("textures/gui/note_paper_gui.png");
    private final Contents contents;
    private int pageIndex;
    private List<FormattedCharSequence> cachedPage;
    private int cachedPageIndex;

    public SignedPaperGui(Contents contents) {
        super(GameNarrator.NO_TITLE);
        this.cachedPage = Collections.emptyList();
        this.cachedPageIndex = -1;
        this.contents = contents;
    }

    public boolean setPage(int index) {
        int i = Mth.clamp(index, 0, this.contents.getPageCount() - 1);
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
        this.addRenderableWidget(new Button(this.width / 2 - 100, 196, 200, 20, CommonComponents.GUI_DONE, (button) -> {
            this.minecraft.setScreen(null);
        }));
    }
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);
        int i = (this.width - 192) / 2;
        this.blit(matrices, i, 2, 0, 0, 192, 192);
        if (this.cachedPageIndex != this.pageIndex) {
            FormattedText stringVisitable = this.contents.getPage(this.pageIndex);
            this.cachedPage = this.font.split(stringVisitable, 114);
        }

        this.cachedPageIndex = this.pageIndex;
        Objects.requireNonNull(this.font);
        int l = Math.min(128 / 9, this.cachedPage.size());

        for(int m = 0; m < l; ++m) {
            FormattedCharSequence orderedText = this.cachedPage.get(m);
            Font var10000 = this.font;
            float var10003 = (float)(i + 36);
            Objects.requireNonNull(this.font);
            var10000.draw(matrices, orderedText, var10003, (float)(32 + m * 9), 0);
        }

        Style style = this.getTextStyleAt(mouseX, mouseY);
        if (style != null) {
            this.renderComponentHoverEffect(matrices, style, mouseX, mouseY);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            Style style = this.getTextStyleAt(mouseX, mouseY);
            if (style != null && this.handleComponentClicked(style)) {
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean handleComponentClicked(Style style) {
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
            boolean bl = super.handleComponentClicked(style);
            if (bl && clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.closeScreen();
            }

            return bl;
        }
    }

    protected void closeScreen() {
        this.minecraft.setScreen(null);
    }

    @Nullable
    public Style getTextStyleAt(double x, double y) {
        if (this.cachedPage.isEmpty()) {
            return null;
        } else {
            int i = Mth.floor(x - (double)((this.width - 192) / 2) - 36.0);
            int j = Mth.floor(y - 2.0 - 30.0);
            if (i >= 0 && j >= 0) {
                Objects.requireNonNull(this.font);
                int k = Math.min(128 / 9, this.cachedPage.size());
                if (i <= 114) {
                    Objects.requireNonNull(this.minecraft.font);
                    if (j < 9 * k + k) {
                        Objects.requireNonNull(this.minecraft.font);
                        int l = j / 9;
                        if (l < this.cachedPage.size()) {
                            FormattedCharSequence orderedText = this.cachedPage.get(l);
                            return this.minecraft.font.getSplitter().componentStyleAtWidth(orderedText, i);
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

    static List<String> readPages(CompoundTag nbt) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        Objects.requireNonNull(builder);
        filterPages(nbt, builder::add);
        return builder.build();
    }

    public static void filterPages(CompoundTag nbt, Consumer<String> pageConsumer) {
        ListTag nbtList = nbt.getList("pages", 8).copy();
        IntFunction intFunction;
        if (Minecraft.getInstance().isTextFilteringEnabled() && nbt.contains("filtered_pages", 10)) {
            CompoundTag nbtCompound = nbt.getCompound("filtered_pages");
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

        FormattedText getPageUnchecked(int index);

        default FormattedText getPage(int index) {
            return index >= 0 && index < this.getPageCount() ? this.getPageUnchecked(index) : FormattedText.EMPTY;
        }

        static Contents create(ItemStack stack) {
            if (stack.is(Items.WRITTEN_BOOK)) {
                return new WrittenBookContents(stack);
            } else {
                return stack.is(Items.WRITABLE_BOOK) ? new WritableBookContents(stack) : EMPTY_PROVIDER;
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
            CompoundTag nbtCompound = stack.getTag();
            return nbtCompound != null ? readPages(nbtCompound) : ImmutableList.of();
        }

        public int getPageCount() {
            return this.pages.size();
        }

        public FormattedText getPageUnchecked(int index) {
            return FormattedText.of((String)this.pages.get(index));
        }
    }

    @Environment(EnvType.CLIENT)
    public static class WrittenBookContents implements Contents {
        private final List<String> pages;

        public WrittenBookContents(ItemStack stack) {
            this.pages = getPages(stack);
        }

        private static List<String> getPages(ItemStack stack) {
            CompoundTag nbtCompound = stack.getTag();
            return WrittenBookItem.makeSureTagIsValid(nbtCompound) ? readPages(nbtCompound) : ImmutableList.of(Component.Serializer.toJson(Component.translatable("book.invalid.tag").withStyle(ChatFormatting.DARK_RED)));
        }

        public int getPageCount() {
            return this.pages.size();
        }

        public FormattedText getPageUnchecked(int index) {
            String string = this.pages.get(index);

            try {
                FormattedText stringVisitable = Component.Serializer.fromJson(string);
                if (stringVisitable != null) {
                    return stringVisitable;
                }
            } catch (Exception ignored) {
            }

            return FormattedText.of(string);
        }
    }
}
