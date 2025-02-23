package net.silvertide.pmmo_classes.data;

public enum PrimaryClassSkill implements IClassSkill {
    ARTIFICER(ClassGroup.EXPERT,160,26),
    BARBARIAN(ClassGroup.WARRIOR,160,48),
    BARD(ClassGroup.EXPERT,160,70),
    CLERIC(ClassGroup.PRIEST,160,92),
    DRUID(ClassGroup.PRIEST,160,114),
    FIGHTER(ClassGroup.WARRIOR,160,136),
    MONK(ClassGroup.WARRIOR,160,158),
    PALADIN(ClassGroup.PRIEST,88,180),
    RANGER(ClassGroup.EXPERT,160,202),
    ROGUE(ClassGroup.EXPERT,160,224),
    SORCERER(ClassGroup.MAGE,72,180),
    WARLOCK(ClassGroup.MAGE,72,202),
    WIZARD(ClassGroup.MAGE,72,224);

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
    public ClassType getClassType() { return ClassType.PRIMARY; }
}