package net.silvertide.pmmo_classes.client.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.client.keybindings.Keybindings;
import net.silvertide.pmmo_classes.gui.ManageClassesScreen;

@Mod.EventBusSubscriber(modid = PMMOClasses.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientForgeEvents {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent clientTickEvent) {
        if(Minecraft.getInstance().player == null) return;
        if(Keybindings.INSTANCE.useOpenManageClassesKey.consumeClick()) {
            Minecraft.getInstance().setScreen(new ManageClassesScreen());
        }
    }
}
