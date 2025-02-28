package net.silvertide.pmmo_classes.registry;

import net.minecraft.client.renderer.item.ItemProperties;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.data.InsigniaColor;
import net.silvertide.pmmo_classes.utils.DataComponentUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemPropertyRegistry {
    private static final List<InsigniaColor> RANKABLE_COLORS = Arrays.asList(InsigniaColor.RED, InsigniaColor.TEAL, InsigniaColor.PURPLE, InsigniaColor.WHITE);

    public static void addCustomItemProperties() {
        ItemProperties.register(ItemRegistry.INSIGNIA.get(), PMMOClasses.id("configuration"),
                (stack, level, entity, seed) -> DataComponentUtil.getInsigniaData(stack).map(insigniaData -> {
                    float configuration = 1f;

                    // Default is RED
                    switch(insigniaData.getColor()) {
                        case TEAL -> configuration += 1f;
                        case PURPLE -> configuration += 2f;
                        case WHITE -> configuration += 3f;
                    }

                    // Default is PLAIN
                    if(RANKABLE_COLORS.contains(insigniaData.getColor())){
                        switch(insigniaData.getRank()) {
                            case IRON -> configuration += 20f;
                            case GOLD -> configuration += 40f;
                            case EMERALD -> configuration += 60f;
                            case DIAMOND -> configuration += 80f;
                        }
                    }

                    return configuration;
                }).orElse(1f));
    }
}
