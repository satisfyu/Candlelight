//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.satisfy.candlelight.client.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager.LogicOp;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.SelectionManager;
import net.minecraft.client.util.SelectionManager.SelectionType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class NoteEditScreen extends Screen {
    private static final int MAX_TEXT_WIDTH = 114;
    private static final int MAX_TEXT_HEIGHT = 128;
    private static final int WIDTH = 192;
    private static final int HEIGHT = 192;
    private static final Text EDIT_TITLE_TEXT = Text.literal("Enter Note Title");
    private static final Text FINALIZE_WARNING_TEXT = Text.translatable("book.finalizeWarning");
    private static final OrderedText BLACK_CURSOR_TEXT;
    private static final OrderedText GRAY_CURSOR_TEXT;
    private final PlayerEntity player;
    private final ItemStack itemStack;
    private boolean dirty;
    private boolean signing;
    private int tickCounter;
    private int currentPage;
    private final List<String> pages = Lists.newArrayList();
    private String title = "";
    private final SelectionManager currentPageSelectionManager = new SelectionManager(this::getCurrentPageContent, this::setPageContent, this::getClipboard, this::setClipboard, (string) -> {
        return string.length() < 1024 && this.textRenderer.getWrappedLinesHeight(string, 114) <= 128;
    });
    private final SelectionManager bookTitleSelectionManager = new SelectionManager(() -> {
        return this.title;
    }, (title) -> {
        this.title = title;
    }, this::getClipboard, this::setClipboard, (string) -> {
        return string.length() < 16;
    });
    private long lastClickTime;
    private int lastClickIndex = -1;
    private PageTurnWidget nextPageButton;
    private PageTurnWidget previousPageButton;
    private ButtonWidget doneButton;
    private ButtonWidget signButton;
    private ButtonWidget finalizeButton;
    private ButtonWidget cancelButton;
    private final Hand hand;
    @Nullable
    private PageContent pageContent;
    private Text pageIndicatorText;
    private final Text signedByText;

    public NoteEditScreen(PlayerEntity player, ItemStack itemStack, Hand hand) {
        super(NarratorManager.EMPTY);
        this.pageContent = NoteEditScreen.PageContent.EMPTY;
        this.pageIndicatorText = ScreenTexts.EMPTY;
        this.player = player;
        this.itemStack = itemStack;
        this.hand = hand;
        NbtCompound nbtCompound = itemStack.getNbt();
        if (nbtCompound != null) {
            List var10001 = this.pages;
            Objects.requireNonNull(var10001);
            BookScreen.filterPages(nbtCompound, var10001::add);
        }

        if (this.pages.isEmpty()) {
            this.pages.add("");
        }

        this.signedByText = Text.translatable("book.byAuthor", new Object[]{player.getName()}).formatted(Formatting.DARK_GRAY);
    }

    private void setClipboard(String clipboard) {
        if (this.client != null) {
            SelectionManager.setClipboard(this.client, clipboard);
        }

    }

    private String getClipboard() {
        return this.client != null ? SelectionManager.getClipboard(this.client) : "";
    }

    private int countPages() {
        return this.pages.size();
    }

    public void tick() {
        super.tick();
        ++this.tickCounter;
    }

    protected void init() {
        this.invalidatePageContent();
        this.client.keyboard.setRepeatEvents(true);
        this.signButton = (ButtonWidget)this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, 196, 98, 20, Text.translatable("book.signButton"), (button) -> {
            this.signing = true;
            this.updateButtons();
        }));
        this.doneButton = (ButtonWidget)this.addDrawableChild(new ButtonWidget(this.width / 2 + 2, 196, 98, 20, ScreenTexts.DONE, (button) -> {
            this.client.setScreen((Screen)null);
            this.finalizeBook(false);
        }));
        this.finalizeButton = (ButtonWidget)this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, 196, 98, 20, Text.translatable("book.finalizeButton"), (button) -> {
            if (this.signing) {
                this.finalizeBook(true);
                this.client.setScreen((Screen)null);
            }

        }));
        this.cancelButton = (ButtonWidget)this.addDrawableChild(new ButtonWidget(this.width / 2 + 2, 196, 98, 20, ScreenTexts.CANCEL, (button) -> {
            if (this.signing) {
                this.signing = false;
            }

            this.updateButtons();
        }));
        int i = (this.width - 192) / 2;
        boolean j = true;
        this.nextPageButton = (PageTurnWidget)this.addDrawableChild(new PageTurnWidget(i + 116, 159, true, (button) -> {
            this.openNextPage();
        }, true));
        this.previousPageButton = (PageTurnWidget)this.addDrawableChild(new PageTurnWidget(i + 43, 159, false, (button) -> {
            this.openPreviousPage();
        }, true));
        this.updateButtons();
    }

    private void openPreviousPage() {
        if (this.currentPage > 0) {
            --this.currentPage;
        }

        this.updateButtons();
        this.changePage();
    }

    private void openNextPage() {
        if (this.currentPage < this.countPages() - 1) {
            ++this.currentPage;
        } else {
            this.appendNewPage();
            if (this.currentPage < this.countPages() - 1) {
                ++this.currentPage;
            }
        }

        this.updateButtons();
        this.changePage();
    }

    public void removed() {
        this.client.keyboard.setRepeatEvents(false);
    }

    private void updateButtons() {
        this.previousPageButton.visible = !this.signing && this.currentPage > 0;
        this.nextPageButton.visible = !this.signing;
        this.doneButton.visible = !this.signing;
        this.signButton.visible = !this.signing;
        this.cancelButton.visible = this.signing;
        this.finalizeButton.visible = this.signing;
        this.finalizeButton.active = !this.title.trim().isEmpty();
    }

    private void removeEmptyPages() {
        ListIterator<String> listIterator = this.pages.listIterator(this.pages.size());

        while(listIterator.hasPrevious() && ((String)listIterator.previous()).isEmpty()) {
            listIterator.remove();
        }

    }

    private void finalizeBook(boolean signBook) {
        if (this.dirty) {
            this.removeEmptyPages();
            this.writeNbtData(signBook);
            int i = this.hand == Hand.MAIN_HAND ? this.player.getInventory().selectedSlot : 40;
            this.client.getNetworkHandler().sendPacket(new BookUpdateC2SPacket(i, this.pages, signBook ? Optional.of(this.title.trim()) : Optional.empty()));
        }
    }

    private void writeNbtData(boolean signBook) {
        NbtList nbtList = new NbtList();
        Stream var10000 = this.pages.stream().map(NbtString::of);
        Objects.requireNonNull(nbtList);
        var10000.forEach((a) -> nbtList.add((NbtElement) a));
        if (!this.pages.isEmpty()) {
            this.itemStack.setSubNbt("pages", nbtList);
        }

        if (signBook) {
            this.itemStack.setSubNbt("author", NbtString.of(this.player.getGameProfile().getName()));
            this.itemStack.setSubNbt("title", NbtString.of(this.title.trim()));
        }

    }

    private void appendNewPage() {
        if (this.countPages() < 100) {
            this.pages.add("");
            this.dirty = true;
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else if (this.signing) {
            return this.keyPressedSignMode(keyCode, scanCode, modifiers);
        } else {
            boolean bl = this.keyPressedEditMode(keyCode, scanCode, modifiers);
            if (bl) {
                this.invalidatePageContent();
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean charTyped(char chr, int modifiers) {
        if (super.charTyped(chr, modifiers)) {
            return true;
        } else if (this.signing) {
            boolean bl = this.bookTitleSelectionManager.insert(chr);
            if (bl) {
                this.updateButtons();
                this.dirty = true;
                return true;
            } else {
                return false;
            }
        } else if (SharedConstants.isValidChar(chr)) {
            this.currentPageSelectionManager.insert(Character.toString(chr));
            this.invalidatePageContent();
            return true;
        } else {
            return false;
        }
    }

    private boolean keyPressedEditMode(int keyCode, int scanCode, int modifiers) {
        if (Screen.isSelectAll(keyCode)) {
            this.currentPageSelectionManager.selectAll();
            return true;
        } else if (Screen.isCopy(keyCode)) {
            this.currentPageSelectionManager.copy();
            return true;
        } else if (Screen.isPaste(keyCode)) {
            this.currentPageSelectionManager.paste();
            return true;
        } else if (Screen.isCut(keyCode)) {
            this.currentPageSelectionManager.cut();
            return true;
        } else {
            SelectionManager.SelectionType selectionType = Screen.hasControlDown() ? SelectionType.WORD : SelectionType.CHARACTER;
            switch (keyCode) {
                case 257:
                case 335:
                    this.currentPageSelectionManager.insert("\n");
                    return true;
                case 259:
                    this.currentPageSelectionManager.delete(-1, selectionType);
                    return true;
                case 261:
                    this.currentPageSelectionManager.delete(1, selectionType);
                    return true;
                case 262:
                    this.currentPageSelectionManager.moveCursor(1, Screen.hasShiftDown(), selectionType);
                    return true;
                case 263:
                    this.currentPageSelectionManager.moveCursor(-1, Screen.hasShiftDown(), selectionType);
                    return true;
                case 264:
                    this.moveDownLine();
                    return true;
                case 265:
                    this.moveUpLine();
                    return true;
                case 266:
                    this.previousPageButton.onPress();
                    return true;
                case 267:
                    this.nextPageButton.onPress();
                    return true;
                case 268:
                    this.moveToLineStart();
                    return true;
                case 269:
                    this.moveToLineEnd();
                    return true;
                default:
                    return false;
            }
        }
    }

    private void moveUpLine() {
        this.moveVertically(-1);
    }

    private void moveDownLine() {
        this.moveVertically(1);
    }

    private void moveVertically(int lines) {
        int i = this.currentPageSelectionManager.getSelectionStart();
        int j = this.getPageContent().getVerticalOffset(i, lines);
        this.currentPageSelectionManager.moveCursorTo(j, Screen.hasShiftDown());
    }

    private void moveToLineStart() {
        if (Screen.hasControlDown()) {
            this.currentPageSelectionManager.moveCursorToStart(Screen.hasShiftDown());
        } else {
            int i = this.currentPageSelectionManager.getSelectionStart();
            int j = this.getPageContent().getLineStart(i);
            this.currentPageSelectionManager.moveCursorTo(j, Screen.hasShiftDown());
        }

    }

    private void moveToLineEnd() {
        if (Screen.hasControlDown()) {
            this.currentPageSelectionManager.moveCursorToEnd(Screen.hasShiftDown());
        } else {
            PageContent pageContent = this.getPageContent();
            int i = this.currentPageSelectionManager.getSelectionStart();
            int j = pageContent.getLineEnd(i);
            this.currentPageSelectionManager.moveCursorTo(j, Screen.hasShiftDown());
        }

    }

    private boolean keyPressedSignMode(int keyCode, int scanCode, int modifiers) {
        switch (keyCode) {
            case 257:
            case 335:
                if (!this.title.isEmpty()) {
                    this.finalizeBook(true);
                    this.client.setScreen((Screen)null);
                }

                return true;
            case 259:
                this.bookTitleSelectionManager.delete(-1);
                this.updateButtons();
                this.dirty = true;
                return true;
            default:
                return false;
        }
    }

    private String getCurrentPageContent() {
        return this.currentPage >= 0 && this.currentPage < this.pages.size() ? (String)this.pages.get(this.currentPage) : "";
    }

    private void setPageContent(String newContent) {
        if (this.currentPage >= 0 && this.currentPage < this.pages.size()) {
            this.pages.set(this.currentPage, newContent);
            this.dirty = true;
            this.invalidatePageContent();
        }

    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.setFocused((Element)null);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BookScreen.BOOK_TEXTURE);
        int i = (this.width - 192) / 2;
        boolean j = true;
        this.drawTexture(matrices, i, 2, 0, 0, 192, 192);
        int l;
        int m;
        if (this.signing) {
            boolean bl = this.tickCounter / 6 % 2 == 0;
            OrderedText orderedText = OrderedText.concat(OrderedText.styledForwardsVisitedString(this.title, Style.EMPTY), bl ? BLACK_CURSOR_TEXT : GRAY_CURSOR_TEXT);
            int k = this.textRenderer.getWidth(EDIT_TITLE_TEXT);
            this.textRenderer.draw(matrices, EDIT_TITLE_TEXT, (float)(i + 36 + (114 - k) / 2), 34.0F, 0);
            l = this.textRenderer.getWidth(orderedText);
            this.textRenderer.draw(matrices, orderedText, (float)(i + 36 + (114 - l) / 2), 50.0F, 0);
            m = this.textRenderer.getWidth(this.signedByText);
            this.textRenderer.draw(matrices, this.signedByText, (float)(i + 36 + (114 - m) / 2), 60.0F, 0);
            this.textRenderer.drawTrimmed(FINALIZE_WARNING_TEXT, i + 36, 82, 114, 0);
        } else {
            int n = this.textRenderer.getWidth(this.pageIndicatorText);
            this.textRenderer.draw(matrices, this.pageIndicatorText, (float)(i - n + 192 - 44), 18.0F, 0);
            PageContent pageContent = this.getPageContent();
            Line[] var15 = pageContent.lines;
            l = var15.length;

            for(m = 0; m < l; ++m) {
                Line line = var15[m];
                this.textRenderer.draw(matrices, line.text, (float)line.x, (float)line.y, -16777216);
            }

            this.drawSelection(pageContent.selectionRectangles);
            this.drawCursor(matrices, pageContent.position, pageContent.atEnd);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    private void drawCursor(MatrixStack matrices, Position position, boolean atEnd) {
        if (this.tickCounter / 6 % 2 == 0) {
            position = this.absolutePositionToScreenPosition(position);
            if (!atEnd) {
                int var10001 = position.x;
                int var10002 = position.y - 1;
                int var10003 = position.x + 1;
                int var10004 = position.y;
                Objects.requireNonNull(this.textRenderer);
                DrawableHelper.fill(matrices, var10001, var10002, var10003, var10004 + 9, -16777216);
            } else {
                this.textRenderer.draw(matrices, "_", (float)position.x, (float)position.y, 0);
            }
        }

    }

    private void drawSelection(Rect2i[] selectionRectangles) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(0.0F, 0.0F, 255.0F, 255.0F);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(LogicOp.OR_REVERSE);
        bufferBuilder.begin(DrawMode.QUADS, VertexFormats.POSITION);
        Rect2i[] var4 = selectionRectangles;
        int var5 = selectionRectangles.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Rect2i rect2i = var4[var6];
            int i = rect2i.getX();
            int j = rect2i.getY();
            int k = i + rect2i.getWidth();
            int l = j + rect2i.getHeight();
            bufferBuilder.vertex((double)i, (double)l, 0.0).next();
            bufferBuilder.vertex((double)k, (double)l, 0.0).next();
            bufferBuilder.vertex((double)k, (double)j, 0.0).next();
            bufferBuilder.vertex((double)i, (double)j, 0.0).next();
        }

        tessellator.draw();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

    private Position screenPositionToAbsolutePosition(Position position) {
        return new Position(position.x - (this.width - 192) / 2 - 36, position.y - 32);
    }

    private Position absolutePositionToScreenPosition(Position position) {
        return new Position(position.x + (this.width - 192) / 2 + 36, position.y + 32);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            if (button == 0) {
                long l = Util.getMeasuringTimeMs();
                PageContent pageContent = this.getPageContent();
                int i = pageContent.getCursorPosition(this.textRenderer, this.screenPositionToAbsolutePosition(new Position((int)mouseX, (int)mouseY)));
                if (i >= 0) {
                    if (i == this.lastClickIndex && l - this.lastClickTime < 250L) {
                        if (!this.currentPageSelectionManager.isSelecting()) {
                            this.selectCurrentWord(i);
                        } else {
                            this.currentPageSelectionManager.selectAll();
                        }
                    } else {
                        this.currentPageSelectionManager.moveCursorTo(i, Screen.hasShiftDown());
                    }

                    this.invalidatePageContent();
                }

                this.lastClickIndex = i;
                this.lastClickTime = l;
            }

            return true;
        }
    }

    private void selectCurrentWord(int cursor) {
        String string = this.getCurrentPageContent();
        this.currentPageSelectionManager.setSelection(TextHandler.moveCursorByWords(string, -1, cursor, false), TextHandler.moveCursorByWords(string, 1, cursor, false));
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
            return true;
        } else {
            if (button == 0) {
                PageContent pageContent = this.getPageContent();
                int i = pageContent.getCursorPosition(this.textRenderer, this.screenPositionToAbsolutePosition(new Position((int)mouseX, (int)mouseY)));
                this.currentPageSelectionManager.moveCursorTo(i, true);
                this.invalidatePageContent();
            }

            return true;
        }
    }

    private PageContent getPageContent() {
        if (this.pageContent == null) {
            this.pageContent = this.createPageContent();
            this.pageIndicatorText = Text.translatable("book.pageIndicator", new Object[]{this.currentPage + 1, this.countPages()});
        }

        return this.pageContent;
    }

    private void invalidatePageContent() {
        this.pageContent = null;
    }

    private void changePage() {
        this.currentPageSelectionManager.putCursorAtEnd();
        this.invalidatePageContent();
    }

    private PageContent createPageContent() {
        String string = this.getCurrentPageContent();
        if (string.isEmpty()) {
            return NoteEditScreen.PageContent.EMPTY;
        } else {
            int i = this.currentPageSelectionManager.getSelectionStart();
            int j = this.currentPageSelectionManager.getSelectionEnd();
            IntList intList = new IntArrayList();
            List<Line> list = Lists.newArrayList();
            MutableInt mutableInt = new MutableInt();
            MutableBoolean mutableBoolean = new MutableBoolean();
            TextHandler textHandler = this.textRenderer.getTextHandler();
            textHandler.wrapLines(string, 114, Style.EMPTY, true, (style, start, end) -> {
                int h = mutableInt.getAndIncrement();
                String stringx = string.substring(start, end);
                mutableBoolean.setValue(stringx.endsWith("\n"));
                String string2 = StringUtils.stripEnd(stringx, " \n");
                Objects.requireNonNull(this.textRenderer);
                int b = h * 9;
                Position position = this.absolutePositionToScreenPosition(new Position(0, b));
                intList.add(start);
                list.add(new Line(style, string2, position.x, position.y));
            });
            int[] is = intList.toIntArray();
            boolean bl = i == string.length();
            Position position;
            int l;
            if (bl && mutableBoolean.isTrue()) {
                int var10003 = list.size();
                Objects.requireNonNull(this.textRenderer);
                position = new Position(0, var10003 * 9);
            } else {
                int k = getLineFromOffset(is, i);
                l = this.textRenderer.getWidth(string.substring(is[k], i));
                Objects.requireNonNull(this.textRenderer);
                position = new Position(l, k * 9);
            }

            List<Rect2i> list2 = Lists.newArrayList();
            if (i != j) {
                l = Math.min(i, j);
                int m = Math.max(i, j);
                int n = getLineFromOffset(is, l);
                int o = getLineFromOffset(is, m);
                int p;
                int q;
                if (n == o) {
                    Objects.requireNonNull(this.textRenderer);
                    p = n * 9;
                    q = is[n];
                    list2.add(this.getLineSelectionRectangle(string, textHandler, l, m, p, q));
                } else {
                    p = n + 1 > is.length ? string.length() : is[n + 1];
                    Objects.requireNonNull(this.textRenderer);
                    list2.add(this.getLineSelectionRectangle(string, textHandler, l, p, n * 9, is[n]));

                    for(q = n + 1; q < o; ++q) {
                        Objects.requireNonNull(this.textRenderer);
                        int r = q * 9;
                        String string2 = string.substring(is[q], is[q + 1]);
                        int s = (int)textHandler.getWidth(string2);
                        Position var10002 = new Position(0, r);
                        Objects.requireNonNull(this.textRenderer);
                        list2.add(this.getRectFromCorners(var10002, new Position(s, r + 9)));
                    }

                    int var10004 = is[o];
                    Objects.requireNonNull(this.textRenderer);
                    list2.add(this.getLineSelectionRectangle(string, textHandler, var10004, m, o * 9, is[o]));
                }
            }

            return new PageContent(string, position, bl, is, (Line[])list.toArray(new Line[0]), (Rect2i[])list2.toArray(new Rect2i[0]));
        }
    }

    static int getLineFromOffset(int[] lineStarts, int position) {
        int i = Arrays.binarySearch(lineStarts, position);
        return i < 0 ? -(i + 2) : i;
    }



    private Rect2i getLineSelectionRectangle(String string, TextHandler handler, int selectionStart, int selectionEnd, int lineY, int lineStart) {
        String string2 = string.substring(lineStart, selectionStart);
        String string3 = string.substring(lineStart, selectionEnd);
        Position position = new Position((int)handler.getWidth(string2), lineY);
        int var10002 = (int)handler.getWidth(string3);
        Objects.requireNonNull(this.textRenderer);
        Position position2 = new Position(var10002, lineY + 9);
        return this.getRectFromCorners(position, position2);
    }

    private Rect2i getRectFromCorners(Position start, Position end) {
        Position position = this.absolutePositionToScreenPosition(start);
        Position position2 = this.absolutePositionToScreenPosition(end);
        int i = Math.min(position.x, position2.x);
        int j = Math.max(position.x, position2.x);
        int k = Math.min(position.y, position2.y);
        int l = Math.max(position.y, position2.y);
        return new Rect2i(i, k, j - i, l - k);
    }

    static {
        BLACK_CURSOR_TEXT = OrderedText.styledForwardsVisitedString("_", Style.EMPTY.withColor(Formatting.BLACK));
        GRAY_CURSOR_TEXT = OrderedText.styledForwardsVisitedString("_", Style.EMPTY.withColor(Formatting.GRAY));
    }

    @Environment(EnvType.CLIENT)
    static class PageContent {
        static final PageContent EMPTY;
        private final String pageContent;
        final Position position;
        final boolean atEnd;
        private final int[] lineStarts;
        final Line[] lines;
        final Rect2i[] selectionRectangles;

        public PageContent(String pageContent, Position position, boolean atEnd, int[] lineStarts, Line[] lines, Rect2i[] selectionRectangles) {
            this.pageContent = pageContent;
            this.position = position;
            this.atEnd = atEnd;
            this.lineStarts = lineStarts;
            this.lines = lines;
            this.selectionRectangles = selectionRectangles;
        }

        public int getCursorPosition(TextRenderer renderer, Position position) {
            int var10000 = position.y;
            Objects.requireNonNull(renderer);
            int i = var10000 / 9;
            if (i < 0) {
                return 0;
            } else if (i >= this.lines.length) {
                return this.pageContent.length();
            } else {
                Line line = this.lines[i];
                return this.lineStarts[i] + renderer.getTextHandler().getTrimmedLength(line.content, position.x, line.style);
            }
        }

        public int getVerticalOffset(int position, int lines) {
            int i = NoteEditScreen.getLineFromOffset(this.lineStarts, position);
            int j = i + lines;
            int m;
            if (0 <= j && j < this.lineStarts.length) {
                int k = position - this.lineStarts[i];
                int l = this.lines[j].content.length();
                m = this.lineStarts[j] + Math.min(k, l);
            } else {
                m = position;
            }

            return m;
        }

        public int getLineStart(int position) {
            int i = NoteEditScreen.getLineFromOffset(this.lineStarts, position);
            return this.lineStarts[i];
        }

        public int getLineEnd(int position) {
            int i = NoteEditScreen.getLineFromOffset(this.lineStarts, position);
            return this.lineStarts[i] + this.lines[i].content.length();
        }

        static {
            EMPTY = new PageContent("", new Position(0, 0), true, new int[]{0}, new Line[]{new Line(Style.EMPTY, "", 0, 0)}, new Rect2i[0]);
        }
    }

    @Environment(EnvType.CLIENT)
    static class Line {
        final Style style;
        final String content;
        final Text text;
        final int x;
        final int y;

        public Line(Style style, String content, int x, int y) {
            this.style = style;
            this.content = content;
            this.x = x;
            this.y = y;
            this.text = Text.literal(content).setStyle(style);
        }
    }

    @Environment(EnvType.CLIENT)
    private static class Position {
        public final int x;
        public final int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
