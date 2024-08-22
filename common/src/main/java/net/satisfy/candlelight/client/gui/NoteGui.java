package net.satisfy.candlelight.client.gui;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.satisfy.candlelight.util.CandlelightIdentifier;
import net.satisfy.candlelight.util.CandlelightUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public abstract class NoteGui extends Screen {
    public static final ResourceLocation NOTE_TEXTURE = CandlelightIdentifier.of("textures/gui/note_paper_gui.png");
    private static final Component EDIT_TITLE_TEXT = Component.literal("Enter Note Title");
    private static final Component FINALIZE_WARNING_TEXT = Component.translatable("book.finalizeWarning");
    private static final FormattedCharSequence BLACK_CURSOR_TEXT;
    private static final FormattedCharSequence GRAY_CURSOR_TEXT;

    static {
        BLACK_CURSOR_TEXT = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.BLACK));
        GRAY_CURSOR_TEXT = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.GRAY));
    }

    protected final Player player;
    protected final ItemStack itemStack;
    private final List<String> text = Lists.newArrayList();
    private final Component signedByText;
    protected boolean dirty;
    private boolean signing;
    private int frameTick;
    private String title = "";
    private final TextFieldHelper noteTitleSelectionManager = new TextFieldHelper(() -> this.title, (title) -> this.title = title, this::getClipboard, this::setClipboard, (string) -> string.length() < 16);
    private long lastClickTime;
    private int lastClickIndex = -1;
    private Button doneButton;
    private Button signButton;
    private Button finalizeButton;
    private Button cancelButton;
    @Nullable
    private NoteGui.DisplayCache pageContent;
    private final TextFieldHelper currentPageSelectionManager = new TextFieldHelper(this::getCurrentDisplayCache, this::setDisplayCache, this::getClipboard, this::setClipboard, (string) -> string.length() < 1024 && this.font.wordWrapHeight(string, 114) <= 128);

    public NoteGui(Player player, ItemStack itemStack) {
        super(GameNarrator.NO_TITLE);
        this.pageContent = NoteGui.DisplayCache.EMPTY;
        this.player = player;
        this.itemStack = itemStack;
        CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag nbtCompound = customData.copyTag();

        List<String> string = this.text;
        Objects.requireNonNull(string);
        this.loadPages(nbtCompound, string::add);

        if (this.text.isEmpty()) {
            this.text.add("");
        }

        this.signedByText = Component.translatable("book.byAuthor", player.getName()).withStyle(ChatFormatting.DARK_GRAY);
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbtCompound));

    }

    static int getLineFromOffset(int[] lineStarts, int position) {
        int i = Arrays.binarySearch(lineStarts, position);
        return i < 0 ? -(i + 2) : i;
    }

    static int findLineFromPos(int[] is, int i) {
        int j = Arrays.binarySearch(is, i);
        return j < 0 ? -(j + 2) : j;
    }

    @Override
    protected void init() {
        this.invalidateDisplayCache();
        this.signButton = this.addRenderableWidget(Button.builder(Component.translatable("book.signButton"), (button) -> {
            this.signing = true;
            this.updateButtons();
        }).bounds(this.width / 2 - 100, 196, 98, 20).build());
        this.doneButton = this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(null);
            this.finalizeNote(false);
        }).bounds(this.width / 2 + 2, 196, 98, 20).build());
        this.finalizeButton = this.addRenderableWidget(Button.builder(Component.translatable("book.finalizeButton"), (button) -> {
            if (this.signing) {
                this.finalizeNote(true);
                assert this.minecraft != null;
                this.minecraft.setScreen(null);
            }

        }).bounds(this.width / 2 - 100, 196, 98, 20).build());
        this.cancelButton = this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, (button) -> {
            if (this.signing) {
                this.signing = false;
            }

            this.updateButtons();
        }).bounds(this.width / 2 + 2, 196, 98, 20).build());
        this.updateButtons();
    }

    public void loadPages(CompoundTag compoundTag, Consumer<String> consumer) {
        IntFunction<String> intFunction;
        ListTag listTag = compoundTag.getList("text", 8).copy();
        if (Minecraft.getInstance().isTextFilteringEnabled() && compoundTag.contains("filtered_pages", 10)) {
            CompoundTag compoundTag2 = compoundTag.getCompound("filtered_pages");
            intFunction = i -> {
                String string = String.valueOf(i);
                return compoundTag2.contains(string) ? compoundTag2.getString(string) : listTag.getString(i);
            };
        } else {
            intFunction = listTag::getString;
        }
        for (int i2 = 0; i2 < listTag.size(); ++i2) {
            consumer.accept(intFunction.apply(i2));
        }//CURSOR?
    }

    protected void removeEmptyPages() {
        ListIterator<String> listIterator = this.text.listIterator(this.text.size());

        while (listIterator.hasPrevious() && listIterator.previous().isEmpty()) {
            listIterator.remove();
        }
    }

    abstract protected void finalizeNote(boolean signNote);

    protected void writeNbtData(boolean signNote) {
        ListTag nbtList = new ListTag();
        Stream<StringTag> nbts = this.text.stream().map(StringTag::valueOf);
        Objects.requireNonNull(nbtList);
        nbts.forEach(nbtList::add);

        CustomData customData = this.itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag nbtCompound = customData.copyTag();


        if (!this.text.isEmpty()) {
            nbtCompound.put("text", nbtList);
        }

        if (signNote) {
            nbtCompound.put("author", StringTag.valueOf(this.player.getGameProfile().getName()));
            nbtCompound.put("title", StringTag.valueOf(this.title.trim()));
        }
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbtCompound));
    }

    private String getClipboard() {
        return this.minecraft != null ? TextFieldHelper.getClipboardContents(this.minecraft) : "";
    }

    private void setClipboard(String clipboard) {
        if (this.minecraft != null) {
            TextFieldHelper.setClipboardContents(this.minecraft, clipboard);
        }
    }

    public void tick() {
        super.tick();
        ++this.frameTick;
    }

    private void updateButtons() {
        this.doneButton.visible = !this.signing;
        this.signButton.visible = !this.signing;
        this.cancelButton.visible = this.signing;
        this.finalizeButton.visible = this.signing;
        this.finalizeButton.active = !this.title.trim().isEmpty();
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else if (this.signing) {
            return this.keyPressedSignMode(keyCode);
        } else {
            boolean bl = this.keyPressedEditMode(keyCode);
            if (bl) {
                this.invalidateDisplayCache();
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
            boolean bl = this.noteTitleSelectionManager.charTyped(chr);
            if (bl) {
                this.updateButtons();
                this.dirty = true;
                return true;
            } else {
                return false;
            }
        } else if (CandlelightUtil.isAllowedChatCharacter(chr)) {
            this.currentPageSelectionManager.insertText(Character.toString(chr));
            this.invalidateDisplayCache();
            return true;
        } else {
            return false;
        }
    }

    private boolean keyPressedEditMode(int keyCode) {
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
            TextFieldHelper.CursorStep selectionType = Screen.hasControlDown() ? TextFieldHelper.CursorStep.WORD : TextFieldHelper.CursorStep.CHARACTER;
            switch (keyCode) {
                case 257, 335 -> {
                    this.currentPageSelectionManager.insertText("\n");
                    return true;
                }
                case 259 -> {
                    this.currentPageSelectionManager.removeFromCursor(-1, selectionType);
                    return true;
                }
                case 261 -> {
                    this.currentPageSelectionManager.removeFromCursor(1, selectionType);
                    return true;
                }
                case 262 -> {
                    this.currentPageSelectionManager.moveBy(1, Screen.hasShiftDown(), selectionType);
                    return true;
                }
                case 263 -> {
                    this.currentPageSelectionManager.moveBy(-1, Screen.hasShiftDown(), selectionType);
                    return true;
                }
                case 264 -> {
                    this.keyDown();
                    return true;
                }
                case 265 -> {
                    this.keyUp();
                    return true;
                }
                case 268 -> {
                    this.moveToLineStart();
                    return true;
                }
                case 269 -> {
                    this.moveToLineEnd();
                    return true;
                }
                default -> {
                    return false;
                }
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
        int i = this.currentPageSelectionManager.getCursorPos();
        int j = this.getDisplayCache().changeLine(i, lines);
        this.currentPageSelectionManager.setCursorPos(j, Screen.hasShiftDown());
    }

    private void keyUp() {
        this.changeLine(-1);
    }

    private void keyDown() {
        this.changeLine(1);
    }

    private void changeLine(int i) {
        int j = this.currentPageSelectionManager.getCursorPos();
        int k = this.getDisplayCache().changeLine(j, i);
        this.currentPageSelectionManager.setCursorPos(k, Screen.hasShiftDown());
    }

    private void moveToLineStart() {
        if (Screen.hasControlDown()) {
            this.currentPageSelectionManager.setCursorToStart(Screen.hasShiftDown());
        } else {
            int i = this.currentPageSelectionManager.getCursorPos();
            int j = this.getDisplayCache().findLineStart(i);
            this.currentPageSelectionManager.setCursorPos(j, Screen.hasShiftDown());
        }

    }

    private void moveToLineEnd() {
        if (Screen.hasControlDown()) {
            this.currentPageSelectionManager.setCursorToEnd(Screen.hasShiftDown());
        } else {
            NoteGui.DisplayCache pageContent = this.getDisplayCache();
            int i = this.currentPageSelectionManager.getCursorPos();
            int j = pageContent.findLineEnd(i);
            this.currentPageSelectionManager.setCursorPos(j, Screen.hasShiftDown());
        }

    }

    private boolean keyPressedSignMode(int keyCode) {
        switch (keyCode) {
            case 257, 335 -> {
                if (!this.title.isEmpty()) {
                    this.finalizeNote(true);
                    if (this.minecraft != null) this.minecraft.setScreen(null);
                }
                return true;
            }
            case 259 -> {
                this.noteTitleSelectionManager.removeCharsFromCursor(-1);
                this.updateButtons();
                this.dirty = true;
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private String getCurrentDisplayCache() {
        return !this.text.isEmpty() ? this.text.get(0) : "";
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        this.setFocused(null);
        int x = (this.width - 192) / 2;
        guiGraphics.blit(NOTE_TEXTURE, x, 2, 0, 0, 192, 192);
        int l;
        int m;
        if (this.signing) {
            boolean bl = this.frameTick / 6 % 2 == 0;
            FormattedCharSequence orderedText = FormattedCharSequence.composite(FormattedCharSequence.forward(this.title, Style.EMPTY), bl ? BLACK_CURSOR_TEXT : GRAY_CURSOR_TEXT);
            int k = this.font.width(EDIT_TITLE_TEXT);
            guiGraphics.drawString(this.font, EDIT_TITLE_TEXT, x + 36 + (114 - k) / 2, 34, 0, false);
            l = this.font.width(orderedText);
            guiGraphics.drawString(this.font, orderedText, x + 36 + (114 - l) / 2, 50, 0);
            m = this.font.width(this.signedByText);
            guiGraphics.drawString(this.font, this.signedByText, x + 36 + (114 - m) / 2, 60, 0);
            guiGraphics.drawWordWrap(this.font, FINALIZE_WARNING_TEXT, x + 36, 82, 114, 0);
        } else {
            DisplayCache displayCache = this.getDisplayCache();
            LineInfo[] var15 = displayCache.lines;
            l = var15.length;

            for (m = 0; m < l; ++m) {
                NoteGui.LineInfo line = var15[m];
                guiGraphics.drawString(this.font, line.asComponent, line.x, line.y, -16777216, false);
            }

            this.renderHighlight(guiGraphics, displayCache.selection);
            this.renderCursor(guiGraphics, displayCache.cursor, displayCache.cursorAtEnd);
        }

        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    private void renderCursor(GuiGraphics guiGraphics, Pos2i pos2i, boolean bl) {
        if (this.frameTick / 6 % 2 == 0) {
            pos2i = this.convertLocalToScreen(pos2i);
            if (!bl) {
                int var10001 = pos2i.x;
                int var10002 = pos2i.y - 1;
                int var10003 = pos2i.x + 1;
                int var10004 = pos2i.y;
                Objects.requireNonNull(this.font);
                guiGraphics.fill(var10001, var10002, var10003, var10004 + 9, -16777216);
            } else {
                guiGraphics.drawString(this.font, "_", pos2i.x, pos2i.y, 0, false);
            }
        }
    }

    private void renderHighlight(GuiGraphics guiGraphics, Rect2i[] rect2is) {
        int var4 = rect2is.length;

        for (Rect2i rect2i : rect2is) {
            int i = rect2i.getX();
            int j = rect2i.getY();
            int k = i + rect2i.getWidth();
            int l = j + rect2i.getHeight();
            guiGraphics.fill(RenderType.guiTextHighlight(), i, j, k, l, -16776961);
        }

    }

    private Pos2i convertScreenToLocal(Pos2i pos2i) {
        return new Pos2i(pos2i.x - (this.width - 192) / 2 - 36, pos2i.y - 32);
    }

    private NoteGui.Position absolutePositionToScreenPosition(NoteGui.Position position) {
        return new NoteGui.Position(position.x + (this.width - 192) / 2 + 36, position.y + 32);
    }

    private Pos2i convertLocalToScreen(Pos2i pos2i) {
        return new Pos2i(pos2i.x + (this.width - 192) / 2 + 36, pos2i.y + 32);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!super.mouseClicked(mouseX, mouseY, button)) {
            if (button == 0) {
                long l = Util.getMillis();
                DisplayCache pageContent = this.getDisplayCache();
                int i = pageContent.getIndexAtPosition(this.font, this.convertScreenToLocal(new Pos2i((int) mouseX, (int) mouseY)));
                if (i >= 0) {
                    if (i == this.lastClickIndex && l - this.lastClickTime < 250L) {
                        if (!this.currentPageSelectionManager.isSelecting()) {
                            this.selectCurrentWord(i);
                        } else {
                            this.currentPageSelectionManager.selectAll();
                        }
                    } else {
                        this.currentPageSelectionManager.setCursorPos(i, Screen.hasShiftDown());
                    }

                    this.invalidateDisplayCache();
                }

                this.lastClickIndex = i;
                this.lastClickTime = l;
            }

        }
        return true;
    }

    private void selectCurrentWord(int cursor) {
        String string = this.getCurrentDisplayCache();
        this.currentPageSelectionManager.setSelectionRange(StringSplitter.getWordPosition(string, -1, cursor, false), StringSplitter.getWordPosition(string, 1, cursor, false));
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (!super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
            if (button == 0) {
                DisplayCache pageContent = this.getDisplayCache();
                int i = pageContent.getIndexAtPosition(this.font, this.convertScreenToLocal(new Pos2i((int) mouseX, (int) mouseY)));
                this.currentPageSelectionManager.setCursorPos(i, true);
                this.invalidateDisplayCache();
            }

        }
        return true;
    }

    private NoteGui.DisplayCache getDisplayCache() {
        if (this.pageContent == null) {
            this.pageContent = this.createDisplayCache();
        }

        return this.pageContent;
    }

    private void setDisplayCache(String newContent) {
        if (!this.text.isEmpty()) {
            this.text.set(0, newContent);
            this.dirty = true;
            this.invalidateDisplayCache();
        }

    }

    private void invalidateDisplayCache() {
        this.pageContent = null;
    }

    private NoteGui.DisplayCache createDisplayCache() {
        String string = this.getCurrentDisplayCache();
        if (string.isEmpty()) {
            return NoteGui.DisplayCache.EMPTY;
        } else {
            int i = this.currentPageSelectionManager.getCursorPos();
            int j = this.currentPageSelectionManager.getSelectionPos();
            List<Integer> intList = new ArrayList<>();
            List<LineInfo> list = new ArrayList<>();
            MutableInt mutableInt = new MutableInt();
            MutableBoolean mutableBoolean = new MutableBoolean();
            StringSplitter textHandler = this.font.getSplitter();
            textHandler.splitLines(string, 114, Style.EMPTY, true, (style, start, end) -> {
                int h = mutableInt.getAndIncrement();
                String substring = string.substring(start, end);
                mutableBoolean.setValue(substring.endsWith("\n"));
                String string2 = StringUtils.stripEnd(substring, " \n");
                Objects.requireNonNull(this.font);
                int b = h * 9;
                NoteGui.Position position = this.absolutePositionToScreenPosition(new NoteGui.Position(0, b));
                intList.add(start);
                list.add(new NoteGui.LineInfo(style, string2, position.x, position.y));
            });
            int[] is = intList.stream().mapToInt(Integer::intValue).toArray();
            boolean bl = i == string.length();
            Pos2i position;
            int l;
            if (bl && mutableBoolean.isTrue()) {
                int var10003 = list.size();
                Objects.requireNonNull(this.font);
                position = new Pos2i(0, var10003 * 9);
            } else {
                int k = getLineFromOffset(is, i);
                l = this.font.width(string.substring(is[k], i));
                Objects.requireNonNull(this.font);
                position = new Pos2i(l, k * 9);
            }

            List<Rect2i> list2 = new ArrayList<>();
            if (i != j) {
                l = Math.min(i, j);
                int m = Math.max(i, j);
                int n = getLineFromOffset(is, l);
                int o = getLineFromOffset(is, m);
                int p;
                int q;
                if (n == o) {
                    Objects.requireNonNull(this.font);
                    p = n * 9;
                    q = is[n];
                    list2.add(this.getLineSelectionRectangle(string, textHandler, l, m, p, q));
                } else {
                    p = n + 1 > is.length ? string.length() : is[n + 1];
                    Objects.requireNonNull(this.font);
                    list2.add(this.getLineSelectionRectangle(string, textHandler, l, p, n * 9, is[n]));

                    for (q = n + 1; q < o; ++q) {
                        Objects.requireNonNull(this.font);
                        int r = q * 9;
                        String string2 = string.substring(is[q], is[q + 1]);
                        int s = (int) textHandler.stringWidth(string2);
                        NoteGui.Position var10002 = new NoteGui.Position(0, r);
                        Objects.requireNonNull(this.font);
                        list2.add(this.getRectFromCorners(var10002, new NoteGui.Position(s, r + 9)));
                    }

                    int var10004 = is[o];
                    Objects.requireNonNull(this.font);
                    list2.add(this.getLineSelectionRectangle(string, textHandler, var10004, m, o * 9, is[o]));
                }
            }

            return new NoteGui.DisplayCache(string, position, bl, is, list.toArray(new NoteGui.LineInfo[0]), list2.toArray(new Rect2i[0]));
        }
    }

    private Rect2i getLineSelectionRectangle(String string, StringSplitter handler, int selectionStart, int selectionEnd, int lineY, int lineStart) {
        String string2 = string.substring(lineStart, selectionStart);
        String string3 = string.substring(lineStart, selectionEnd);
        NoteGui.Position position = new NoteGui.Position((int) handler.stringWidth(string2), lineY);
        int var10002 = (int) handler.stringWidth(string3);
        Objects.requireNonNull(this.font);
        NoteGui.Position position2 = new NoteGui.Position(var10002, lineY + 9);
        return this.getRectFromCorners(position, position2);
    }

    private Rect2i getRectFromCorners(NoteGui.Position start, NoteGui.Position end) {
        NoteGui.Position position = this.absolutePositionToScreenPosition(start);
        NoteGui.Position position2 = this.absolutePositionToScreenPosition(end);
        int i = Math.min(position.x, position2.x);
        int j = Math.max(position.x, position2.x);
        int k = Math.min(position.y, position2.y);
        int l = Math.max(position.y, position2.y);
        return new Rect2i(i, k, j - i, l - k);
    }

    @Environment(EnvType.CLIENT)
    @SuppressWarnings("all")
    static class DisplayCache {
        static final DisplayCache EMPTY;

        static {
            EMPTY = new DisplayCache("", new Pos2i(0, 0), true, new int[]{0}, new LineInfo[]{new LineInfo(Style.EMPTY, "", 0, 0)}, new Rect2i[0]);
        }

        final Pos2i cursor;
        final boolean cursorAtEnd;
        final LineInfo[] lines;
        final Rect2i[] selection;
        private final String fullText;
        private final int[] lineStarts;

        public DisplayCache(String string, Pos2i pos2i, boolean bl, int[] is, LineInfo[] lineInfos, Rect2i[] rect2is) {
            this.fullText = string;
            this.cursor = pos2i;
            this.cursorAtEnd = bl;
            this.lineStarts = is;
            this.lines = lineInfos;
            this.selection = rect2is;
        }

        public int getIndexAtPosition(Font font, Pos2i pos2i) {
            int var10000 = pos2i.y;
            Objects.requireNonNull(font);
            int i = var10000 / 9;
            if (i < 0) {
                return 0;
            } else if (i >= this.lines.length) {
                return this.fullText.length();
            } else {
                LineInfo lineInfo = this.lines[i];
                return this.lineStarts[i] + font.getSplitter().plainIndexAtWidth(lineInfo.contents, pos2i.x, lineInfo.style);
            }
        }

        public int changeLine(int i, int j) {
            int k = findLineFromPos(this.lineStarts, i);
            int l = k + j;
            int o;
            if (l >= 0 && l < this.lineStarts.length) {
                int m = i - this.lineStarts[k];
                int n = this.lines[l].contents.length();
                o = this.lineStarts[l] + Math.min(m, n);
            } else {
                o = i;
            }

            return o;
        }

        public int findLineStart(int i) {
            int j = findLineFromPos(this.lineStarts, i);
            return this.lineStarts[j];
        }

        public int findLineEnd(int i) {
            int j = findLineFromPos(this.lineStarts, i);
            return this.lineStarts[j] + this.lines[j].contents.length();
        }
    }

    @Environment(EnvType.CLIENT)
    private static class LineInfo {
        final Style style;
        final String contents;
        final Component asComponent;
        final int x;
        final int y;

        public LineInfo(Style style, String string, int i, int j) {
            this.style = style;
            this.contents = string;
            this.x = i;
            this.y = j;
            this.asComponent = Component.literal(string).setStyle(style);
        }
    }

    @Environment(EnvType.CLIENT)
    private record Position(int x, int y) {
    }

    @Environment(EnvType.CLIENT)
    static class Pos2i {
        public final int x;
        public final int y;

        Pos2i(int i, int j) {
            this.x = i;
            this.y = j;
        }
    }
}
