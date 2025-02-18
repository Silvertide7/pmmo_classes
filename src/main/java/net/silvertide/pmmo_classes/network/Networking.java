package net.silvertide.pmmo_classes.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.network.client_packets.CB_OpenManageClassesScreen;
import net.silvertide.pmmo_classes.network.server_packets.SB_RemoveClassSkill;
import net.silvertide.pmmo_classes.network.server_packets.SB_ToggleManageClassesScreen;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = PMMOClasses.MOD_ID)
public class Networking {
    @SubscribeEvent
    public static void registerMessages(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PMMOClasses.MOD_ID);

        registrar
            // CLIENT BOUND PACKETS
            .playToClient(CB_OpenManageClassesScreen.TYPE, CB_OpenManageClassesScreen.STREAM_CODEC, CB_OpenManageClassesScreen::handle)
            // SERVER BOUND PACKETS
            .playToServer(SB_RemoveClassSkill.TYPE, SB_RemoveClassSkill.STREAM_CODEC, SB_RemoveClassSkill::handle)
            .playToServer(SB_ToggleManageClassesScreen.TYPE, SB_ToggleManageClassesScreen.STREAM_CODEC, SB_ToggleManageClassesScreen::handle);
    }
}
