package net.silvertide.pmmo_classes.events;

import harmonised.pmmo.core.Core;
import harmonised.pmmo.core.IDataStorage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.data.*;
import net.silvertide.pmmo_classes.utils.ClassUtil;
import net.silvertide.pmmo_classes.utils.PMMOUtil;
import net.silvertide.pmmo_skill_books.events.SkillGrantEvent;

import java.util.List;
import java.util.Map;


@EventBusSubscriber(modid = PMMOClasses.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent()
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        if (player instanceof ServerPlayer serverPlayer) {
            IDataStorage dataStore = Core.get(LogicalSide.SERVER).getData();
            List<String> skillsToClear = dataStore.getXpMap(serverPlayer.getUUID()).entrySet().stream()
                    .filter(entry -> entry.getValue().getLevel().getLevel() == 0L && entry.getValue().getXp() == 0L)
                    .map(Map.Entry::getKey).toList();

            if(!skillsToClear.isEmpty()) PMMOUtil.deleteSkills(serverPlayer, skillsToClear);

            PlayerClassProfile playerClassProfile = new PlayerClassProfile(serverPlayer);
            if(playerClassProfile.checkAndUpdatePrimaryClasses(serverPlayer)) {
                playerClassProfile.refreshProfile(serverPlayer);
            }
            if(playerClassProfile.checkAndUpdateSubClasses(serverPlayer, null)) {
                playerClassProfile.refreshProfile(serverPlayer);
            };
            playerClassProfile.checkAndUpdateAscendedClasses(serverPlayer);
        }
    }

    @SubscribeEvent()
    public static void onSkillGrantEvent(SkillGrantEvent.Post skillGrantEvent) {
        if(skillGrantEvent.getEntity() instanceof ServerPlayer serverPlayer) {
            ClassUtil.getClassSkill(skillGrantEvent.getSkill()).ifPresent(classSkill -> {

                PlayerClassProfile playerClassProfile = new PlayerClassProfile(serverPlayer);

                // Check if the player should receive an ascended class skill and grant it if so.
                playerClassProfile.checkAndUpdateAscendedClasses(serverPlayer);

                // Check if a subclass was added and a conflict exists. Remove the old one if so.
                if(classSkill.getClassType() == ClassType.SUB) {
                    playerClassProfile.checkAndUpdateSubClasses(serverPlayer, (SubClassSkill) classSkill);
                }
            });
        }
    }
}
