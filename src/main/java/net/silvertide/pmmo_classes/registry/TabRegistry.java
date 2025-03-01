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
import net.silvertide.pmmo_classes.data.ClassGroup;
import net.silvertide.pmmo_classes.data.PrimaryClassSkill;
import net.silvertide.pmmo_classes.utils.ClassUtil;

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
                        List<String> warriorClassSkills = ClassUtil.getPrimaryClassSkills(ClassGroup.WARRIOR);
                        addInsignia(output, "pmmo_classes.insignia.iron.warrior", warriorClassSkills, 1L, -1,"red", "iron");
                        addInsignia(output, "pmmo_classes.insignia.gold.warrior", warriorClassSkills, 2L, -1,"red", "gold");
                        addInsignia(output, "pmmo_classes.insignia.emerald.warrior", warriorClassSkills, 3L, -1,"red", "emerald");
                        addInsignia(output, "pmmo_classes.insignia.diamond.warrior", warriorClassSkills, 4L, -1,"red", "diamond");

                        List<String> priestClassSkills = ClassUtil.getPrimaryClassSkills(ClassGroup.PRIEST);
                        addInsignia(output, "pmmo_classes.insignia.iron.priest", priestClassSkills, 1L,  -1,"white", "iron");
                        addInsignia(output, "pmmo_classes.insignia.gold.priest", priestClassSkills, 2L, -1,"white", "gold");
                        addInsignia(output, "pmmo_classes.insignia.emerald.priest", priestClassSkills, 3L, -1,"white", "emerald");
                        addInsignia(output, "pmmo_classes.insignia.diamond.priest", priestClassSkills, 4L, -1,"white", "diamond");

                        List<String> mageClassSkills = ClassUtil.getPrimaryClassSkills(ClassGroup.MAGE);
                        addInsignia(output, "pmmo_classes.insignia.iron.mage", mageClassSkills, 1L, -1,"purple", "iron");
                        addInsignia(output, "pmmo_classes.insignia.gold.mage", mageClassSkills, 2L, -1,"purple", "gold");
                        addInsignia(output, "pmmo_classes.insignia.emerald.mage", mageClassSkills, 3L, -1,"purple", "emerald");
                        addInsignia(output, "pmmo_classes.insignia.diamond.mage", mageClassSkills, 4L, -1,"purple", "diamond");

                        List<String> expertClassSkills = ClassUtil.getPrimaryClassSkills(ClassGroup.EXPERT);
                        addInsignia(output, "pmmo_classes.insignia.iron.expert", expertClassSkills, 1L, -1,"teal", "iron");
                        addInsignia(output, "pmmo_classes.insignia.gold.expert", expertClassSkills, 2L, -1,"teal", "gold");
                        addInsignia(output, "pmmo_classes.insignia.emerald.expert", expertClassSkills, 3L, -1,"teal", "emerald");
                        addInsignia(output, "pmmo_classes.insignia.diamond.expert", expertClassSkills, 4L, -1,"teal", "diamond");

                        // Add SubClasses
                        addInsignia(output, "pmmo_classes.insignia.plain.artificer", ClassUtil.getSubClassSkills(PrimaryClassSkill.ARTIFICER), 1L, -1, "teal", "plain");
                        addInsignia(output, "pmmo_classes.insignia.plain.barbarian", ClassUtil.getSubClassSkills(PrimaryClassSkill.BARBARIAN), 1L, -1, "orange", "plain");
                        addInsignia(output, "pmmo_classes.insignia.plain.bard", ClassUtil.getSubClassSkills(PrimaryClassSkill.BARD), 1L, -1, "light_purple", "plain");
                        addInsignia(output, "pmmo_classes.insignia.plain.cleric", ClassUtil.getSubClassSkills(PrimaryClassSkill.CLERIC), 1L, -1, "white", "plain");
                        addInsignia(output, "pmmo_classes.insignia.plain.druid", ClassUtil.getSubClassSkills(PrimaryClassSkill.DRUID), 1L, -1, "green", "plain");
                        addInsignia(output, "pmmo_classes.insignia.plain.fighter", ClassUtil.getSubClassSkills(PrimaryClassSkill.FIGHTER), 1L, -1, "red", "plain");
                        addInsignia(output, "pmmo_classes.insignia.plain.monk", ClassUtil.getSubClassSkills(PrimaryClassSkill.MONK), 1L, -1, "blue", "plain");
                        addInsignia(output, "pmmo_classes.insignia.plain.paladin", ClassUtil.getSubClassSkills(PrimaryClassSkill.PALADIN), 1L, -1, "yellow", "plain");
                        addInsignia(output, "pmmo_classes.insignia.plain.ranger", ClassUtil.getSubClassSkills(PrimaryClassSkill.RANGER), 1L, -1, "light_green", "plain");
                        addInsignia(output, "pmmo_classes.insignia.plain.rogue", ClassUtil.getSubClassSkills(PrimaryClassSkill.ROGUE), 1L, -1, "black", "plain");
                        addInsignia(output, "pmmo_classes.insignia.plain.sorcerer", ClassUtil.getSubClassSkills(PrimaryClassSkill.SORCERER), 1L, -1, "pink", "plain");
                        addInsignia(output, "pmmo_classes.insignia.plain.warlock", ClassUtil.getSubClassSkills(PrimaryClassSkill.WARLOCK), 1L, -1, "purple", "plain");
                        addInsignia(output, "pmmo_classes.insignia.plain.wizard", ClassUtil.getSubClassSkills(PrimaryClassSkill.WIZARD), 1L, -1, "light_blue", "plain");

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
