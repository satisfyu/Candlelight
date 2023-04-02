package net.satisfy.candlelight.client.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.recipebook.*;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.gui.handler.CookingPanScreenHandler;
import net.satisfy.candlelight.client.screen.VineryRecipeBookResults;
import net.satisfy.candlelight.client.screen.VineryRecipeResultCollection;
import net.satisfy.candlelight.client.search.VinerySearchManager;
import net.satisfy.candlelight.registry.RecipeTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class VineryRecipeBookWidget extends RecipeBookWidget {
    public static final Identifier TEXTURE = new Identifier("textures/gui/recipe_book.png");
    private static final Text SEARCH_HINT_TEXT;
    private static final Text TOGGLE_CRAFTABLE_RECIPES_TEXT;
    private static final Text TOGGLE_ALL_RECIPES_TEXT;
    private int leftOffset;
    private int parentWidth;
    private int parentHeight;
    protected final RecipeBookGhostSlots ghostSlots = new RecipeBookGhostSlots();
    private final List<VineryRecipeGroupButtonWidget> tabButtons = Lists.newArrayList();
    @Nullable
    private VineryRecipeGroupButtonWidget currentTab;
    protected ToggleButtonWidget toggleCraftableButton;
    protected CookingPanScreenHandler craftingScreenHandler;
    protected MinecraftClient client;
    @Nullable
    private TextFieldWidget searchField;
    private String searchText = "";
    private VineryClientRecipeBook recipeBook;
    private final VineryRecipeBookResults recipesArea = new VineryRecipeBookResults();
    private final RecipeMatcher recipeFinder = new RecipeMatcher();
    private int cachedInvChangeCount;
    private boolean searching;
    private boolean open;
    private boolean narrow;

    public VineryRecipeBookWidget() {

    }

    public void initialize(int parentWidth, int parentHeight, MinecraftClient client, boolean narrow, CookingPanScreenHandler craftingScreenHandler) {
        this.client = client;
        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;
        this.craftingScreenHandler = craftingScreenHandler;
        this.narrow = narrow;
        client.player.currentScreenHandler = craftingScreenHandler;
        this.recipeBook = new VineryClientRecipeBook();
        this.cachedInvChangeCount = client.player.getInventory().getChangeCount();
        this.open = this.isGuiOpen();
        if (this.open) {
            this.reset();
        }

        client.keyboard.setRepeatEvents(true);
    }

    public void reset() {
        this.leftOffset = this.narrow ? 0 : 86;
        int i = (this.parentWidth - 147) / 2 - this.leftOffset;
        int j = (this.parentHeight - 166) / 2;
        this.recipeFinder.clear();
        this.client.player.getInventory().populateRecipeFinder(this.recipeFinder);
        this.craftingScreenHandler.populateRecipeFinder(this.recipeFinder);
        String string = this.searchField != null ? this.searchField.getText() : "";
        TextRenderer var10003 = this.client.textRenderer;
        int var10004 = i + 25;
        int var10005 = j + 14;
        Objects.requireNonNull(this.client.textRenderer);
        this.searchField = new TextFieldWidget(var10003, var10004, var10005, 80, 9 + 5, Text.translatable("itemGroup.search"));
        this.searchField.setMaxLength(50);
        this.searchField.setDrawsBackground(false);
        this.searchField.setVisible(true);
        this.searchField.setEditableColor(16777215);
        this.searchField.setText(string);
        this.recipesArea.initialize(this.client, i, j);
        this.recipesArea.setGui(this);
        this.toggleCraftableButton = new ToggleButtonWidget(i + 110, j + 12, 26, 16, Candlelight.rememberedCraftableToggle);
        this.setBookButtonTexture();
        this.tabButtons.clear();

        for (VineryRecipeBookGroup recipeBookGroup : VineryRecipeBookGroup.getGroups(this.craftingScreenHandler.getVineryCategory())) {
            this.tabButtons.add(new VineryRecipeGroupButtonWidget(recipeBookGroup));
        }

        if (this.currentTab != null) {
            this.currentTab = this.tabButtons.stream().filter((button) -> button.getGroup().equals(this.currentTab.getGroup())
            ).findFirst().orElse(null);
        }

        if (this.currentTab == null) {
            this.currentTab = this.tabButtons.get(0);
        }

        this.currentTab.setToggled(true);
        this.refreshResults(false);
        this.refreshTabButtons();
    }

    public boolean changeFocus(boolean lookForwards) {
        return false;
    }

    protected void setBookButtonTexture() {
        this.toggleCraftableButton.setTextureUV(152, 41, 28, 18, TEXTURE);
    }

    public void close() {
        this.client.keyboard.setRepeatEvents(false);
    }

    public int findLeftEdge(int width, int backgroundWidth) {
        int i;
        if (this.isOpen() && !this.narrow) {
            i = 177 + (width - backgroundWidth - 200) / 2;
        } else {
            i = (width - backgroundWidth) / 2;
        }

        return i;
    }

    public void toggleOpen() {
        this.setOpen(!this.isOpen());
    }

    public boolean isOpen() {
        return this.open;
    }

    private boolean isGuiOpen() {
        return Candlelight.rememberedRecipeBookOpen;
    }

    protected void setOpen(boolean opened) {
        if (opened) {
            this.reset();
        }

        this.open = opened;
        Candlelight.rememberedRecipeBookOpen = opened;
        if (!opened) {
            this.recipesArea.hideAlternates();
        }

        this.sendBookDataPacket();
    }

    public void slotClicked(@Nullable Slot slot) {
        if (slot != null && slot.id < this.craftingScreenHandler.getCraftingSlotCount()) {
            this.ghostSlots.reset();
            if (this.isOpen()) {
                this.refreshInputs();
            }
        }

    }

    private void refreshResults(boolean resetCurrentPage) {
        if (this.currentTab == null) return;
        if (this.searchField == null) return;

        List<VineryRecipeResultCollection> list = this.recipeBook.getResultsForGroup(currentTab.getGroup(), client.world.getRecipeManager().listAllOfType(RecipeTypes.COOKING_PAN_RECIPE_TYPE));

        String string = this.searchField.getText();

        if (!string.isEmpty()) {
            ObjectSet<VineryRecipeResultCollection> objectSet = new ObjectLinkedOpenHashSet<>(this.client.getSearchProvider(VinerySearchManager.VINERY_RECIPE_OUTPUT).findAll(string.toLowerCase(Locale.ROOT))); //TODO GAME CRASH
            list.removeIf((recipeResultCollection) -> !objectSet.contains(recipeResultCollection));
        }

        if (Candlelight.rememberedCraftableToggle) {
            list.removeIf((resultCollection) -> !resultCollection.hasCraftableRecipes());
        }

        this.recipesArea.setResults(list, resetCurrentPage, currentTab.getGroup());
    }

    private void refreshTabButtons() {
        int i = (this.parentWidth - 147) / 2 - this.leftOffset - 30;
        int j = (this.parentHeight - 166) / 2 + 3;
        int l = 0;

        for (VineryRecipeGroupButtonWidget recipeGroupButtonWidget : this.tabButtons) {
            VineryRecipeBookGroup recipeBookGroup = recipeGroupButtonWidget.getGroup();
            if (recipeBookGroup != VineryRecipeBookGroup.SEARCH) {
                recipeGroupButtonWidget.visible = true;
                recipeGroupButtonWidget.setPos(i, j + 27 * l++);
                /*
                if (recipeGroupButtonWidget.hasKnownRecipes(this.recipeBook)) { //TODO recipes
                    recipeGroupButtonWidget.checkForNewRecipes(this.client);
                }
                 */
            } else {
                recipeGroupButtonWidget.visible = true;
                recipeGroupButtonWidget.setPos(i, j + 27 * l++);
            }
        }
    }

    public void update() {
        boolean bl = this.isGuiOpen();
        if (this.isOpen() != bl) {
            this.setOpen(bl);
        }

        if (this.isOpen()) {
            if (this.cachedInvChangeCount != this.client.player.getInventory().getChangeCount()) {
                this.refreshInputs();
                this.cachedInvChangeCount = this.client.player.getInventory().getChangeCount();
            }

            this.searchField.tick();
        }
    }

    private void refreshInputs() {
        this.recipeFinder.clear();
        this.client.player.getInventory().populateRecipeFinder(this.recipeFinder);
        this.craftingScreenHandler.populateRecipeFinder(this.recipeFinder);
        this.refreshResults(false);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.isOpen()) {
            matrices.push();
            matrices.translate(0.0, 0.0, 100.0);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TEXTURE);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int i = (this.parentWidth - 147) / 2 - this.leftOffset;
            int j = (this.parentHeight - 166) / 2;
            this.drawTexture(matrices, i, j, 1, 1, 147, 166);
            if (!this.searchField.isFocused() && this.searchField.getText().isEmpty()) {
                drawTextWithShadow(matrices, this.client.textRenderer, SEARCH_HINT_TEXT, i + 25, j + 14, -1);
            } else {
                this.searchField.render(matrices, mouseX, mouseY, delta);
            }

            for (VineryRecipeGroupButtonWidget recipeGroupButtonWidget : this.tabButtons) {
                recipeGroupButtonWidget.render(matrices, mouseX, mouseY, delta);
            }

            this.toggleCraftableButton.render(matrices, mouseX, mouseY, delta);
            this.recipesArea.draw(matrices, i, j, mouseX, mouseY, delta);
            matrices.pop();
        }
    }

    public void drawTooltip(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        if (this.isOpen()) {
            this.recipesArea.drawTooltip(matrices, mouseX, mouseY);
            if (this.toggleCraftableButton.isHovered()) {
                Text text = this.getCraftableButtonText();
                if (this.client.currentScreen != null) {
                    this.client.currentScreen.renderTooltip(matrices, text, mouseX, mouseY);
                }
            }

            this.drawGhostSlotTooltip(matrices, x, y, mouseX, mouseY);
        }
    }

    private Text getCraftableButtonText() {
        return this.toggleCraftableButton.isToggled() ? this.getToggleCraftableButtonText() : TOGGLE_ALL_RECIPES_TEXT;
    }

    protected Text getToggleCraftableButtonText() {
        return TOGGLE_CRAFTABLE_RECIPES_TEXT;
    }

    private void drawGhostSlotTooltip(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        ItemStack itemStack = null;

        for(int i = 0; i < this.ghostSlots.getSlotCount(); ++i) {
            RecipeBookGhostSlots.GhostInputSlot ghostInputSlot = this.ghostSlots.getSlot(i);
            int j = ghostInputSlot.getX() + x;
            int k = ghostInputSlot.getY() + y;
            if (mouseX >= j && mouseY >= k && mouseX < j + 16 && mouseY < k + 16) {
                itemStack = ghostInputSlot.getCurrentItemStack();
            }
        }

        if (itemStack != null && this.client.currentScreen != null) {
            this.client.currentScreen.renderTooltip(matrices, this.client.currentScreen.getTooltipFromItem(itemStack), mouseX, mouseY);
        }

    }

    public void drawGhostSlots(MatrixStack matrices, int x, int y, boolean bl, float delta) {
        this.ghostSlots.draw(matrices, this.client, x, y, bl, delta);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isOpen() && !this.client.player.isSpectator()) {
            if (this.recipesArea.mouseClicked(mouseX, mouseY, button, (this.parentWidth - 147) / 2 - this.leftOffset, (this.parentHeight - 166) / 2, 147, 166)) {
                Recipe<?> recipe = this.recipesArea.getLastClickedRecipe();
                VineryRecipeResultCollection recipeResultCollection = this.recipesArea.getLastClickedResults();
                if (recipe != null && recipeResultCollection != null) {
                    if (!recipeResultCollection.isCraftable(recipe) && this.ghostSlots.getRecipe() == recipe) {
                        return false;
                    }

                    this.ghostSlots.reset();
                    this.client.interactionManager.clickRecipe(this.client.player.currentScreenHandler.syncId, recipe, Screen.hasShiftDown());
                    if (!this.isWide()) {
                        this.setOpen(false);
                    }
                }

                return true;
            } else if (this.searchField.mouseClicked(mouseX, mouseY, button)) {
                return true;
            } else if (this.toggleCraftableButton.mouseClicked(mouseX, mouseY, button)) {
                boolean bl = this.toggleFilteringCraftable();
                this.toggleCraftableButton.setToggled(bl);
                this.sendBookDataPacket();
                this.refreshResults(false);
                return true;
            } else {
                Iterator var6 = this.tabButtons.iterator();

                VineryRecipeGroupButtonWidget recipeGroupButtonWidget;
                do {
                    if (!var6.hasNext()) {
                        return false;
                    }

                    recipeGroupButtonWidget = (VineryRecipeGroupButtonWidget)var6.next();
                } while(!recipeGroupButtonWidget.mouseClicked(mouseX, mouseY, button));

                if (this.currentTab != recipeGroupButtonWidget) {
                    if (this.currentTab != null) {
                        this.currentTab.setToggled(false);
                    }

                    this.currentTab = recipeGroupButtonWidget;
                    this.currentTab.setToggled(true);
                    this.refreshResults(true);
                }

                return true;
            }
        } else {
            return false;
        }
    }

    private boolean toggleFilteringCraftable() {
        //VineryRecipeBookCategory recipeBookCategory = this.craftingScreenHandler.getVineryCategory();
        boolean bl = !Candlelight.rememberedCraftableToggle;
        Candlelight.rememberedCraftableToggle = bl;
        return bl;
    }

    public boolean isClickOutsideBounds(double mouseX, double mouseY, int x, int y, int backgroundWidth, int backgroundHeight, int button) {
        if (!this.isOpen()) {
            return true;
        } else {
            boolean bl = mouseX < (double)x || mouseY < (double)y || mouseX >= (double)(x + backgroundWidth) || mouseY >= (double)(y + backgroundHeight);
            boolean bl2 = (double)(x - 147) < mouseX && mouseX < (double)x && (double)y < mouseY && mouseY < (double)(y + backgroundHeight);
            return bl && !bl2 && !this.currentTab.isHovered();
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.searching = false;
        if (this.isOpen() && !this.client.player.isSpectator()) {
            if (keyCode == 256 && !this.isWide()) {
                this.setOpen(false);
                return true;
            } else if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
                this.refreshSearchResults();
                return true;
            } else if (this.searchField.isFocused() && this.searchField.isVisible() && keyCode != 256) {
                return true;
            } else if (this.client.options.chatKey.matchesKey(keyCode, scanCode) && !this.searchField.isFocused()) {
                this.searching = true;
                this.searchField.setTextFieldFocused(true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.searching = false;
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    public boolean charTyped(char chr, int modifiers) {
        if (this.searching) {
            return false;
        } else if (this.isOpen() && !this.client.player.isSpectator()) {
            if (this.searchField.charTyped(chr, modifiers)) {
                this.refreshSearchResults();
                return true;
            }
        }
        return false;
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return false;
    }

    private void refreshSearchResults() {
        String string = this.searchField.getText().toLowerCase(Locale.ROOT);
        this.triggerPirateSpeakEasterEgg(string);
        if (!string.equals(this.searchText)) {
            this.refreshResults(false);
            this.searchText = string;
        }

    }

    private void triggerPirateSpeakEasterEgg(String search) {
        if ("excitedze".equals(search)) {
            LanguageManager languageManager = this.client.getLanguageManager();
            LanguageDefinition languageDefinition = languageManager.getLanguage("en_pt");
            if (languageManager.getLanguage().compareTo(languageDefinition) == 0) {
                return;
            }

            languageManager.setLanguage(languageDefinition);
            this.client.options.language = languageDefinition.getCode();
            this.client.reloadResources();
            this.client.options.write();
        }

    }

    private boolean isWide() {
        return this.leftOffset == 86;
    }

    public void refresh() {
        this.refreshTabButtons();
        if (this.isOpen()) {
            this.refreshResults(false);
        }

    }

    public void onRecipesDisplayed(List<Recipe<?>> recipes) {
        for (Recipe<?> recipe : recipes) {
            this.client.player.onRecipeDisplayed(recipe);
        }
    }

    public void showGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        ItemStack itemStack = recipe.getOutput();
        this.ghostSlots.setRecipe(recipe);
        this.ghostSlots.addSlot(Ingredient.ofStacks(itemStack), (slots.get(0)).x, (slots.get(0)).y);
        this.alignRecipeToGrid(this.craftingScreenHandler.getCraftingWidth(), this.craftingScreenHandler.getCraftingHeight(), this.craftingScreenHandler.getCraftingResultSlotIndex(), recipe, recipe.getIngredients().iterator(), 0);
    }

    public void acceptAlignedInput(Iterator<Ingredient> inputs, int slot, int amount, int gridX, int gridY) {
        Ingredient ingredient = (inputs.next());
        if (!ingredient.isEmpty()) {
            Slot slot2 = this.craftingScreenHandler.slots.get(slot);
            this.ghostSlots.addSlot(ingredient, slot2.x, slot2.y);
        }

    }

    protected void sendBookDataPacket() {
        if (this.client.getNetworkHandler() != null) {
            /*
            VineryRecipeBookCategory recipeBookCategory = this.craftingScreenHandler.getVineryCategory();
            boolean bl = this.recipeBook.isGuiOpen(recipeBookCategory);
            boolean bl2 = this.recipeBook.isFilteringCraftable();
            this.client.getNetworkHandler().sendPacket(new RecipeCategoryOptionsC2SPacket(recipeBookCategory, bl, bl2));

             */
        }

    }

    public Selectable.SelectionType getType() {
        return this.open ? SelectionType.HOVERED : SelectionType.NONE;
    }

    public void appendNarrations(NarrationMessageBuilder builder) {//TODO
        /*
        List<Selectable> list = Lists.newArrayList();
        this.recipesArea.forEachButton((button) -> {
            if (button.isNarratable()) {
                list.add(button);
            }

        });
        list.add(this.searchField);
        list.add(this.toggleCraftableButton);
        list.addAll(this.tabButtons);
        Screen.SelectedElementNarrationData selectedElementNarrationData = Screen.findSelectedElementData(list, (Selectable)null);
        if (selectedElementNarrationData != null) {
            selectedElementNarrationData.selectable.appendNarrations(builder.nextMessage());
        }

         */

    }

    static {
        SEARCH_HINT_TEXT = Text.translatable("gui.recipebook.search_hint").formatted(Formatting.ITALIC).formatted(Formatting.GRAY);
        TOGGLE_CRAFTABLE_RECIPES_TEXT = Text.translatable("gui.recipebook.toggleRecipes.craftable");
        TOGGLE_ALL_RECIPES_TEXT = Text.translatable("gui.recipebook.toggleRecipes.all");
    }
}