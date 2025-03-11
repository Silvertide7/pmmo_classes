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
import net.silvertide.pmmo_skill_books.registry.ItemRegistry;
import net.silvertide.pmmo_skill_books.utils.DataComponentUtil;

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
                        addSkillGrantItem(output, "pmmo_classes.insignia.iron.warrior", warriorClassSkills, "set",1L, 20, "iron","red");
                        addSkillGrantItem(output, "pmmo_classes.insignia.gold.warrior", warriorClassSkills, "set",2L, 25, "gold","red");
                        addSkillGrantItem(output, "pmmo_classes.insignia.emerald.warrior", warriorClassSkills, "set",3L, 30, "emerald","red");
                        addSkillGrantItem(output, "pmmo_classes.insignia.diamond.warrior", warriorClassSkills, "set",4L, 40, "diamond","red");

                        List<String> priestClassSkills = ClassUtil.getPrimaryClassSkills(ClassGroup.PRIEST);
                        addSkillGrantItem(output, "pmmo_classes.insignia.iron.priest", priestClassSkills, "set",1L,  20, "iron", "white");
                        addSkillGrantItem(output, "pmmo_classes.insignia.gold.priest", priestClassSkills, "set",2L, 25, "gold", "white");
                        addSkillGrantItem(output, "pmmo_classes.insignia.emerald.priest", priestClassSkills, "set",3L, 30, "emerald", "white");
                        addSkillGrantItem(output, "pmmo_classes.insignia.diamond.priest", priestClassSkills, "set",4L, 40, "diamond", "white");

                        List<String> mageClassSkills = ClassUtil.getPrimaryClassSkills(ClassGroup.MAGE);
                        addSkillGrantItem(output, "pmmo_classes.insignia.iron.mage", mageClassSkills, "set",1L, 20, "iron", "purple");
                        addSkillGrantItem(output, "pmmo_classes.insignia.gold.mage", mageClassSkills, "set",2L, 25, "gold", "purple");
                        addSkillGrantItem(output, "pmmo_classes.insignia.emerald.mage", mageClassSkills, "set",3L, 30, "emerald", "purple");
                        addSkillGrantItem(output, "pmmo_classes.insignia.diamond.mage", mageClassSkills, "set",4L, 40, "diamond", "purple");

                        List<String> expertClassSkills = ClassUtil.getPrimaryClassSkills(ClassGroup.EXPERT);
                        addSkillGrantItem(output, "pmmo_classes.insignia.iron.expert", expertClassSkills, "set",1L, 20, "iron", "teal");
                        addSkillGrantItem(output, "pmmo_classes.insignia.gold.expert", expertClassSkills, "set",2L, 25, "gold", "teal");
                        addSkillGrantItem(output, "pmmo_classes.insignia.emerald.expert", expertClassSkills, "set",3L, 30, "emerald", "teal");
                        addSkillGrantItem(output, "pmmo_classes.insignia.diamond.expert", expertClassSkills, "set",4L, 40, "diamond", "teal");

                        // Add SubClasses
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.artificer", ClassUtil.getSubClassSkills(PrimaryClassSkill.ARTIFICER), "set", 1L, 30, "plain", "teal");
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.barbarian", ClassUtil.getSubClassSkills(PrimaryClassSkill.BARBARIAN), "set",1L, 30, "plain", "orange");
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.bard", ClassUtil.getSubClassSkills(PrimaryClassSkill.BARD), "set",1L, 30, "plain", "light_purple");
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.cleric", ClassUtil.getSubClassSkills(PrimaryClassSkill.CLERIC), "set",1L, 30, "plain", "white");
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.druid", ClassUtil.getSubClassSkills(PrimaryClassSkill.DRUID), "set",1L, 30, "plain", "green");
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.fighter", ClassUtil.getSubClassSkills(PrimaryClassSkill.FIGHTER), "set",1L, 30, "plain", "red");
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.monk", ClassUtil.getSubClassSkills(PrimaryClassSkill.MONK), "set",1L, 30, "plain", "blue");
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.paladin", ClassUtil.getSubClassSkills(PrimaryClassSkill.PALADIN), "set",1L, 30, "plain", "yellow");
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.ranger", ClassUtil.getSubClassSkills(PrimaryClassSkill.RANGER), "set",1L, 30, "plain", "light_green");
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.rogue", ClassUtil.getSubClassSkills(PrimaryClassSkill.ROGUE), "set",1L, 30, "plain", "black");
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.sorcerer", ClassUtil.getSubClassSkills(PrimaryClassSkill.SORCERER), "set",1L, 30, "plain", "pink");
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.warlock", ClassUtil.getSubClassSkills(PrimaryClassSkill.WARLOCK), "set",1L, 30, "plain", "purple");
                        addSkillGrantItem(output, "pmmo_classes.insignia.plain.wizard", ClassUtil.getSubClassSkills(PrimaryClassSkill.WIZARD), "set",1L, 30, "plain", "light_blue");

                    })
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .build());

    private static ItemStack getIcon() {
        ItemStack skillGrant = new ItemStack(ItemRegistry.SKILL_GRANT.get());
        DataComponentUtil.addSkillGrantData(skillGrant, "", List.of(), "level", 1L,  0, "insignia", "gold", "red");
        return skillGrant;
    }

    private static void addSkillGrantItem(CreativeModeTab.Output output, String name, List<String> skills, String applicationType, Long value, int experienceCost, String rank, String color) {
        ItemStack skillGrant = new ItemStack(ItemRegistry.SKILL_GRANT.get());
        DataComponentUtil.addSkillGrantData(skillGrant, name, skills, applicationType, value,  experienceCost, "insignia", rank, color);
        output.accept(skillGrant);
    }
}
