package net.silvertide.pmmo_classes;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.silvertide.pmmo_classes.config.ServerConfigs;
import net.silvertide.pmmo_classes.registry.ItemRegistry;
import net.silvertide.pmmo_classes.registry.TabRegistry;
import net.silvertide.pmmo_skill_books.registry.ItemPropertyRegistry;
import org.slf4j.Logger;

@Mod(PMMOClasses.MOD_ID)
public class PMMOClasses
{
    public static final String MOD_ID = "pmmo_classes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PMMOClasses(IEventBus modEventBus, ModContainer modContainer)
    {
        ItemRegistry.register(modEventBus);
        TabRegistry.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfigs.SPEC, String.format("%s-server.toml", MOD_ID));
    }

    @EventBusSubscriber(modid = MOD_ID, bus= EventBusSubscriber.Bus.MOD, value=Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent clientSetupEvent) {
            clientSetupEvent.enqueueWork(() -> ItemProperties.register(ItemRegistry.CLASS_GRANT.get(), ItemPropertyRegistry.SKILL_GRANT_PROPERTY, (stack, level, entity, seed) -> ItemPropertyRegistry.getSkillGrantConfiguration(stack)));
        }
    }


    public static ResourceLocation id(String location) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, location);
    }
}
