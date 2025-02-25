package net.silvertide.pmmo_classes.network.server_packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.silvertide.pmmo_classes.PMMOClasses;
import org.jetbrains.annotations.NotNull;


public record SB_RemoveClassSkill(String skill) implements CustomPacketPayload {
    public static final Type<SB_RemoveClassSkill> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PMMOClasses.MOD_ID, "sb_remove_class_skill"));
    public static final StreamCodec<FriendlyByteBuf, SB_RemoveClassSkill> STREAM_CODEC = StreamCodec
            .composite(ByteBufCodecs.STRING_UTF8, SB_RemoveClassSkill::skill,
                    SB_RemoveClassSkill::new);

    public static void handle(SB_RemoveClassSkill packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if(ctx.player() instanceof ServerPlayer player) {
                //TODO: Remove skill / subclasses / ascended classes
            }
        });
    }

    @Override
    public @NotNull Type<SB_RemoveClassSkill> type() { return TYPE; }
}
