package net.silvertide.pmmo_classes.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.data.PrimaryClassSkill;
import net.silvertide.pmmo_classes.utils.ClassUtil;
import net.silvertide.pmmo_classes.utils.DataComponentUtil;

import java.util.List;

public class TabRegistry {


    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PMMOClasses.MOD_ID);

    public static void register(IEventBus eventBus) { CREATIVE_MODE_TABS.register(eventBus); }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register("pmmo_class_tab",
            () -> CreativeModeTab.builder()
                    .icon(TabRegistry::getIcon)
                    .title(Component.translatable("creative_tab.pmmo_classes.classes"))
                    .displayItems((displayParameters, output) -> {

                        // Setup Class Group Items
                        List<String> warriorClassSkills = ClassUtil.getWarriorClassSkills();
                        addInsignia(output, "Warrior's", warriorClassSkills, 1L, -1,"red", "iron");
                        addInsignia(output, "Warrior's", warriorClassSkills, 2L, -1,"red", "gold");
                        addInsignia(output, "Warrior's", warriorClassSkills, 3L, -1,"red", "emerald");
                        addInsignia(output, "Warrior's", warriorClassSkills, 4L, -1,"red", "diamond");

                        List<String> priestClassSkills = ClassUtil.getPriestClassSkills();
                        addInsignia(output, "Priest's", priestClassSkills, 1L,  -1,"white", "iron");
                        addInsignia(output, "Priest's", priestClassSkills, 2L, -1,"white", "gold");
                        addInsignia(output, "Priest's", priestClassSkills, 3L, -1,"white", "emerald");
                        addInsignia(output, "Priest's", priestClassSkills, 4L, -1,"white", "diamond");

                        List<String> mageClassSkills = ClassUtil.getMageClassSkills();
                        addInsignia(output, "Iron Mage's", mageClassSkills, 1L, -1,"purple", "iron");
                        addInsignia(output, "Gold Mage's", mageClassSkills, 2L, -1,"purple", "gold");
                        addInsignia(output, "Emerald Mage's", mageClassSkills, 3L, -1,"purple", "emerald");
                        addInsignia(output, "Diamond Mage's", mageClassSkills, 4L, -1,"purple", "diamond");

                        List<String> expertClassSkills = ClassUtil.getExpertClassSkills();
                        addInsignia(output, "Iron Expert's", expertClassSkills, 1L, -1,"teal", "iron");
                        addInsignia(output, "Gold Expert's", expertClassSkills, 2L, -1,"teal", "gold");
                        addInsignia(output, "Emerald Expert's", expertClassSkills, 3L, -1,"teal", "emerald");
                        addInsignia(output, "Diamond Expert's", expertClassSkills, 4L, -1,"teal", "diamond");

                        // Add SubClasses
                        addInsignia(output, "Barbarian's", ClassUtil.getSubClassSkills(PrimaryClassSkill.BARBARIAN), 1L, -1, "red", "plain");

                    })
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .build());

    private static ItemStack getIcon(){
        return new ItemStack(ItemRegistry.INSIGNIA.get());
    }

    private static void addInsignia(CreativeModeTab.Output output, String name, List<String> skills, Long value, int experienceCost, String color, String rank) {
        ItemStack skillBook = new ItemStack(ItemRegistry.INSIGNIA.get());
        DataComponentUtil.addInsigniaData(skillBook, name, skills, "set", value, experienceCost, color, rank);
        output.accept(skillBook);
    }
}
