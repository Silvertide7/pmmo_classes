package net.silvertide.pmmo_classes.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.data.PrimaryClassSkill;
import net.silvertide.pmmo_classes.network.PacketHandler;
import net.silvertide.pmmo_classes.network.server_packets.SB_RemoveClassSkill;
import net.silvertide.pmmo_classes.utils.GUIUtil;

@OnlyIn(Dist.CLIENT)
public class DeleteConfirmationScreen extends Screen {
    private static final float TEXT_SCALE = 0.85F;
    private static final ResourceLocation TEXTURE = new ResourceLocation(PMMOClasses.MOD_ID, "textures/gui/gui_delete_class_confirmation.png");
    private static final int SCREEN_WIDTH = 146;
    private static final int SCREEN_HEIGHT = 64;

    // Button Constants
    private static final int BUTTON_Y = 46;
    private static final int BUTTON_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 12;

    // Instance Variables
    private final ManageClassesScreen manageScreen;
    private final PrimaryClassSkill classSkillToDelete;

    private boolean cancelButtonDown = false;
    private boolean confirmButtonDown = false;

    protected DeleteConfirmationScreen(ManageClassesScreen manageScreen, PrimaryClassSkill classSkillToDelete) {
        super(Component.literal(""));
        this.manageScreen = manageScreen;
        this.classSkillToDelete = classSkillToDelete;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        try {
            renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
            super.render(guiGraphics, mouseX, mouseY, partialTicks);
        } catch (Exception ignore) {
            onClose();
        }
    }

    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = getScreenStartX();
        int y = getScreenStartY();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0F, 0F, 1000F);
        guiGraphics.blit(TEXTURE, x, y, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        renderDeleteText(guiGraphics);
        renderButtons(guiGraphics, mouseX, mouseY);

        guiGraphics.pose().popPose();
    }

    private int getScreenStartX() {
        return (this.width - SCREEN_WIDTH) / 2;
    }

    private int getScreenStartY() {
        return (this.height - SCREEN_HEIGHT) / 2;
    }

    private void renderDeleteText(GuiGraphics guiGraphics) {
        Component deleteText = Component.translatable("screen.text.pmmo_classes.confirmation.delete_text", GUIUtil.prettifyEnum(this.classSkillToDelete));
        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, TEXT_SCALE, this.font, deleteText, getScreenStartX() + SCREEN_WIDTH / 2, getScreenStartY() + 18, SCREEN_WIDTH * 8 / 10, 0xFFFFFF);
    }

    private void renderButtons(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderConfirmButton(guiGraphics, mouseX, mouseY);
        renderCancelButton(guiGraphics, mouseX, mouseY);
    }

    private void renderConfirmButton(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int buttonX = getConfirmButtonX();
        int buttonY = getButtonY();

        boolean isHovering = isHoveringConfirmButton(mouseX, mouseY);
        int buttonOffset = getButtonOffsetToRender(confirmButtonDown, isHovering, mouseX, mouseY);
        guiGraphics.blit(TEXTURE, buttonX, buttonY, 147, buttonOffset, BUTTON_WIDTH, BUTTON_HEIGHT);

        Component buttonTextComp = Component.translatable("screen.button.pmmo_classes.confirmation.confirm");
        int buttonTextX = buttonX + BUTTON_WIDTH / 2;
        int buttonTextY = buttonY + BUTTON_HEIGHT / 2;

        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, TEXT_SCALE, this.font, buttonTextComp, buttonTextX, buttonTextY, BUTTON_WIDTH, 0xFFFFFF);
    }

    private int getConfirmButtonX() {
        return getScreenStartX() + getButtonSpacing();
    }

    private void renderCancelButton(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int buttonX = getCancelButtonX();
        int buttonY = getButtonY();

        boolean isHovering = isHoveringCancelButton(mouseX, mouseY);
        int buttonOffset = getButtonOffsetToRender(cancelButtonDown, isHovering, mouseX, mouseY);
        guiGraphics.blit(TEXTURE, buttonX, buttonY, 147, buttonOffset, BUTTON_WIDTH, BUTTON_HEIGHT);

        int buttonTextX = buttonX + BUTTON_WIDTH / 2;
        int buttonTextY = buttonY + BUTTON_HEIGHT / 2;
        Component buttonTextComp = Component.translatable("screen.button.pmmo_classes.confirmation.cancel");

        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, TEXT_SCALE, this.font, buttonTextComp, buttonTextX, buttonTextY, BUTTON_WIDTH, 0xFFFFFF);
    }

    private int getCancelButtonX() {
        return getScreenStartX() + getButtonSpacing() * 2 + BUTTON_WIDTH ;
    }

    private int getButtonSpacing() {
        return (SCREEN_WIDTH - BUTTON_WIDTH * 2) / 3;
    }

    private int getButtonY() {
        return getScreenStartY() + BUTTON_Y;
    }

    private int getButtonOffsetToRender(boolean isButtonDown, boolean isHoveringButton, int mouseX, int mouseY) {
        if(isButtonDown) {
            return 26;
        }
        else if (isHoveringButton) {
            return 13;
        }
        else {
            return 0;
        }
    }

    private boolean isHoveringCancelButton(double mouseX, double mouseY) {
        return GUIUtil.isHovering(getCancelButtonX(), getButtonY(), BUTTON_WIDTH, BUTTON_HEIGHT, mouseX, mouseY);
    }

    private boolean isHoveringConfirmButton(double mouseX, double mouseY) {
        return GUIUtil.isHovering(getConfirmButtonX(), getButtonY(), BUTTON_WIDTH, BUTTON_HEIGHT, mouseX, mouseY);
    }

    @Override
    public void onClose() {
        super.onClose();
        this.manageScreen.createClassCards();
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(cancelButtonDown && isHoveringCancelButton(mouseX, mouseY)) {
            this.onClose();
            return true;
        } else if(confirmButtonDown && isHoveringConfirmButton(mouseX, mouseY)){
            PacketHandler.sendToServer(new SB_RemoveClassSkill(this.classSkillToDelete.getSkillName()));
            this.onClose();
            return true;
        }
        cancelButtonDown = false;
        confirmButtonDown = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(isHoveringCancelButton(mouseX, mouseY)) {
            cancelButtonDown = true;
            return true;
        } else if (isHoveringConfirmButton(mouseX, mouseY)){
            confirmButtonDown = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
