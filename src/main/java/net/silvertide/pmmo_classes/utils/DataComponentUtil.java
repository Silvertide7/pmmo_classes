package net.silvertide.pmmo_classes.utils;

import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_classes.items.components.InsigniaData;
import net.silvertide.pmmo_classes.registry.DataComponentRegistry;

import java.util.Optional;

public final class DataComponentUtil {
    private DataComponentUtil() {}

//    public static Optional<InsigniaData> getSkillBookData(ItemStack stack) {
//        return Optional.ofNullable(stack.get(DataComponentRegistry.SKILL_BOOK_DATA));
//    }
//
//    public static void setSkillBookData(ItemStack stack, InsigniaData
//            attunementData) {
//        stack.set(DataComponentRegistry.SKILL_BOOK_DATA, attunementData);
//    }
//
//    public static void addSkillBookData(ItemStack stack, String skill, String applicationType, Long value, String bookColor, String trimColor) {
//        InsigniaData data = new InsigniaData(skill, applicationType, value, bookColor, trimColor);
//        setSkillBookData(stack, data);
//    }
}