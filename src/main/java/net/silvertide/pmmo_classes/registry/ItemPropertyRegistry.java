package net.silvertide.pmmo_classes.registry;

import net.minecraft.client.renderer.item.ItemProperties;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.utils.DataComponentUtil;

public class ItemPropertyRegistry {
    public static void addCustomItemProperties() {
        ItemProperties.register(ItemRegistry.SKILL_BOOK.get(), PMMOClasses.id("configuration"),
                (stack, level, entity, seed) -> DataComponentUtil.getSkillBookData(stack).map(skillBookData -> {
                    float configuration = 1f;

                    switch(skillBookData.getColor()) {
                        case GREEN -> configuration += 10;
                        case PURPLE -> configuration += 20;
                        case BLACK -> configuration += 30;
                    }

                    switch(skillBookData.getTrim()) {
                        case GOLD -> configuration += 1;
                        case EMERALD -> configuration += 2;
                        case DIAMOND -> configuration += 3;
                    }
                    return configuration;
                }).orElse(1f));
    }
}
