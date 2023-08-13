package satisfyu.candlelight.client.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public abstract class NoteGui extends Screen {
    public static final ResourceLocation NOTE_TEXTURE = new CandlelightIdentifier("textures/gui/note_paper_gui.png");
    private static final Component EDIT_TITLE_TEXT = Component.literal("Enter Note Title");
    private static final Component FINALIZE_WARNING_TEXT = Component.translatable("book.finalizeWarning");
    private static final FormattedCharSequence BLACK_CURSOR_TEXT;
    private static final FormattedCharSequence GRAY_CURSOR_TEXT;
    protected final Player player;
    protected final ItemStack itemStack;
    protected boolean dirty;
    private boolean signing;
    private int tickCounter;
    private final List<String> text = Lists.newArrayList();
    private String title = "";
    private final TextFieldHelper currentPageSelectionManager = new TextFieldHelper(this::getCurrentPageContent, this::setPageContent, this::getClipboard, this::setClipboard, (string) -> string.length() < 1024 && this.font.wordWrapHeight(string, 114) <= 128);
    private final TextFieldHelper noteTitleSelectionManager = new TextFieldHelper(() -> this.title, (title) -> this.title = title, this::getClipboard, this::setClipboard, (string) -> string.length() < 16);
    private long lastClickTime;
    private int lastClickIndex = -1;
    private Button doneButton;
    private Button signButton;
    private Button finalizeButton;
    private Button cancelButton;
    @Nullable
    private NoteGui.PageContent pageContent;
    private final Component signedByText;

    public NoteGui(Player player, ItemStack itemStack) {
        super(GameNarrator.NO_TITLE);
        this.pageContent = NoteGui.PageContent.EMPTY;
        this.player = player;
        this.itemStack = itemStack;
        CompoundTag nbtCompound = itemStack.getTag();
        if (nbtCompound != null) {
            List<String> string = this.text;
            Objects.requireNonNull(string);
            loadPages(nbtCompound, string::add);
        }

        if (this.text.isEmpty()) {
            this.text.add("");
        }

        this.signedByText = Component.translatable("book.byAuthor", player.getName()).withStyle(ChatFormatting.DARK_GRAY);

    }

    @Override
    protected void init() {
        this.invalidatePageContent();
        if (this.minecraft != null) this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.signButton = this.addRenderableWidget(new Button(this.width / 2 - 100, 196, 98, 20, Component.translatable("book.signButton"), (button) -> {
            this.signing = true;
            this.updateButtons();
        }));
        this.doneButton = this.addRenderableWidget(new Button(this.width / 2 + 2, 196, 98, 20, CommonComponents.GUI_DONE, (button) -> {
            this.minecraft.setScreen(null);
            this.finalizeNote(false);
        }));
        this.finalizeButton = this.addRenderableWidget(new Button(this.width / 2 - 100, 196, 98, 20, Component.translatable("book.finalizeButton"), (button) -> {
            if (this.signing) {
                this.finalizeNote(true);
                this.minecraft.setScreen(null);
            }

        }));
        this.cancelButton = this.addRenderableWidget(new Button(this.width / 2 + 2, 196, 98, 20, CommonComponents.GUI_CANCEL, (button) -> {
            if (this.signing) {
                this.signing = false;
            }

            this.updateButtons();
        }));
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

        while(listIterator.hasPrevious() && listIterator.previous().isEmpty()) {
            listIterator.remove();
        }
    }

    abstract protected void finalizeNote(boolean signNote);

    protected void writeNbtData(boolean signNote) {
        ListTag nbtList = new ListTag();
        Stream<StringTag> nbts = this.text.stream().map(StringTag::valueOf);
        Objects.requireNonNull(nbtList);
        nbts.forEach(nbtList::add);
        if (!this.text.isEmpty()) {
            this.itemStack.addTagElement("text", nbtList);
        }

        if (signNote) {
            this.itemStack.addTagElement("author", StringTag.valueOf(this.player.getGameProfile().getName()));
            this.itemStack.addTagElement("title", StringTag.valueOf(this.title.trim()));
        }
    }

    private void setClipboard(String clipboard) {
        if (this.minecraft != null) {
            TextFieldHelper.setClipboardContents(this.minecraft, clipboard);
        }
    }

    private String getClipboard() {
        return this.minecraft != null ? TextFieldHelper.getClipboardContents(this.minecraft) : "";
    }

    public void tick() {
        super.tick();
        ++this.tickCounter;
    }

    public void removed() {
        if (this.minecraft != null) this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
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
            boolean bl = this.noteTitleSelectionManager.charTyped(chr);
            if (bl) {
                this.updateButtons();
                this.dirty = true;
                return true;
            } else {
                return false;
            }
        } else if (SharedConstants.isAllowedChatCharacter(chr)) {
            this.currentPageSelectionManager.insertText(Character.toString(chr));
            this.invalidatePageContent();
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
                    this.moveDownLine();
                    return true;
                }
                case 265 -> {
                    this.moveUpLine();
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
        int j = this.getPageContent().getVerticalOffset(i, lines);
        this.currentPageSelectionManager.setCursorPos(j, Screen.hasShiftDown());
    }

    private void moveToLineStart() {
        if (Screen.hasControlDown()) {
            this.currentPageSelectionManager.setCursorToStart(Screen.hasShiftDown());
        } else {
            int i = this.currentPageSelectionManager.getCursorPos();
            int j = this.getPageContent().getLineStart(i);
            this.currentPageSelectionManager.setCursorPos(j, Screen.hasShiftDown());
        }

    }

    private void moveToLineEnd() {
        if (Screen.hasControlDown()) {
            this.currentPageSelectionManager.setCursorToEnd(Screen.hasShiftDown());
        } else {
            NoteGui.PageContent pageContent = this.getPageContent();
            int i = this.currentPageSelectionManager.getCursorPos();
            int j = pageContent.getLineEnd(i);
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

    private String getCurrentPageContent() {
        return 0 < this.text.size() ? this.text.get(0) : "";
    }

    private void setPageContent(String newContent) {
        if (0 < this.text.size()) {
            this.text.set(0, newContent);
            this.dirty = true;
            this.invalidatePageContent();
        }

    }

    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.setFocused(null);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, NOTE_TEXTURE);
        int x = (this.width - 192) / 2;
        this.blit(matrices, x, 2, 0, 0, 192, 192);
        int l;
        int m;
        if (this.signing) {
            boolean bl = this.tickCounter / 6 % 2 == 0;
            FormattedCharSequence orderedText = FormattedCharSequence.composite(FormattedCharSequence.forward(this.title, Style.EMPTY), bl ? BLACK_CURSOR_TEXT : GRAY_CURSOR_TEXT);
            int k = this.font.width(EDIT_TITLE_TEXT);
            this.font.draw(matrices, EDIT_TITLE_TEXT, (float)(x + 36 + (114 - k) / 2), 34.0F, 0);
            l = this.font.width(orderedText);
            this.font.draw(matrices, orderedText, (float)(x + 36 + (114 - l) / 2), 50.0F, 0);
            m = this.font.width(this.signedByText);
            this.font.draw(matrices, this.signedByText, (float)(x + 36 + (114 - m) / 2), 60.0F, 0);
            this.font.drawWordWrap(FINALIZE_WARNING_TEXT, x + 36, 82, 114, 0);
        } else {
            NoteGui.PageContent pageContent = this.getPageContent();
            NoteGui.Line[] var15 = pageContent.lines;
            l = var15.length;

            for(m = 0; m < l; ++m) {
                NoteGui.Line line = var15[m];
                this.font.draw(matrices, line.text, (float)line.x, (float)line.y, -16777216);
            }

            this.drawSelection(pageContent.selectionRectangles);
            this.drawCursor(matrices, pageContent.position, pageContent.atEnd);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    private void drawCursor(PoseStack matrices, NoteGui.Position position, boolean atEnd) {
        if (this.tickCounter / 6 % 2 == 0) {
            position = this.absolutePositionToScreenPosition(position);
            if (!atEnd) {
                int var10001 = position.x;
                int var10002 = position.y - 1;
                int var10003 = position.x + 1;
                int var10004 = position.y;
                Objects.requireNonNull(this.font);
                GuiComponent.fill(matrices, var10001, var10002, var10003, var10004 + 9, -16777216);
            } else {
                this.font.draw(matrices, "_", (float)position.x, (float)position.y, 0);
            }
        }

    }

    private void drawSelection(Rect2i[] selectionRectangles) {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(0.0F, 0.0F, 255.0F, 255.0F);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for (Rect2i rect2i : selectionRectangles) {
            int i = rect2i.getX();
            int j = rect2i.getY();
            int k = i + rect2i.getWidth();
            int l = j + rect2i.getHeight();
            bufferBuilder.vertex(i, l, 0.0).endVertex();
            bufferBuilder.vertex(k, l, 0.0).endVertex();
            bufferBuilder.vertex(k, j, 0.0).endVertex();
            bufferBuilder.vertex(i, j, 0.0).endVertex();
        }

        tessellator.end();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

    private NoteGui.Position screenPositionToAbsolutePosition(NoteGui.Position position) {
        return new NoteGui.Position(position.x - (this.width - 192) / 2 - 36, position.y - 32);
    }

    private NoteGui.Position absolutePositionToScreenPosition(NoteGui.Position position) {
        return new NoteGui.Position(position.x + (this.width - 192) / 2 + 36, position.y + 32);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!super.mouseClicked(mouseX, mouseY, button)) {
            if (button == 0) {
                long l = Util.getMillis();
                PageContent pageContent = this.getPageContent();
                int i = pageContent.getCursorPosition(this.font, this.screenPositionToAbsolutePosition(new Position((int) mouseX, (int) mouseY)));
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

                    this.invalidatePageContent();
                }

                this.lastClickIndex = i;
                this.lastClickTime = l;
            }

        }
        return true;
    }

    private void selectCurrentWord(int cursor) {
        String string = this.getCurrentPageContent();
        this.currentPageSelectionManager.setSelectionRange(StringSplitter.getWordPosition(string, -1, cursor, false), StringSplitter.getWordPosition(string, 1, cursor, false));
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (!super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
            if (button == 0) {
                PageContent pageContent = this.getPageContent();
                int i = pageContent.getCursorPosition(this.font, this.screenPositionToAbsolutePosition(new Position((int) mouseX, (int) mouseY)));
                this.currentPageSelectionManager.setCursorPos(i, true);
                this.invalidatePageContent();
            }

        }
        return true;
    }

    private NoteGui.PageContent getPageContent() {
        if (this.pageContent == null) {
            this.pageContent = this.createPageContent();
        }

        return this.pageContent;
    }

    private void invalidatePageContent() {
        this.pageContent = null;
    }

    private NoteGui.PageContent createPageContent() {
        String string = this.getCurrentPageContent();
        if (string.isEmpty()) {
            return NoteGui.PageContent.EMPTY;
        } else {
            int i = this.currentPageSelectionManager.getCursorPos();
            int j = this.currentPageSelectionManager.getSelectionPos();
            List<Integer> intList = new ArrayList<>();
            List<NoteGui.Line> list = new ArrayList<>();
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
                list.add(new NoteGui.Line(style, string2, position.x, position.y));
            });
            int[] is = intList.stream().mapToInt(Integer::intValue).toArray();
            boolean bl = i == string.length();
            NoteGui.Position position;
            int l;
            if (bl && mutableBoolean.isTrue()) {
                int var10003 = list.size();
                Objects.requireNonNull(this.font);
                position = new NoteGui.Position(0, var10003 * 9);
            } else {
                int k = getLineFromOffset(is, i);
                l = this.font.width(string.substring(is[k], i));
                Objects.requireNonNull(this.font);
                position = new NoteGui.Position(l, k * 9);
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

                    for(q = n + 1; q < o; ++q) {
                        Objects.requireNonNull(this.font);
                        int r = q * 9;
                        String string2 = string.substring(is[q], is[q + 1]);
                        int s = (int)textHandler.stringWidth(string2);
                        NoteGui.Position var10002 = new NoteGui.Position(0, r);
                        Objects.requireNonNull(this.font);
                        list2.add(this.getRectFromCorners(var10002, new NoteGui.Position(s, r + 9)));
                    }

                    int var10004 = is[o];
                    Objects.requireNonNull(this.font);
                    list2.add(this.getLineSelectionRectangle(string, textHandler, var10004, m, o * 9, is[o]));
                }
            }

            return new NoteGui.PageContent(string, position, bl, is, list.toArray(new NoteGui.Line[0]), list2.toArray(new Rect2i[0]));
        }
    }

    static int getLineFromOffset(int[] lineStarts, int position) {
        int i = Arrays.binarySearch(lineStarts, position);
        return i < 0 ? -(i + 2) : i;
    }



    private Rect2i getLineSelectionRectangle(String string, StringSplitter handler, int selectionStart, int selectionEnd, int lineY, int lineStart) {
        String string2 = string.substring(lineStart, selectionStart);
        String string3 = string.substring(lineStart, selectionEnd);
        NoteGui.Position position = new NoteGui.Position((int)handler.stringWidth(string2), lineY);
        int var10002 = (int)handler.stringWidth(string3);
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

    static {
        BLACK_CURSOR_TEXT = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.BLACK));
        GRAY_CURSOR_TEXT = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.GRAY));
    }

    @Environment(EnvType.CLIENT)
    static class PageContent {
        static final NoteGui.PageContent EMPTY;
        private final String pageContent;
        final NoteGui.Position position;
        final boolean atEnd;
        private final int[] lineStarts;
        final NoteGui.Line[] lines;
        final Rect2i[] selectionRectangles;

        public PageContent(String pageContent, NoteGui.Position position, boolean atEnd, int[] lineStarts, NoteGui.Line[] lines, Rect2i[] selectionRectangles) {
            this.pageContent = pageContent;
            this.position = position;
            this.atEnd = atEnd;
            this.lineStarts = lineStarts;
            this.lines = lines;
            this.selectionRectangles = selectionRectangles;
        }

        public int getCursorPosition(Font renderer, NoteGui.Position position) {
            int var10000 = position.y;
            Objects.requireNonNull(renderer);
            int i = var10000 / 9;
            if (i < 0) {
                return 0;
            } else if (i >= this.lines.length) {
                return this.pageContent.length();
            } else {
                NoteGui.Line line = this.lines[i];
                return this.lineStarts[i] + renderer.getSplitter().plainIndexAtWidth(line.content, position.x, line.style);
            }
        }

        public int getVerticalOffset(int position, int lines) {
            int i = NotePaperGui.getLineFromOffset(this.lineStarts, position);
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
            int i = NotePaperGui.getLineFromOffset(this.lineStarts, position);
            return this.lineStarts[i];
        }

        public int getLineEnd(int position) {
            int i = NotePaperGui.getLineFromOffset(this.lineStarts, position);
            return this.lineStarts[i] + this.lines[i].content.length();
        }

        static {
            EMPTY = new NoteGui.PageContent("", new NoteGui.Position(0, 0), true, new int[]{0}, new NoteGui.Line[]{new NoteGui.Line(Style.EMPTY, "", 0, 0)}, new Rect2i[0]);
        }
    }

    @Environment(EnvType.CLIENT)
    static class Line {
        final Style style;
        final String content;
        final Component text;
        final int x;
        final int y;

        public Line(Style style, String content, int x, int y) {
            this.style = style;
            this.content = content;
            this.x = x;
            this.y = y;
            this.text = Component.literal(content).setStyle(style);
        }
    }

    @Environment(EnvType.CLIENT)
        private record Position(int x, int y) {
    }
}
