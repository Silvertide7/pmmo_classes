package net.silvertide.pmmo_classes.data;

import net.silvertide.pmmo_classes.utils.ClassUtil;

public enum PrimaryClassSkill implements IClassSkill {
    ARTIFICER(ClassGroup.EXPERT,0,0),
    BARBARIAN(ClassGroup.WARRIOR,0,22),
    BARD(ClassGroup.EXPERT,0,44),
    CLERIC(ClassGroup.PRIEST,0,66),
    DRUID(ClassGroup.PRIEST,0,88),
    FIGHTER(ClassGroup.WARRIOR,0,110),
    MONK(ClassGroup.WARRIOR,0,132),
    PALADIN(ClassGroup.PRIEST,0,154),
    RANGER(ClassGroup.EXPERT,0,176),
    ROGUE(ClassGroup.EXPERT,0,198),
    SORCERER(ClassGroup.MAGE,0,220),
    WARLOCK(ClassGroup.MAGE,88,0),
    WIZARD(ClassGroup.MAGE,88,22);

    private final ClassGroup group;
    private final int xOffset;
    private final int yOffset;

    PrimaryClassSkill(ClassGroup group, int xOffset, int yOffset) {
        this.group = group;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public ClassGroup getGroup() { return group; }

    public int getXOffset() { return this.xOffset; }

    public int getYOffset() { return this.yOffset; }

    @Override
    public String getSkillName() {
        return ClassUtil.getSkillString(this);
    }

    @Override
    public ClassType getClassType() { return ClassType.PRIMARY; }
}