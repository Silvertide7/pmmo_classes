package net.silvertide.pmmo_classes;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.silvertide.pmmo_classes.client.keybindings.Keybindings;
import net.silvertide.pmmo_classes.config.Config;
import net.silvertide.pmmo_classes.registry.ItemRegistry;
import net.silvertide.pmmo_classes.registry.TabRegistry;
import net.silvertide.pmmo_skill_books.registry.ItemPropertyRegistry;
import org.slf4j.Logger;

@Mod(PMMOClasses.MOD_ID)
public class PMMOClasses
{
    public static final String MOD_ID = "pmmo_classes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PMMOClasses() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.register(modEventBus);
        TabRegistry.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD, value= Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent clientSetupEvent) {
            clientSetupEvent.enqueueWork(() -> ItemProperties.register(ItemRegistry.CLASS_GRANT.get(), ItemPropertyRegistry.SKILL_GRANT_PROPERTY, (stack, level, entity, seed) -> ItemPropertyRegistry.getSkillGrantConfiguration(stack)));
        }

        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent keyMappingsEvent) {
            keyMappingsEvent.register(Keybindings.INSTANCE.useOpenManageClassesKey);
        }
    }

    public static ResourceLocation id(String location) {
        return new ResourceLocation(MOD_ID, location);
    }
}
