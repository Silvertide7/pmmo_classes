package net.silvertide.pmmo_classes.data;

public enum PrimaryClassSkill implements IClassSkill {
    BARBARIAN(ClassGroup.WARRIOR),
    FIGHTER(ClassGroup.WARRIOR),
    MONK(ClassGroup.WARRIOR),
    PALADIN(ClassGroup.PRIEST),
    CLERIC(ClassGroup.PRIEST),
    DRUID(ClassGroup.PRIEST),
    SORCERER(ClassGroup.MAGE),
    WIZARD(ClassGroup.MAGE),
    WARLOCK(ClassGroup.MAGE),
    BARD(ClassGroup.EXPERT),
    RANGER(ClassGroup.EXPERT),
    ROGUE(ClassGroup.EXPERT),
    ARTIFICER(ClassGroup.EXPERT);

    private final ClassGroup group;

    PrimaryClassSkill(ClassGroup group) {
        this.group = group;
    }

    public ClassGroup getGroup() {
        return group;
    }
}