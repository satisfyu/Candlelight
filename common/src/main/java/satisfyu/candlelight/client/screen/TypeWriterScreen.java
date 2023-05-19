package satisfyu.candlelight.client.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;
import satisfyu.candlelight.networking.CandlelightMessages;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.stream.Stream;
@Environment(EnvType.CLIENT)
public class TypeWriterScreen extends Screen {
    public static final ResourceLocation BOOK_TEXTURE = new CandlelightIdentifier("textures/gui/note_paper_gui.png");
    private static final Component EDIT_TITLE_TEXT = Component.literal("Enter Note Title");
    private static final Component FINALIZE_WARNING_TEXT = Component.translatable("book.finalizeWarning");
    private static final FormattedCharSequence BLACK_CURSOR_TEXT;
    private static final FormattedCharSequence GRAY_CURSOR_TEXT;
    private final Player player;
    private final ItemStack itemStack;
    private boolean dirty;
    private boolean signing;
    private int tickCounter;
    private final List<String> pages = Lists.newArrayList();
    private String title = "";
    private final TextFieldHelper currentPageSelectionManager = new TextFieldHelper(this::getCurrentPageContent, this::setPageContent, this::getClipboard, this::setClipboard, (string) -> string.length() < 1024 && this.font.wordWrapHeight(string, 114) <= 128);
    private final TextFieldHelper bookTitleSelectionManager = new TextFieldHelper(() -> this.title, (title) -> {
        this.title = title;
    }, this::getClipboard, this::setClipboard, (string) -> string.length() < 16);
    private long lastClickTime;
    private int lastClickIndex = -1;
    private Button doneButton;
    private Button signButton;
    private Button finalizeButton;
    private Button cancelButton;
    private final InteractionHand hand;
    @Nullable
    private PageContent pageContent;
    private final Component signedByText;

    private BlockPos pos;

    public TypeWriterScreen(Player player, ItemStack itemStack, InteractionHand hand) {
        super(GameNarrator.NO_TITLE);
        this.pageContent = PageContent.EMPTY;
        this.player = player;
        this.itemStack = itemStack;
        this.hand = hand;
        CompoundTag nbtCompound = itemStack.getTag();
        if (nbtCompound != null) {
            List<String> string = this.pages;
            Objects.requireNonNull(string);
            BookViewScreen.loadPages(nbtCompound, string::add);
        }

        if (this.pages.isEmpty()) {
            this.pages.add("");
        }

        this.signedByText = Component.translatable("book.byAuthor", player.getName()).withStyle(ChatFormatting.DARK_GRAY);
        this.pos = BlockPos.ZERO;
    }

    public TypeWriterScreen(Player player, ItemStack itemStack, InteractionHand hand, BlockPos pos)
    {
        this(player, itemStack, hand);
        this.pos = pos;
    }

    private void setClipboard(String clipboard) {
        if (this.minecraft != null) {
            TextFieldHelper.setClipboardContents(this.minecraft, clipboard);
        }

    }

