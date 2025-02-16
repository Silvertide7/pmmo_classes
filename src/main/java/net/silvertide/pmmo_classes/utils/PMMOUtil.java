package net.silvertide.pmmo_classes.utils;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.config.Config;
import net.minecraft.server.level.ServerPlayer;
import net.silvertide.pmmo_classes.PMMOClasses;

public final class PMMOUtil {
    private PMMOUtil() {}

    public static boolean isPlayerAtMaxLevel(ServerPlayer player, String skill) {
        boolean result = false;
        try {
            result = APIUtils.getLevel(skill, player) >= Config.server().levels().maxLevel();
        } catch (IllegalArgumentException ignored) {
            PMMOClasses.LOGGER.error("PMMOClasses - isPlayerAtMaxLevel - IllegalArgumentException");
        }
        return result;
    }
}