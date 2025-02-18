package net.silvertide.pmmo_classes.client.events;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.client.keybindings.Keybindings;

@EventBusSubscriber(modid = PMMOClasses.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent keyMappingsEvent) {
        keyMappingsEvent.register(Keybindings.INSTANCE.useOpenManageClassesKey);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent clientSetupEvent) {
//            ItemPropertyRegistry.addCustomItemProperties();
    }

}

