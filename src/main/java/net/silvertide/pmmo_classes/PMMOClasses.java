package net.silvertide.pmmo_classes;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.silvertide.pmmo_classes.registry.DataComponentRegistry;
import net.silvertide.pmmo_classes.registry.ItemPropertyRegistry;
import net.silvertide.pmmo_classes.registry.ItemRegistry;
import net.silvertide.pmmo_classes.registry.TabRegistry;
import org.slf4j.Logger;

@Mod(PMMOClasses.MOD_ID)
public class PMMOClasses
{
    public static final String MOD_ID = "pmmo_classes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PMMOClasses(IEventBus modEventBus, ModContainer modContainer)
    {
//        DataComponentRegistry.register(modEventBus);
//        ItemRegistry.register(modEventBus);
//        TabRegistry.register(modEventBus);
    }

    public static ResourceLocation id(String location) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, location);
    }
}
