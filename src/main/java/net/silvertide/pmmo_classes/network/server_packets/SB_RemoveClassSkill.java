package net.silvertide.pmmo_classes.network.server_packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.silvertide.pmmo_classes.data.AscendedClassSkill;
import net.silvertide.pmmo_classes.data.PlayerClassProfile;
import net.silvertide.pmmo_classes.data.PrimaryClassSkill;
import net.silvertide.pmmo_classes.data.SubClassSkill;
import net.silvertide.pmmo_classes.network.PacketHandler;
import net.silvertide.pmmo_classes.network.client_packets.CB_ClassRemoved;
import net.silvertide.pmmo_classes.utils.GUIUtil;
import net.silvertide.pmmo_classes.utils.PMMOUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class SB_RemoveClassSkill {
    private final String skill;

    public SB_RemoveClassSkill(String skill) {
        this.skill = skill;
    }

    public SB_RemoveClassSkill(FriendlyByteBuf buf) {
        this.skill = buf.readUtf();
    }

    public String getSkill() {
        return skill;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(this.skill);
    }

    public static void handle(SB_RemoveClassSkill packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.getSender();
            if(serverPlayer != null) {
                String primaryClassSkillToRemove = packet.getSkill();
                PlayerClassProfile profile = new PlayerClassProfile(serverPlayer);
                List<String> skillsToRemove = getSkillsToRemove(profile, primaryClassSkillToRemove);

                if(!skillsToRemove.isEmpty()) {
                    PMMOUtil.deleteSkills(serverPlayer, skillsToRemove);
                    PacketHandler.sendToClient(serverPlayer, new CB_ClassRemoved());
                    sendUpdateMessageToPlayer(serverPlayer, skillsToRemove);
                }
            }
        });
        ctx.setPacketHandled(true);
    }

    private static void sendUpdateMessageToPlayer(ServerPlayer serverPlayer, List<String> skillsToRemove) {
        StringBuilder skillsRemovedDisplay = new StringBuilder();
        skillsRemovedDisplay.append("[ ");
        for(int i = 0; i < skillsToRemove.size(); i++) {
            skillsRemovedDisplay.append(GUIUtil.prettifyName(skillsToRemove.get(i)));

            if(i != skillsToRemove.size() - 1) {
                skillsRemovedDisplay.append(", ");
            }
        }
        skillsRemovedDisplay.append("]");
        serverPlayer.sendSystemMessage(Component.translatable("pmmo_classes.message.remove_classes", skillsRemovedDisplay.toString()));
    }

    private static @NotNull List<String> getSkillsToRemove(PlayerClassProfile profile, String primaryClassSkillToRemove) {
        List<String> skillsToRemove = new ArrayList<>();

        for(PrimaryClassSkill primaryClassSkill : profile.getPrimaryClassesAsList()) {
            if(primaryClassSkillToRemove.equals(primaryClassSkill.getSkillName())) {
                skillsToRemove.add(primaryClassSkill.getSkillName());
            }
        }

        for(SubClassSkill subClassSkill : profile.getSubClassesAsList()) {
            if(primaryClassSkillToRemove.equals(subClassSkill.getParentClass().getSkillName())) {
                skillsToRemove.add(subClassSkill.getSkillName());
            }
        }

        AscendedClassSkill ascendedClassSkill = profile.getAscendedClassSkill();
        if(ascendedClassSkill != null && (primaryClassSkillToRemove.equals(ascendedClassSkill.getFirstPrimaryClass().getSkillName()) || primaryClassSkillToRemove.equals(ascendedClassSkill.getSecondPrimaryClass().getSkillName()))) {
            skillsToRemove.add(ascendedClassSkill.getSkillName());
        }
        return skillsToRemove;
    }
}
