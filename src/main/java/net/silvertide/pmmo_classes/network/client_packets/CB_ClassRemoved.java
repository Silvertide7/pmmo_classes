package net.silvertide.pmmo_classes.network.client_packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.gui.ManageClassesScreen;
import org.jetbrains.annotations.NotNull;

public record CB_ClassRemoved() implements CustomPacketPayload {
    public static final CB_ClassRemoved INSTANCE = new CB_ClassRemoved();
    public static final Type<CB_ClassRemoved> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PMMOClasses.MOD_ID, "cb_class_removed"));
    public static final StreamCodec<FriendlyByteBuf, CB_ClassRemoved> STREAM_CODEC =  StreamCodec.unit(INSTANCE);

    public static void handle(CB_ClassRemoved packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if(Minecraft.getInstance().screen instanceof ManageClassesScreen manageClassesScreen) {
                manageClassesScreen.createClassCards();
            }
        });
    }

    @Override
    public @NotNull Type<CB_ClassRemoved> type() { return TYPE; }
}
