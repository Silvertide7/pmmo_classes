package net.silvertide.pmmo_classes.utils;

import harmonised.pmmo.core.Core;
import harmonised.pmmo.core.IDataStorage;
import harmonised.pmmo.network.Networking;
import harmonised.pmmo.network.clientpackets.CP_SyncData_ClearXp;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.LogicalSide;

import java.util.List;

public final class PMMOUtil {
    private PMMOUtil() {}

    public static void deleteSkills(ServerPlayer serverPlayer, List<String> skillsToRemove) {
        IDataStorage data = Core.get(LogicalSide.SERVER).getData();
        skillsToRemove.forEach(skill -> {
            data.setXpRaw(serverPlayer.getUUID(), skill, 0L);
            data.getXpMap(serverPlayer.getUUID()).remove(skill);
            Networking.sendToClient(new CP_SyncData_ClearXp(skill), serverPlayer);
        });
    }
}