    private String getClipboard() {
        return this.minecraft != null ? TextFieldHelper.getClipboardContents(this.minecraft) : "";
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
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.signButton = this.addRenderableWidget(new Button(this.width / 2 - 100, 196, 98, 20, Component.translatable("book.signButton"), (button) -> {
            this.signing = true;
            this.updateButtons();
        }));
        this.doneButton = this.addRenderableWidget(new Button(this.width / 2 + 2, 196, 98, 20, CommonComponents.GUI_DONE, (button) -> {
            this.minecraft.setScreen(null);
            this.finalizeBook(false);
        }));
        this.finalizeButton = this.addRenderableWidget(new Button(this.width / 2 - 100, 196, 98, 20, Component.translatable("book.finalizeButton"), (button) -> {
            if (this.signing) {
                this.finalizeBook(true);
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

    public void removed() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    private void updateButtons() {
        this.doneButton.visible = !this.signing;
        this.signButton.visible = !this.signing;
        this.cancelButton.visible = this.signing;
        this.finalizeButton.visible = this.signing;
        this.finalizeButton.active = !this.title.trim().isEmpty();
    }

    private void removeEmptyPages() {
        ListIterator<String> listIterator = this.pages.listIterator(this.pages.size());

        while(listIterator.hasPrevious() && listIterator.previous().isEmpty()) {
            listIterator.remove();
        }

    }

    private void finalizeBook(boolean signBook) {
        if (this.dirty) {
            this.removeEmptyPages();
            this.writeNbtData(signBook);
            int i = this.hand == InteractionHand.MAIN_HAND ? this.player.getInventory().selected : 40;
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeNbt(itemStack.getTag());
            buf.writeBlockPos(pos);
            buf.writeBoolean(signBook);
            /*buf.writeCollection(this.pages, (buf2, page) -> {
                buf2.writeString(page, 8192);
            });
            buf.writeOptional(signBook ? Optional.of(this.title.trim()) : Optional.empty(), (buf2, title) -> {
                buf2.writeString(title, 128);
            });*/
            NetworkManager.sendToServer(CandlelightMessages.TYPEWRITER_SYNC, buf);
            //this.client.getNetworkHandler().sendPacket(new BookUpdateC2SPacket(i, this.pages, signBook ? Optional.of(this.title.trim()) : Optional.empty()));
        }
    }

    private void writeNbtData(boolean signBook) {
        ListTag nbtList = new ListTag();
        Stream<StringTag> nbts = this.pages.stream().map(StringTag::valueOf);
        Objects.requireNonNull(nbtList);
        nbts.forEach((nbt) -> nbtList.add(nbt));
        if (!this.pages.isEmpty()) {
            this.itemStack.addTagElement("pages", nbtList);
        }

        if (signBook) {
            this.itemStack.addTagElement("author", StringTag.valueOf(this.player.getGameProfile().getName()));
            this.itemStack.addTagElement("title", StringTag.valueOf(this.title.trim()));
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
            boolean bl = this.bookTitleSelectionManager.charTyped(chr);
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
            PageContent pageContent = this.getPageContent();
            int i = this.currentPageSelectionManager.getCursorPos();
            int j = pageContent.getLineEnd(i);
            this.currentPageSelectionManager.setCursorPos(j, Screen.hasShiftDown());
        }

    }

    private boolean keyPressedSignMode(int keyCode, int scanCode, int modifiers) {
        switch (keyCode) {
            case 257, 335 -> {
                if (!this.title.isEmpty()) {
                    this.finalizeBook(true);
                    this.minecraft.setScreen(null);
                }
                return true;
            }
            case 259 -> {
                this.bookTitleSelectionManager.removeCharsFromCursor(-1);
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
        return 0 < this.pages.size() ? this.pages.get(0) : "";
    }

    private void setPageContent(String newContent) {
        if (0 < this.pages.size()) {
            this.pages.set(0, newContent);
            this.dirty = true;
            this.invalidatePageContent();
        }

    }

    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.setFocused(null);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);
        int i = (this.width - 192) / 2;
        boolean j = true;
        this.blit(matrices, i, 2, 0, 0, 192, 192);
        int l;
        int m;
        if (this.signing) {
            boolean bl = this.tickCounter / 6 % 2 == 0;
            FormattedCharSequence orderedText = FormattedCharSequence.composite(FormattedCharSequence.forward(this.title, Style.EMPTY), bl ? BLACK_CURSOR_TEXT : GRAY_CURSOR_TEXT);
            int k = this.font.width(EDIT_TITLE_TEXT);
            this.font.draw(matrices, EDIT_TITLE_TEXT, (float)(i + 36 + (114 - k) / 2), 34.0F, 0);
            l = this.font.width(orderedText);
            this.font.draw(matrices, orderedText, (float)(i + 36 + (114 - l) / 2), 50.0F, 0);
            m = this.font.width(this.signedByText);
            this.font.draw(matrices, this.signedByText, (float)(i + 36 + (114 - m) / 2), 60.0F, 0);
            this.font.drawWordWrap(FINALIZE_WARNING_TEXT, i + 36, 82, 114, 0);
        } else {
            PageContent pageContent = this.getPageContent();
            Line[] var15 = pageContent.lines;
            l = var15.length;

            for(m = 0; m < l; ++m) {
                Line line = var15[m];
                this.font.draw(matrices, line.text, (float)line.x, (float)line.y, -16777216);
            }

            this.drawSelection(pageContent.selectionRectangles);
            this.drawCursor(matrices, pageContent.position, pageContent.atEnd);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    private void drawCursor(PoseStack matrices, Position position, boolean atEnd) {
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
        int var5 = selectionRectangles.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Rect2i rect2i = selectionRectangles[var6];
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
                long l = Util.getMillis();
                PageContent pageContent = this.getPageContent();
                int i = pageContent.getCursorPosition(this.font, this.screenPositionToAbsolutePosition(new Position((int)mouseX, (int)mouseY)));
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

            return true;
        }
    }

    private void selectCurrentWord(int cursor) {
        String string = this.getCurrentPageContent();
        this.currentPageSelectionManager.setSelectionRange(StringSplitter.getWordPosition(string, -1, cursor, false), StringSplitter.getWordPosition(string, 1, cursor, false));
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
            return true;
        } else {
            if (button == 0) {
                PageContent pageContent = this.getPageContent();
                int i = pageContent.getCursorPosition(this.font, this.screenPositionToAbsolutePosition(new Position((int)mouseX, (int)mouseY)));
                this.currentPageSelectionManager.setCursorPos(i, true);
                this.invalidatePageContent();
            }

            return true;
        }
    }

    private PageContent getPageContent() {
        if (this.pageContent == null) {
            this.pageContent = this.createPageContent();
        }

        return this.pageContent;
    }

    private void invalidatePageContent() {
        this.pageContent = null;
    }

    private void changePage() {
        this.currentPageSelectionManager.setCursorToEnd();
        this.invalidatePageContent();
    }

    private PageContent createPageContent() {
        String string = this.getCurrentPageContent();
        if (string.isEmpty()) {
            return PageContent.EMPTY;
        } else {
            int i = this.currentPageSelectionManager.getCursorPos();
            int j = this.currentPageSelectionManager.getSelectionPos();
            IntList intList = new IntArrayList();
            List<Line> list = Lists.newArrayList();
            MutableInt mutableInt = new MutableInt();
            MutableBoolean mutableBoolean = new MutableBoolean();
            StringSplitter textHandler = this.font.getSplitter();
            textHandler.splitLines(string, 114, Style.EMPTY, true, (style, start, end) -> {
                int h = mutableInt.getAndIncrement();
                String stringx = string.substring(start, end);
                mutableBoolean.setValue(stringx.endsWith("\n"));
                String string2 = StringUtils.stripEnd(stringx, " \n");
                Objects.requireNonNull(this.font);
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
                Objects.requireNonNull(this.font);
                position = new Position(0, var10003 * 9);
            } else {
                int k = getLineFromOffset(is, i);
                l = this.font.width(string.substring(is[k], i));
                Objects.requireNonNull(this.font);
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
                        Position var10002 = new Position(0, r);
                        Objects.requireNonNull(this.font);
                        list2.add(this.getRectFromCorners(var10002, new Position(s, r + 9)));
                    }

                    int var10004 = is[o];
                    Objects.requireNonNull(this.font);
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



    private Rect2i getLineSelectionRectangle(String string, StringSplitter handler, int selectionStart, int selectionEnd, int lineY, int lineStart) {
        String string2 = string.substring(lineStart, selectionStart);
        String string3 = string.substring(lineStart, selectionEnd);
        Position position = new Position((int)handler.stringWidth(string2), lineY);
        int var10002 = (int)handler.stringWidth(string3);
        Objects.requireNonNull(this.font);
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
        BLACK_CURSOR_TEXT = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.BLACK));
        GRAY_CURSOR_TEXT = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.GRAY));
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

        public int getCursorPosition(Font renderer, Position position) {
            int var10000 = position.y;
            Objects.requireNonNull(renderer);
            int i = var10000 / 9;
            if (i < 0) {
                return 0;
            } else if (i >= this.lines.length) {
                return this.pageContent.length();
            } else {
                Line line = this.lines[i];
                return this.lineStarts[i] + renderer.getSplitter().plainIndexAtWidth(line.content, position.x, line.style);
            }
        }

        public int getVerticalOffset(int position, int lines) {
            int i = NotePaperScreen.getLineFromOffset(this.lineStarts, position);
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
            int i = NotePaperScreen.getLineFromOffset(this.lineStarts, position);
            return this.lineStarts[i];
        }

        public int getLineEnd(int position) {
            int i = NotePaperScreen.getLineFromOffset(this.lineStarts, position);
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
    private static class Position {
        public final int x;
        public final int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
