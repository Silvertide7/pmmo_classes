package net.silvertide.pmmo_classes.data;

public enum PrimaryClassSkill implements IClassSkill {
    BARBARIAN(ClassGroup.WARRIOR,0,100),
    FIGHTER(ClassGroup.WARRIOR,0,188),
    MONK(ClassGroup.WARRIOR,0,210),
    PALADIN(ClassGroup.PRIEST,88,78),
    CLERIC(ClassGroup.PRIEST,0,144),
    DRUID(ClassGroup.PRIEST,0,166),
    SORCERER(ClassGroup.MAGE,88,144),
    WIZARD(ClassGroup.MAGE,88,188),
    WARLOCK(ClassGroup.MAGE,88,166),
    BARD(ClassGroup.EXPERT,0,122),
    RANGER(ClassGroup.EXPERT,88,100),
    ROGUE(ClassGroup.EXPERT,88,122),
    ARTIFICER(ClassGroup.EXPERT,0,78);

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