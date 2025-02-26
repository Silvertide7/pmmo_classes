package net.silvertide.pmmo_classes.network.server_packets;

import com.mojang.brigadier.arguments.StringArgumentType;
import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.core.Core;
import harmonised.pmmo.core.IDataStorage;
import harmonised.pmmo.network.Networking;
import harmonised.pmmo.network.clientpackets.CP_SyncData_ClearXp;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.data.AscendedClassSkill;
import net.silvertide.pmmo_classes.data.PlayerClassProfile;
import net.silvertide.pmmo_classes.data.PrimaryClassSkill;
import net.silvertide.pmmo_classes.data.SubClassSkill;
import net.silvertide.pmmo_classes.network.client_packets.CB_ClassRemoved;
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

                for(String skill : skillsToRemove) {
                    IDataStorage data = Core.get(LogicalSide.SERVER).getData();
                    data.getXpMap(serverPlayer.getUUID()).remove(skill);
                    Networking.sendToClient(new CP_SyncData_ClearXp(skill), serverPlayer);
                }

                if(!skillsToRemove.isEmpty()) {
                    PacketDistributor.sendToPlayer(serverPlayer, new CB_ClassRemoved());
                }
            }
        });
    }

    @Override
    public @NotNull Type<SB_RemoveClassSkill> type() { return TYPE; }
}
