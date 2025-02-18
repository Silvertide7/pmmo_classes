package net.silvertide.pmmo_classes.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.silvertide.pmmo_classes.PMMOClasses;

@OnlyIn(Dist.CLIENT)
public class ManageClassesScreen extends Screen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(PMMOClasses.MOD_ID, "textures/gui/gui_manage_classes_screen.png");
    private static final int SCREEN_WIDTH = 146;
    private static final int SCREEN_HEIGHT = 81;

    //138 = 98
    //73 = 5 pixels = 58 = 29 pixel height

    public ManageClassesScreen() {
        super(Component.literal(""));
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
        int x = getScreenStartX();
        int y = getScreenStartY();

        guiGraphics.blit(TEXTURE, x, y, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private int getScreenStartX() {
        return (this.width - SCREEN_WIDTH) / 2;
    }

    private int getScreenStartY() {
        return (this.height - SCREEN_HEIGHT) / 2;
    }
}
