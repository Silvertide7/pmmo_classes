package net.silvertide.pmmo_classes.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.events.custom.GainClassSkillEvent;


@EventBusSubscriber(modid = PMMOClasses.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    @SubscribeEvent(priority=EventPriority.LOW)
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        if (player instanceof ServerPlayer serverPlayer) {
            // Sync attuned items and information to player
//            Map<UUID, AttunedItem> attunedItems = ArtifactorySavedData.get().getAttunedItems(serverPlayer.getUUID());
//            NetworkUtil.updateAllAttunedItems(serverPlayer, attunedItems);
//            ArtifactorySavedData.get().updatePlayerDisplayName(serverPlayer);
        }
    }

    @SubscribeEvent()
    public static void onPlayerGainClass(GainClassSkillEvent gainClassSkillEvent) {
        PMMOClasses.LOGGER.info("Gained a skill " + gainClassSkillEvent.getSkill());
    }
}
