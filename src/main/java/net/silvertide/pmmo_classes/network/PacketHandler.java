package net.silvertide.pmmo_classes.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.network.client_packets.CB_ClassRemoved;
import net.silvertide.pmmo_classes.network.server_packets.SB_RemoveClassSkill;

public class PacketHandler {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }
    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(PMMOClasses.MOD_ID, "main"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // CLIENT BOUND
        net.messageBuilder(CB_ClassRemoved.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CB_ClassRemoved::new)
                .encoder(CB_ClassRemoved::encode)
                .consumerMainThread(CB_ClassRemoved::handle)
                .add();

        // SERVER BOUND
        net.messageBuilder(SB_RemoveClassSkill.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SB_RemoveClassSkill::new)
                .encoder(SB_RemoveClassSkill::encode)
                .consumerMainThread(SB_RemoveClassSkill::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToClient(ServerPlayer player, MSG message) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAllPlayers(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

}
