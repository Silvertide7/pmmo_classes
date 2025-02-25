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
import net.silvertide.pmmo_classes.data.PlayerClassProfile;
import net.silvertide.pmmo_classes.data.PrimaryClassSkill;
import net.silvertide.pmmo_classes.data.SubClassSkill;
import net.silvertide.pmmo_classes.utils.GUIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ManageClassesScreen extends Screen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(PMMOClasses.MOD_ID, "textures/gui/gui_manage_classes_screen.png");
    private static final int SCREEN_WIDTH = 146;
    private static final int MAX_SCREEN_HEIGHT = 101;

    private static final int CARD_HEIGHT = 25;
    private static final int CARD_WIDTH = 108;

    //CLOSE BUTTON CONSTANTS
    private static final int CLOSE_BUTTON_X = 130;
    private static final int CLOSE_BUTTON_Y = 4;
    private static final int CLOSE_BUTTON_WIDTH = 12;
    private static final int CLOSE_BUTTON_HEIGHT = 12;

    private boolean closeButtonDown = false;

    private final PlayerClassProfile classProfile;
    private final List<ClassSkillRenderer> skillRenderers;

    public ManageClassesScreen() {
        super(Component.literal(""));
        this.classProfile = new PlayerClassProfile(Minecraft.getInstance().player);
        this.skillRenderers = new ArrayList<>();
        addSkillRenderers();
    }

    private void addSkillRenderers() {
        int index = 0;
        for (Map.Entry<PrimaryClassSkill, Experience> entry : this.classProfile.getPrimaryClassMap().entrySet()) {
            SubClassSkill subClassSkill = this.classProfile.findMatchingSubClass(entry.getKey()).orElse(null);
            ClassSkillRenderer classSkillRenderer = new ClassSkillRenderer(this, index, entry.getKey(),entry.getValue().getLevel().getLevel(), subClassSkill);
            this.skillRenderers.add(classSkillRenderer);
            index++;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        try {
            super.render(guiGraphics, mouseX, mouseY, partialTicks);
        } catch (Exception ignore) {
            onClose();
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderTransparentBackground(guiGraphics);
        renderScreenBackground(guiGraphics);
        renderTitle(guiGraphics);
        renderCloseButton(guiGraphics, mouseX, mouseY);

        if(!skillRenderers.isEmpty()) {
            for(ClassSkillRenderer skillRenderer : skillRenderers) {
                skillRenderer.render(guiGraphics, mouseX, mouseY);
            }
        } else {
            renderNoClassesText(guiGraphics);
        }
    }

    private void renderNoClassesText(GuiGraphics guiGraphics) {
        Component buttonTextComp = Component.translatable("pmmo_classes.screen.manage_classes.no_classes_text");
        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.85F, this.font, buttonTextComp, this.getScreenStartX() + SCREEN_WIDTH / 2, this.getScreenStartY() + this.getBackgroundHeight() / 2, 100, 0xFFFFFF);
    }

    private void renderTitle(GuiGraphics guiGraphics) {
        Component buttonTextComp = Component.translatable("pmmo_classes.screen.manage_classes.title");
        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.85F, this.font, buttonTextComp, this.getScreenStartX() + SCREEN_WIDTH / 2, this.getScreenStartY() + 8, 100, 0xFFFFFF);
    }

    private void renderScreenBackground(GuiGraphics guiGraphics) {
        int x = this.getScreenStartX();
        int y = this.getScreenStartY();

        int backgroundHeight = getBackgroundHeight();
        guiGraphics.blit(TEXTURE, x, y, 0, 0, SCREEN_WIDTH, backgroundHeight);
        guiGraphics.blit(TEXTURE, x, y + backgroundHeight, 0, 103, SCREEN_WIDTH, 4);
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

    // CLOSE BUTTON
    private void renderCloseButton(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int buttonX = this.getScreenStartX() + CLOSE_BUTTON_X;
        int buttonY = this.getScreenStartY() + CLOSE_BUTTON_Y;

        int buttonOffset = getCloseButtonOffsetToRender(mouseX, mouseY);
        guiGraphics.blit(TEXTURE, buttonX, buttonY, 147, buttonOffset, CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT);
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

    private boolean isHoveringCloseButton(double mouseX, double mouseY) {
        return isHovering(CLOSE_BUTTON_X, CLOSE_BUTTON_Y, CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT, mouseX, mouseY);
    }

    private boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        return GUIUtil.isHovering(this.getScreenStartX(), this.getScreenStartY(), pX, pY, pWidth, pHeight, pMouseX, pMouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(closeButtonDown && isHoveringCloseButton(mouseX, mouseY)) {
            this.onClose();
            return true;
        } else {
            for(ClassSkillRenderer skillRenderer : skillRenderers) {
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
            for(ClassSkillRenderer skillRenderer : skillRenderers) {
                skillRenderer.mouseClicked(mouseX, mouseY);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() { return false; }


    private class ClassSkillRenderer {
        private static final int CARD_X = 17;
        private static final int CARD_Y = 19;

        private final int DELETE_BUTTON_X= 95;
        private final int DELETE_BUTTON_Y = 3;
        private final int DELETE_BUTTON_WIDTH = 12;
        private final int DELETE_BUTTON_HEIGHT = 9;

        // Control Data
        private boolean isDeleteButtonDown = false;

        //Incoming Instance Data
        private ManageClassesScreen manageClassesScreen;
        private int order;
        private PrimaryClassSkill primaryClassSkill;
        private int primaryLevel;
        private SubClassSkill subClassSkill;

        public ClassSkillRenderer(ManageClassesScreen manageClassesScreen, int order, PrimaryClassSkill primaryClassSkill, long primaryLevel, SubClassSkill subClassSkill) {
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
//            renderLevel(guiGraphics);
//            renderName(guiGraphics);

            renderDeleteButton(guiGraphics, mouseX, mouseY);
        }

        private void renderCard(GuiGraphics guiGraphics) {
            guiGraphics.blit(TEXTURE, getCardStartX(), getCardStartY(), 147, 0, CARD_WIDTH, CARD_HEIGHT);
        }

        private void renderClassLogo(GuiGraphics guiGraphics) {
            int rankOffset = (Math.min(4, primaryLevel) - 1) * 22;
            guiGraphics.blit(TEXTURE, getCardStartX() + 2, getCardStartY() + 2, this.primaryClassSkill.getXOffset() + rankOffset, this.primaryClassSkill.getYOffset(), 21, 21);
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
                PMMOClasses.LOGGER.info("Click");
//                minecraft.pushGuiLayer(new DeleteConfirmationScreen(this.manageScreen, attunedItem));
            }
        }

        public void mouseClicked(double mouseX, double mouseY) {
            if(isHoveringDeleteButton(mouseX, mouseY)) {
                this.isDeleteButtonDown = true;
            }
        }
    }
}
