package net.silvertide.pmmo_classes.network.client_packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.gui.ManageClassesScreen;
import org.jetbrains.annotations.NotNull;

public record CB_OpenManageClassesScreen() implements CustomPacketPayload {
    public static final CB_OpenManageClassesScreen INSTANCE = new CB_OpenManageClassesScreen();
    public static final Type<CB_OpenManageClassesScreen> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PMMOClasses.MOD_ID, "cb_open_manage_screen"));
    public static final StreamCodec<FriendlyByteBuf, CB_OpenManageClassesScreen> STREAM_CODEC =  StreamCodec.unit(INSTANCE);

    public static void handle(CB_OpenManageClassesScreen packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> Minecraft.getInstance().setScreen(new ManageClassesScreen()));
    }

    @Override
    public @NotNull Type<CB_OpenManageClassesScreen> type() { return TYPE; }
}
