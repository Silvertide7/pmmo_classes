package net.silvertide.pmmo_classes.gui;

import harmonised.pmmo.storage.Experience;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.client.keybindings.Keybindings;
import net.silvertide.pmmo_classes.data.PlayerClassProfile;
import net.silvertide.pmmo_classes.data.PrimaryClassSkill;
import net.silvertide.pmmo_classes.data.SubClassSkill;
import net.silvertide.pmmo_classes.utils.GUIUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ManageClassesScreen extends Screen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(PMMOClasses.MOD_ID, "textures/gui/gui_manage_classes_screen.png");
    private static final ResourceLocation CLASS_ICON_TEXTURE = ResourceLocation.fromNamespaceAndPath(PMMOClasses.MOD_ID, "textures/gui/class_icons.png");
    private static final int SCREEN_WIDTH = 146;
    private static final int MAX_SCREEN_HEIGHT = 106;

    private static final int CARD_HEIGHT = 25;
    private static final int CARD_WIDTH = 108;

    //CLOSE BUTTON CONSTANTS
    private static final int CLOSE_BUTTON_X = 130;
    private static final int CLOSE_BUTTON_Y = 4;
    private static final int CLOSE_BUTTON_WIDTH = 12;
    private static final int CLOSE_BUTTON_HEIGHT = 12;

    private boolean closeButtonDown = false;

    private PlayerClassProfile classProfile;
    private List<ClassCard> classCards;

    public ManageClassesScreen() {
        super(Component.literal(""));
        createClassCards();
    }

    public void createClassCards() {
        this.classProfile = new PlayerClassProfile(Minecraft.getInstance().player);
        this.classCards = new ArrayList<>();
        int index = 0;
        for (Map.Entry<PrimaryClassSkill, Experience> entry : this.classProfile.getPrimaryClassMap().entrySet()) {
            SubClassSkill subClassSkill = this.classProfile.findMatchingSubClass(entry.getKey()).orElse(null);
            ClassCard classSkillRenderer = new ClassCard(this, index, entry.getKey(),entry.getValue().getLevel().getLevel(), subClassSkill);
            this.classCards.add(classSkillRenderer);
            index++;
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        try {
            super.render(guiGraphics, mouseX, mouseY, partialTicks);
        } catch (Exception ignore) {
            onClose();
        }
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderTransparentBackground(guiGraphics);
        renderScreenBackground(guiGraphics);
        renderTitle(guiGraphics);
        renderCloseButton(guiGraphics, mouseX, mouseY);

        if(!classCards.isEmpty()) {
            for(ClassCard skillRenderer : classCards) {
                skillRenderer.render(guiGraphics, mouseX, mouseY);
            }
            renderAscendedClassText(guiGraphics);
        } else {
            renderNoClassesText(guiGraphics);
        }
    }

    private void renderScreenBackground(GuiGraphics guiGraphics) {
        int x = this.getScreenStartX();
        int y = this.getScreenStartY();

        int backgroundHeight = getBackgroundHeight();
        guiGraphics.blit(TEXTURE, x, y, 0, 0, SCREEN_WIDTH, backgroundHeight);
        guiGraphics.blit(TEXTURE, x, y + backgroundHeight, 0, 107, SCREEN_WIDTH, 3);
    }

    private void renderTitle(GuiGraphics guiGraphics) {
        Component buttonTextComp = Component.translatable("pmmo_classes.screen.manage_classes.title");
        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.85F, this.font, buttonTextComp, this.getScreenStartX() + SCREEN_WIDTH / 2, this.getScreenStartY() + 8, 100, 0xFFFFFF);
    }

    private void renderNoClassesText(GuiGraphics guiGraphics) {
        Component buttonTextComp = Component.translatable("pmmo_classes.screen.manage_classes.no_classes_text");
        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.7F, this.font, buttonTextComp, this.getScreenStartX() + SCREEN_WIDTH / 2, this.getScreenStartY() + this.getBackgroundHeight() / 2 + 5, 100, 0xFFFFFF);
    }

    private void renderCloseButton(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int buttonX = this.getScreenStartX() + CLOSE_BUTTON_X;
        int buttonY = this.getScreenStartY() + CLOSE_BUTTON_Y;

        int buttonOffset = getCloseButtonOffsetToRender(mouseX, mouseY);
        guiGraphics.blit(TEXTURE, buttonX, buttonY, 147, buttonOffset, CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT);
    }

    private void renderAscendedClassText(@NotNull GuiGraphics guiGraphics) {
        if(classCards.size() == 2 && this.classProfile.getAscendedClassSkill() != null) {
            int ascendedCardX = this.getScreenStartX() + SCREEN_WIDTH / 2;
            int ascendedCardY = this.getScreenStartY() + getBackgroundHeight() / 2 + 34;

            guiGraphics.blit(TEXTURE, ascendedCardX - 47, ascendedCardY - 10, 161, 27, 94, 21);

            Component skillComponent = Component.literal(this.classProfile.getAscendedClassSkill().getTranslatedSkillName());
            GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.75F, this.font, skillComponent, ascendedCardX, ascendedCardY,100, 0xe6f8fa);
        }
    }

    private int getCloseButtonOffsetToRender(int mouseX, int mouseY) {
        if(closeButtonDown) {
            return 52;
        } else if (isHoveringCloseButton(mouseX, mouseY)) {
            return 39;
        } else {
            return 26;
        }
    }

    private int getBackgroundHeight() {
        int baseHeight = 30;
        int cardHeight = Math.min(classProfile.getNumPrimaryClasses(), 2) * (CARD_HEIGHT + 1);
        int ascendedHeight = classProfile.getAscendedClassSkill() != null ? 20 : 0;
        return Math.min(baseHeight + cardHeight + ascendedHeight, MAX_SCREEN_HEIGHT);
    }

    private int getScreenStartX() {
        return (this.width - SCREEN_WIDTH) / 2;
    }

    private int getScreenStartY() {
        return (this.height - getBackgroundHeight()) / 2;
    }

    private boolean isHoveringCloseButton(double mouseX, double mouseY) {
        return isHovering(CLOSE_BUTTON_X, CLOSE_BUTTON_Y, CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT, mouseX, mouseY);
    }

    private boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
        return GUIUtil.isHovering(this.getScreenStartX(), this.getScreenStartY(), x, y, width, height, mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(closeButtonDown && isHoveringCloseButton(mouseX, mouseY)) {
            this.onClose();
            return true;
        } else {
            for(ClassCard skillRenderer : classCards) {
                skillRenderer.mouseReleased(mouseX, mouseY);
            }
        }
        closeButtonDown = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(isHoveringCloseButton(mouseX, mouseY)) {
            closeButtonDown = true;
            return true;
        } else {
            for(ClassCard skillRenderer : classCards) {
                skillRenderer.mouseClicked(mouseX, mouseY);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == Keybindings.INSTANCE.useOpenManageClassesKey.getKey().getValue()) {
            onClose();
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public boolean isPauseScreen() { return false; }


    private class ClassCard {
        private static final int CARD_X = 17;
        private static final int CARD_Y = 19;

        private final int DELETE_BUTTON_X= 94;
        private final int DELETE_BUTTON_Y = 2;
        private final int DELETE_BUTTON_WIDTH = 12;
        private final int DELETE_BUTTON_HEIGHT = 9;

        // Control Data
        private boolean isDeleteButtonDown = false;

        //Incoming Instance Data
        private final ManageClassesScreen manageClassesScreen;
        private final int order;
        private final PrimaryClassSkill primaryClassSkill;
        private int primaryLevel;
        private final SubClassSkill subClassSkill;

        public ClassCard(ManageClassesScreen manageClassesScreen, int order, PrimaryClassSkill primaryClassSkill, long primaryLevel, SubClassSkill subClassSkill) {
            this.manageClassesScreen = manageClassesScreen;
            this.order = order;
            this.primaryClassSkill = primaryClassSkill;
            this.subClassSkill = subClassSkill;

            try {
                this.primaryLevel = Math.toIntExact(primaryLevel);
            } catch (ArithmeticException ex) {
                this.primaryLevel = 1;
            }
        }

        public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
            renderCard(guiGraphics);
            renderClassLogo(guiGraphics);
            renderCardTitle(guiGraphics);
            renderLevel(guiGraphics);
            renderDeleteButton(guiGraphics, mouseX, mouseY);
        }


        private void renderCard(GuiGraphics guiGraphics) {
            guiGraphics.blit(TEXTURE, getCardStartX(), getCardStartY(), 147, 0, CARD_WIDTH, CARD_HEIGHT);
        }

        private void renderClassLogo(GuiGraphics guiGraphics) {
            int rankOffset = GUIUtil.getClassRankHorizOffset(primaryLevel);
            guiGraphics.blit(CLASS_ICON_TEXTURE, getCardStartX() + 2, getCardStartY() + 2, this.primaryClassSkill.getXOffset() + rankOffset, this.primaryClassSkill.getYOffset(), 21, 21);
        }

        private void renderCardTitle(GuiGraphics guiGraphics) {
            int textOffsetX = 28;
            int textOffsetY = 5;
            float textScale = 0.7F;

            GUIUtil.drawScaledString(guiGraphics, textScale, manageClassesScreen.font, this.primaryClassSkill.getTranslatedSkillName(), getCardStartX() + textOffsetX, getCardStartY() + textOffsetY, 0xFFFFFF);

            if(this.subClassSkill != null) {
                int subClassTextOffsetX = 0;
                int subClassTextOffsetY = 8;
                float subClassTextScale = 0.5F;

                GUIUtil.drawScaledString(guiGraphics, subClassTextScale, manageClassesScreen.font, this.subClassSkill.getTranslatedSkillName(), getCardStartX() + textOffsetX + subClassTextOffsetX, getCardStartY() + textOffsetY + subClassTextOffsetY, 0xFFFFFF);
            }
        }

        private void renderLevel(GuiGraphics guiGraphics) {
            int textOffsetX = 75;
            int textOffsetY = 5;
            float textScale = 0.6F;

            Component levelComp = Component.translatable("screen.text.pmmo_classes.manage.level", primaryLevel);
            GUIUtil.drawScaledWordWrap(guiGraphics, textScale, manageClassesScreen.font, levelComp, getCardStartX() + textOffsetX, getCardStartY() + textOffsetY, 40, 0xFFFFFF);
        }

        private void renderDeleteButton(GuiGraphics guiGraphics, double mouseX, double mouseY) {
            if(isHoveringOnCard(0, 0, CARD_WIDTH, CARD_HEIGHT, mouseX, mouseY)){
                guiGraphics.blit(TEXTURE, this.getCardStartX() + DELETE_BUTTON_X, this.getCardStartY() + DELETE_BUTTON_Y, 147, getDeleteButtonOffsetToRender(mouseX, mouseY), DELETE_BUTTON_WIDTH, DELETE_BUTTON_HEIGHT);
            }
        }

        private int getDeleteButtonOffsetToRender(double mouseX, double mouseY) {
            if(isDeleteButtonDown) {
                return 85;
            } else if(isHoveringDeleteButton(mouseX, mouseY)) {
                return 75;
            } else {
                return 65;
            }
        }

        public boolean isHoveringDeleteButton(double mouseX, double mouseY) {
            return this.isHoveringOnCard(DELETE_BUTTON_X, DELETE_BUTTON_Y, DELETE_BUTTON_WIDTH, DELETE_BUTTON_HEIGHT, mouseX, mouseY);
        }

        private boolean isHoveringOnCard(int x, int y, int width, int height, double mouseX, double mouseY) {
            return GUIUtil.isHovering(getCardStartX(), getCardStartY(), x, y, width, height, mouseX, mouseY);
        }

        // HELPERS
        private int getCardStartX() {
            return manageClassesScreen.getScreenStartX() + CARD_X;
        }

        private int getCardStartY() {
            return manageClassesScreen.getScreenStartY() + CARD_Y + (this.order * (CARD_HEIGHT + 1));
        }

        public void mouseReleased(double mouseX, double mouseY) {
            if(isDeleteButtonDown && isHoveringDeleteButton(mouseX, mouseY)) {
                handleDeleteButtonPress();
            }
            this.isDeleteButtonDown = false;
        }

        private void handleDeleteButtonPress() {
            Minecraft minecraft = Minecraft.getInstance();
            if(minecraft.gameMode != null) {
                minecraft.pushGuiLayer(new DeleteConfirmationScreen(this.manageClassesScreen, this.primaryClassSkill));
            }
        }

        public void mouseClicked(double mouseX, double mouseY) {
            if(isHoveringDeleteButton(mouseX, mouseY)) {
                this.isDeleteButtonDown = true;
            }
        }
    }
}
