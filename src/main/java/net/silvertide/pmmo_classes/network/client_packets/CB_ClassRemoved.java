package net.silvertide.pmmo_classes.network.client_packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.silvertide.pmmo_classes.gui.ManageClassesScreen;

import java.util.function.Supplier;

public class CB_ClassRemoved {
    public CB_ClassRemoved() {}
    public CB_ClassRemoved(FriendlyByteBuf buf) {}
    public void encode(FriendlyByteBuf buf) {}
    public static void handle(CB_ClassRemoved msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().screen instanceof ManageClassesScreen manageClassesScreen) {
                manageClassesScreen.createClassCards();
            }
        });
        context.setPacketHandled(true);
    }
}
