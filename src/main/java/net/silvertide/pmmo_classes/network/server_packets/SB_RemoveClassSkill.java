package net.silvertide.pmmo_classes.network.server_packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.data.AscendedClassSkill;
import net.silvertide.pmmo_classes.data.PlayerClassProfile;
import net.silvertide.pmmo_classes.data.PrimaryClassSkill;
import net.silvertide.pmmo_classes.data.SubClassSkill;
import net.silvertide.pmmo_classes.network.client_packets.CB_ClassRemoved;
import net.silvertide.pmmo_classes.utils.GUIUtil;
import net.silvertide.pmmo_classes.utils.PMMOUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public record SB_RemoveClassSkill(String skill) implements CustomPacketPayload {
    public static final Type<SB_RemoveClassSkill> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PMMOClasses.MOD_ID, "sb_remove_class_skill"));
    public static final StreamCodec<FriendlyByteBuf, SB_RemoveClassSkill> STREAM_CODEC = StreamCodec
            .composite(ByteBufCodecs.STRING_UTF8, SB_RemoveClassSkill::skill,
                    SB_RemoveClassSkill::new);

    public static void handle(SB_RemoveClassSkill packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if(ctx.player() instanceof ServerPlayer serverPlayer) {
                String primaryClassSkillToRemove = packet.skill();
                PlayerClassProfile profile = new PlayerClassProfile(serverPlayer);
                List<String> skillsToRemove = getSkillsToRemove(profile, primaryClassSkillToRemove);

                if(!skillsToRemove.isEmpty()) {
                    PMMOUtil.deleteSkills(serverPlayer, skillsToRemove);
                    PacketDistributor.sendToPlayer(serverPlayer, new CB_ClassRemoved());
                    sendUpdateMessageToPlayer(serverPlayer, skillsToRemove);
                }
            }
        });
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

    @Override
    public @NotNull Type<SB_RemoveClassSkill> type() { return TYPE; }
}
