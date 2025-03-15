package net.silvertide.pmmo_classes.events;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.core.Core;
import harmonised.pmmo.core.IDataStorage;
import harmonised.pmmo.network.Networking;
import harmonised.pmmo.network.clientpackets.CP_SyncData_ClearXp;
import harmonised.pmmo.storage.Experience;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.EventBusSubscriber;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.config.ServerConfig;
import net.silvertide.pmmo_classes.data.*;
import net.silvertide.pmmo_classes.utils.ClassUtil;
import net.silvertide.pmmo_skill_books.events.SkillGrantEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = PMMOClasses.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent()
    public static void onSkillGrantEvent(SkillGrantEvent.Post skillGrantEvent) {
        if(skillGrantEvent.getEntity() instanceof ServerPlayer serverPlayer) {
            ClassUtil.getClassSkill(skillGrantEvent.getSkill()).ifPresent(classSkill -> {

                PlayerClassProfile playerClassProfile = new PlayerClassProfile(serverPlayer);
                // First check if the player should receive an ascended class skill and grant it if so.
                if(ServerConfig.ASCENDED_CLASSES_ACTIVE.get() && playerClassProfile.getAscendedClassSkill() == null) {
                    if(playerClassProfile.getPrimaryClassMap().size() == 2) {
                        List<PrimaryClassSkill> primaryClassesMeetingRequirements = new ArrayList<>();
                        for(Map.Entry<PrimaryClassSkill, Experience> entry : playerClassProfile.getPrimaryClassMap().entrySet()) {
                            if(entry.getValue().getLevel().getLevel() >= ServerConfig.ASCENDED_CLASS_LEVEL_REQ.get()) {
                                primaryClassesMeetingRequirements.add(entry.getKey());
                            }
                        }
                        if(primaryClassesMeetingRequirements.size() == 2) {
                            AscendedClassSkill.getAscendedClassSkill(primaryClassesMeetingRequirements.get(0), primaryClassesMeetingRequirements.get(1))
                                    .ifPresent(ascendedClassSkill -> {
                                APIUtils.setLevel(ascendedClassSkill.getSkillName(), serverPlayer, 1);
                                    serverPlayer.sendSystemMessage(Component.translatable("pmmo_classes.message.gained_ascended_class", ascendedClassSkill.getTranslatedSkillName()));
                            });
                        }
                    }
                }

                // Check if a subclass was added and a conflict exists. Remove the old one if so.
                if(classSkill.getClassType() == ClassType.SUB) {
                    SubClassSkill subClassSkill = (SubClassSkill) classSkill;
                    if(playerClassProfile.getSubClassMap().size() > 1) {
                        for(SubClassSkill subClassBeingChecked : playerClassProfile.getSubClassesAsList()) {
                            if(subClassBeingChecked != subClassSkill && subClassBeingChecked.getParentClass() == subClassSkill.getParentClass()) {
                                IDataStorage data = Core.get(LogicalSide.SERVER).getData();
                                data.getXpMap(serverPlayer.getUUID()).remove(subClassBeingChecked.getSkillName());
                                Networking.sendToClient(new CP_SyncData_ClearXp(subClassBeingChecked.getSkillName()), serverPlayer);
                            }
                        }
                    }
                }
            });
        }
    }
}
