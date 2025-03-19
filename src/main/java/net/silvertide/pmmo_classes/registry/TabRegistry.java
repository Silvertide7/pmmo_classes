package net.silvertide.pmmo_classes.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.data.ClassGroup;
import net.silvertide.pmmo_classes.data.PrimaryClassSkill;
import net.silvertide.pmmo_classes.utils.ClassUtil;
import net.silvertide.pmmo_skill_books.items.components.SkillGrantData;
import net.silvertide.pmmo_skill_books.utils.SkillGrantUtil;

import java.util.List;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PMMOClasses.MOD_ID);

    public static void register(IEventBus eventBus) { CREATIVE_MODE_TABS.register(eventBus); }

    public static final RegistryObject<CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register("pmmo_class_tab",
            () -> CreativeModeTab.builder()
                    .icon(TabRegistry::getIcon)
                    .title(Component.translatable("creative_tab.pmmo_classes.classes"))
                    .displayItems((displayParameters, output) -> {
                        // Setup Class Group Items
                        List<String> warriorClassSkills = ClassUtil.getPrimaryClassSkills(ClassGroup.WARRIOR);
                        addClassGrantItem(output, "pmmo_classes.insignia.iron.warrior", warriorClassSkills, "set",1L, 20, "iron","red");
                        addClassGrantItem(output, "pmmo_classes.insignia.gold.warrior", warriorClassSkills, "set",2L, 25, "gold","red");
                        addClassGrantItem(output, "pmmo_classes.insignia.emerald.warrior", warriorClassSkills, "set",3L, 30, "emerald","red");
                        addClassGrantItem(output, "pmmo_classes.insignia.diamond.warrior", warriorClassSkills, "set",4L, 40, "diamond","red");

                        List<String> priestClassSkills = ClassUtil.getPrimaryClassSkills(ClassGroup.PRIEST);
                        addClassGrantItem(output, "pmmo_classes.insignia.iron.priest", priestClassSkills, "set",1L,  20, "iron", "white");
                        addClassGrantItem(output, "pmmo_classes.insignia.gold.priest", priestClassSkills, "set",2L, 25, "gold", "white");
                        addClassGrantItem(output, "pmmo_classes.insignia.emerald.priest", priestClassSkills, "set",3L, 30, "emerald", "white");
                        addClassGrantItem(output, "pmmo_classes.insignia.diamond.priest", priestClassSkills, "set",4L, 40, "diamond", "white");

                        List<String> mageClassSkills = ClassUtil.getPrimaryClassSkills(ClassGroup.MAGE);
                        addClassGrantItem(output, "pmmo_classes.insignia.iron.mage", mageClassSkills, "set",1L, 20, "iron", "purple");
                        addClassGrantItem(output, "pmmo_classes.insignia.gold.mage", mageClassSkills, "set",2L, 25, "gold", "purple");
                        addClassGrantItem(output, "pmmo_classes.insignia.emerald.mage", mageClassSkills, "set",3L, 30, "emerald", "purple");
                        addClassGrantItem(output, "pmmo_classes.insignia.diamond.mage", mageClassSkills, "set",4L, 40, "diamond", "purple");

                        List<String> expertClassSkills = ClassUtil.getPrimaryClassSkills(ClassGroup.EXPERT);
                        addClassGrantItem(output, "pmmo_classes.insignia.iron.expert", expertClassSkills, "set",1L, 20, "iron", "teal");
                        addClassGrantItem(output, "pmmo_classes.insignia.gold.expert", expertClassSkills, "set",2L, 25, "gold", "teal");
                        addClassGrantItem(output, "pmmo_classes.insignia.emerald.expert", expertClassSkills, "set",3L, 30, "emerald", "teal");
                        addClassGrantItem(output, "pmmo_classes.insignia.diamond.expert", expertClassSkills, "set",4L, 40, "diamond", "teal");

                        // Add SubClasses
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.artificer", ClassUtil.getSubClassSkills(PrimaryClassSkill.ARTIFICER), "set", 1L, 30, "plain", "teal");
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.barbarian", ClassUtil.getSubClassSkills(PrimaryClassSkill.BARBARIAN), "set",1L, 30, "plain", "orange");
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.bard", ClassUtil.getSubClassSkills(PrimaryClassSkill.BARD), "set",1L, 30, "plain", "light_purple");
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.cleric", ClassUtil.getSubClassSkills(PrimaryClassSkill.CLERIC), "set",1L, 30, "plain", "white");
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.druid", ClassUtil.getSubClassSkills(PrimaryClassSkill.DRUID), "set",1L, 30, "plain", "green");
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.fighter", ClassUtil.getSubClassSkills(PrimaryClassSkill.FIGHTER), "set",1L, 30, "plain", "red");
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.monk", ClassUtil.getSubClassSkills(PrimaryClassSkill.MONK), "set",1L, 30, "plain", "blue");
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.paladin", ClassUtil.getSubClassSkills(PrimaryClassSkill.PALADIN), "set",1L, 30, "plain", "yellow");
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.ranger", ClassUtil.getSubClassSkills(PrimaryClassSkill.RANGER), "set",1L, 30, "plain", "light_green");
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.rogue", ClassUtil.getSubClassSkills(PrimaryClassSkill.ROGUE), "set",1L, 30, "plain", "black");
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.sorcerer", ClassUtil.getSubClassSkills(PrimaryClassSkill.SORCERER), "set",1L, 30, "plain", "pink");
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.warlock", ClassUtil.getSubClassSkills(PrimaryClassSkill.WARLOCK), "set",1L, 30, "plain", "purple");
                        addClassGrantItem(output, "pmmo_classes.insignia.plain.wizard", ClassUtil.getSubClassSkills(PrimaryClassSkill.WIZARD), "set",1L, 30, "plain", "light_blue");
                    })
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .build());

    private static ItemStack getIcon() {
        ItemStack skillGrant = new ItemStack(ItemRegistry.CLASS_GRANT.get());
        SkillGrantData skillGrantData = new SkillGrantData("", List.of(), "level", 1L,  0, "insignia", "gold", "red");
        SkillGrantUtil.putSkillGrantData(skillGrant, skillGrantData);
        return skillGrant;
    }

    private static void addClassGrantItem(CreativeModeTab.Output output, String name, List<String> skills, String applicationType, Long value, int experienceCost, String rank, String color) {
        ItemStack classGrant = new ItemStack(ItemRegistry.CLASS_GRANT.get());
        SkillGrantData skillGrantData = new SkillGrantData(name, skills, applicationType, value,  experienceCost, "insignia", rank, color);
        SkillGrantUtil.putSkillGrantData(classGrant, skillGrantData);
        output.accept(classGrant);
    }
}
