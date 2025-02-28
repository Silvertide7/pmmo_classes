package net.silvertide.pmmo_classes.utils;

import net.silvertide.pmmo_classes.data.AscendedClassSkill;
import net.silvertide.pmmo_classes.data.PrimaryClassSkill;
import net.silvertide.pmmo_classes.data.SubClassSkill;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public final class ClassUtil {
    private ClassUtil() {}

    public static Optional<PrimaryClassSkill> getPrimaryClass(String skill) {
        try {
            return Optional.of(PrimaryClassSkill.valueOf(skill.toUpperCase()));
        } catch(IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static Optional<SubClassSkill> getSubClass(String skill) {
        try {
            return Optional.of(SubClassSkill.valueOf(skill.toUpperCase()));
        } catch(IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static Optional<AscendedClassSkill> getAscendedClass(String skill) {
        try {
            return Optional.of(AscendedClassSkill.valueOf(skill.toUpperCase()));
        } catch(IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static String getSkillString(Enum<?> classSkill) {
        return classSkill.name().toLowerCase();
    }

    public static List<String> getWarriorClassSkills() {
        List<String> warriorSkills = new ArrayList<>();
        warriorSkills.add(PrimaryClassSkill.BARBARIAN.getSkillName());
        warriorSkills.add(PrimaryClassSkill.FIGHTER.getSkillName());
        warriorSkills.add(PrimaryClassSkill.MONK.getSkillName());
        return warriorSkills;
    }

    public static List<String> getPriestClassSkills() {
        List<String> priestSkills = new ArrayList<>();
        priestSkills.add(PrimaryClassSkill.CLERIC.getSkillName());
        priestSkills.add(PrimaryClassSkill.DRUID.getSkillName());
        priestSkills.add(PrimaryClassSkill.PALADIN.getSkillName());
        return priestSkills;
    }

    public static List<String> getExpertClassSkills() {
        List<String> expertSkills = new ArrayList<>();
        expertSkills.add(PrimaryClassSkill.RANGER.getSkillName());
        expertSkills.add(PrimaryClassSkill.ROGUE.getSkillName());
        expertSkills.add(PrimaryClassSkill.BARD.getSkillName());
        expertSkills.add(PrimaryClassSkill.ARTIFICER.getSkillName());
        return expertSkills;
    }

    public static List<String> getMageClassSkills() {
        List<String> mageSkills = new ArrayList<>();
        mageSkills.add(PrimaryClassSkill.SORCERER.getSkillName());
        mageSkills.add(PrimaryClassSkill.WARLOCK.getSkillName());
        mageSkills.add(PrimaryClassSkill.WIZARD.getSkillName());
        return mageSkills;
    }

    public static List<String> getSubClassSkills(PrimaryClassSkill primaryClassSkill) {
        return Arrays.stream(SubClassSkill.values())
            .filter(subClassSkill -> subClassSkill.getParentClass().equals(primaryClassSkill))
            .map(SubClassSkill::getSkillName)
            .toList();
    }
}