package net.silvertide.pmmo_classes.network.server_packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.network.client_packets.CB_OpenManageClassesScreen;
import org.jetbrains.annotations.NotNull;

public record SB_ToggleManageClassesScreen() implements CustomPacketPayload {
    public static final SB_ToggleManageClassesScreen INSTANCE = new SB_ToggleManageClassesScreen();
    public static final Type<SB_ToggleManageClassesScreen> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PMMOClasses.MOD_ID, "sb_toggle_manage_screen"));

    public static final StreamCodec<FriendlyByteBuf, SB_ToggleManageClassesScreen> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static void handle(SB_ToggleManageClassesScreen ignoredPacket, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if(ctx.player() instanceof ServerPlayer player) {
                    PacketDistributor.sendToPlayer(player, new CB_OpenManageClassesScreen());
            }
        });
    }
    @Override
    public @NotNull Type<SB_ToggleManageClassesScreen> type() { return TYPE; }
}
