package net.silvertide.pmmo_classes.data;

import net.minecraft.network.chat.Component;

public record AddClassSkillResult(boolean success, Component messageComponent) {}
