package net.silvertide.pmmo_classes.client.events;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.client.keybindings.Keybindings;
import net.silvertide.pmmo_classes.network.server_packets.SB_ToggleManageClassesScreen;

@EventBusSubscriber(modid = PMMOClasses.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientForgeEvents {
    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Pre clientTickEvent) {
        if(Minecraft.getInstance().player == null) return;
        if(Keybindings.INSTANCE.useOpenManageClassesKey.consumeClick()) {
            PacketDistributor.sendToServer(new SB_ToggleManageClassesScreen());
        }
    }
}
