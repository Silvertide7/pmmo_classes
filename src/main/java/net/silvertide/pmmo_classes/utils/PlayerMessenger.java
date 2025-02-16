package net.silvertide.pmmo_classes.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public final class PlayerMessenger {
    private PlayerMessenger() {}
    public static void displayTranslatabelClientMessage(Player player, Component translation) {
        player.displayClientMessage(translation, true);
    }
}
