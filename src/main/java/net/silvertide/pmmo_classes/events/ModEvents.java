package net.silvertide.pmmo_classes.events;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.network.PacketHandler;


@Mod.EventBusSubscriber(modid = PMMOClasses.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void commonSetupEvent(FMLCommonSetupEvent commonSetupEvent) {
        commonSetupEvent.enqueueWork(PacketHandler::register);
    }
}
