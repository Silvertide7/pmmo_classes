package net.silvertide.pmmo_classes.utils;

import net.silvertide.pmmo_classes.data.AscendedClassSkill;
import net.silvertide.pmmo_classes.data.ClassGroup;
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

    public static List<String> getSubClassSkills(PrimaryClassSkill primaryClassSkill) {
        return Arrays.stream(SubClassSkill.values())
            .filter(subClassSkill -> subClassSkill.getParentClass().equals(primaryClassSkill))
            .map(SubClassSkill::getSkillName)
            .toList();
    }

    public static List<String> getPrimaryClassSkills(ClassGroup classGroup) {
        return Arrays.stream(PrimaryClassSkill.values())
                .filter(primaryClassSkill -> primaryClassSkill.getGroup().equals(classGroup))
                .map(PrimaryClassSkill::getSkillName)
                .toList();
    }
}