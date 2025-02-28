package net.silvertide.pmmo_classes.utils;

import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_classes.items.components.InsigniaData;
import net.silvertide.pmmo_classes.registry.DataComponentRegistry;

import java.util.List;
import java.util.Optional;

public final class DataComponentUtil {
    private DataComponentUtil() {}

    public static Optional<InsigniaData> getInsigniaData(ItemStack stack) {
        return Optional.ofNullable(stack.get(DataComponentRegistry.INSIGNIA_DATA));
    }

    public static void setInsigniaData(ItemStack stack, InsigniaData
            attunementData) {
        stack.set(DataComponentRegistry.INSIGNIA_DATA, attunementData);
    }

    public static void addInsigniaData(ItemStack stack, String name, List<String> skills, String applicationType, Long value, int experienceCost, String bookColor, String trimColor) {
        InsigniaData data = new InsigniaData(name, skills, applicationType, value, experienceCost, bookColor, trimColor);
        setInsigniaData(stack, data);
    }
}