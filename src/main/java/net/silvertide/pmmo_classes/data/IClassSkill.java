package net.silvertide.pmmo_classes.data;

import net.minecraft.network.chat.Component;

public interface IClassSkill {
    String getSkillName();
    ClassType getClassType();

    default String getTranslatedSkillName() {
        return Component.translatable("pmmo." + getSkillName()).getString();
    }
}
