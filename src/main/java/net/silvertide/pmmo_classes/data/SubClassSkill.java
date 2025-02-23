package net.silvertide.pmmo_classes.data;

public enum SubClassSkill implements IClassSkill {
    ALCHEMIST(PrimaryClassSkill.ARTIFICER),
    ARMORER(PrimaryClassSkill.ARTIFICER),
    ARTILLERIST(PrimaryClassSkill.ARTIFICER),
    PATH_OF_THE_BERSERKER(PrimaryClassSkill.BARBARIAN),
    PATH_OF_THE_STORM_HERALD(PrimaryClassSkill.BARBARIAN),
    PATH_OF_THE_BEAST(PrimaryClassSkill.BARBARIAN),
    COLLEGE_OF_SWORDS(PrimaryClassSkill.BARD),
    COLLEGE_OF_WHISPERS(PrimaryClassSkill.BARD),
    COLLEGE_OF_SPIRITS(PrimaryClassSkill.BARD),
    DEATH_DOMAIN(PrimaryClassSkill.CLERIC),
    LIFE_DOMAIN(PrimaryClassSkill.CLERIC),
    WAR_DOMAIN(PrimaryClassSkill.CLERIC),
    CIRCLE_OF_THE_SHEPHERD(PrimaryClassSkill.DRUID),
    CIRCLE_OF_SPORES(PrimaryClassSkill.DRUID),
    CIRCLE_OF_WILDFIRE(PrimaryClassSkill.DRUID),
    CHAMPION(PrimaryClassSkill.FIGHTER),
    ELDRITCH_KNIGHT(PrimaryClassSkill.FIGHTER),
    SAMURAI(PrimaryClassSkill.FIGHTER),
    WAY_OF_THE_KENSEI(PrimaryClassSkill.MONK),
    WAY_OF_SHADOW(PrimaryClassSkill.MONK),
    WAY_OF_THE_SUN_SOUL(PrimaryClassSkill.MONK),
    OATHBREAKER(PrimaryClassSkill.PALADIN),
    OATH_OF_VENGEANCE(PrimaryClassSkill.PALADIN),
    OATH_OF_REDEMPTION(PrimaryClassSkill.PALADIN),
    GLOOM_STALKER(PrimaryClassSkill.RANGER),
    HUNTER(PrimaryClassSkill.RANGER),
    BEAST_MASTER(PrimaryClassSkill.RANGER),
    ASSASSIN(PrimaryClassSkill.ROGUE),
    SCOUT(PrimaryClassSkill.ROGUE),
    SOULKNIFE(PrimaryClassSkill.ROGUE),
    DIVINE_SOUL(PrimaryClassSkill.SORCERER),
    SHADOW_MAGIC(PrimaryClassSkill.SORCERER),
    STORM_SORCERY(PrimaryClassSkill.SORCERER),
    HEXBLADE(PrimaryClassSkill.WARLOCK),
    ARCHFEY(PrimaryClassSkill.WARLOCK),
    FATHOMLESS(PrimaryClassSkill.WARLOCK),
    SCHOOL_OF_EVOCATION(PrimaryClassSkill.WIZARD),
    SCHOOL_OF_NECROMANCY(PrimaryClassSkill.WIZARD),
    GRAVITURGY_MAGIC(PrimaryClassSkill.WIZARD);

    private final PrimaryClassSkill parentClass;

    SubClassSkill(PrimaryClassSkill parentClass) {
        this.parentClass = parentClass;
    }

    public PrimaryClassSkill getParentClass() {
        return parentClass;
    }

    @Override
    public ClassType getClassType() {
        return ClassType.SUB;
    }
}