package net.silvertide.pmmo_classes.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.items.ClassGrantItem;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PMMOClasses.MOD_ID);
    public static void register(IEventBus eventBus){ ITEMS.register(eventBus); }

    public static final RegistryObject<Item> CLASS_GRANT = ITEMS.register("class_grant", ClassGrantItem::new);
}
