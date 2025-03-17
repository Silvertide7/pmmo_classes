package net.silvertide.pmmo_classes.data;

import net.silvertide.pmmo_classes.utils.ClassUtil;

import java.util.Arrays;
import java.util.Optional;


public enum AscendedClassSkill implements IClassSkill {
    RAGNAROK(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.BARBARIAN),
    SONOMANCER(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.BARD),
    VESTIGE(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.CLERIC),
    WILDWEAVER(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.DRUID),
    TITAN(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.FIGHTER),
    ZENITH(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.MONK),
    LUMINAR(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.PALADIN),
    WAYFORGED(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.RANGER),
    GLOOMSMITH(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.ROGUE),
    EIDOLON(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.SORCERER),
    HEXWRIGHT(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.WARLOCK),
    SAGE(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.WIZARD),

    WARCHANTER(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.BARD),
    LIGHTBORN(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.CLERIC),
    STORMBORN(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.DRUID),
    WARLORD(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.FIGHTER),
    WILDSENT(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.MONK),
    CRUSADER(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.PALADIN),
    BLOODFORGED(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.RANGER),
    REAVER(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.ROGUE),
    SANGUINAR(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.SORCERER),
    CURSEBRINGER(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.WARLOCK),
    RUNEBREAKER(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.WIZARD),

    HYMNCALLER(PrimaryClassSkill.BARD, PrimaryClassSkill.CLERIC),
    WINDSINGER(PrimaryClassSkill.BARD, PrimaryClassSkill.DRUID),
    BLADEDANCER(PrimaryClassSkill.BARD, PrimaryClassSkill.FIGHTER),
    WAYFARER(PrimaryClassSkill.BARD, PrimaryClassSkill.MONK),
    LOREKEEPER(PrimaryClassSkill.BARD, PrimaryClassSkill.PALADIN),
    WANDERER(PrimaryClassSkill.BARD, PrimaryClassSkill.RANGER),
    NIGHTSONG(PrimaryClassSkill.BARD, PrimaryClassSkill.ROGUE),
    SPELLWEAVER(PrimaryClassSkill.BARD, PrimaryClassSkill.SORCERER),
    FATESINGER(PrimaryClassSkill.BARD, PrimaryClassSkill.WARLOCK),
    SPELLSINGER(PrimaryClassSkill.BARD, PrimaryClassSkill.WIZARD),

    HIEROPHANT(PrimaryClassSkill.CLERIC, PrimaryClassSkill.DRUID),
    TEMPLAR(PrimaryClassSkill.CLERIC, PrimaryClassSkill.FIGHTER),
    ASCETIC(PrimaryClassSkill.CLERIC, PrimaryClassSkill.MONK),
    PARAGON(PrimaryClassSkill.CLERIC, PrimaryClassSkill.PALADIN),
    WARDEN(PrimaryClassSkill.CLERIC, PrimaryClassSkill.RANGER),
    INQUISITOR(PrimaryClassSkill.CLERIC, PrimaryClassSkill.ROGUE),
    MYSTIC(PrimaryClassSkill.CLERIC, PrimaryClassSkill.SORCERER),
    OCCULTIST(PrimaryClassSkill.CLERIC, PrimaryClassSkill.WARLOCK),
    DIVINIST(PrimaryClassSkill.CLERIC, PrimaryClassSkill.WIZARD),

    PRIMAL(PrimaryClassSkill.DRUID, PrimaryClassSkill.FIGHTER),
    SHAMAN(PrimaryClassSkill.DRUID, PrimaryClassSkill.MONK),
    WILDOATH(PrimaryClassSkill.DRUID, PrimaryClassSkill.PALADIN),
    STALKER(PrimaryClassSkill.DRUID, PrimaryClassSkill.RANGER),
    SHADE(PrimaryClassSkill.DRUID, PrimaryClassSkill.ROGUE),
    STORMCALLER(PrimaryClassSkill.DRUID, PrimaryClassSkill.SORCERER),
    BLIGHTCASTER(PrimaryClassSkill.DRUID, PrimaryClassSkill.WARLOCK),
    ARCHDRUID(PrimaryClassSkill.DRUID, PrimaryClassSkill.WIZARD),

    IRONGUARD(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.MONK),
    WARBORN(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.PALADIN),
    VANGUARD(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.RANGER),
    DUSKRENDER(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.ROGUE),
    RUNEHARROW(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.SORCERER),
    DREADMARKED(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.WARLOCK),
    WARCALLER(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.WIZARD),

    SERAPH(PrimaryClassSkill.MONK, PrimaryClassSkill.PALADIN),
    TRAVELER(PrimaryClassSkill.MONK, PrimaryClassSkill.RANGER),
    WHISPERER(PrimaryClassSkill.MONK, PrimaryClassSkill.ROGUE),
    ARCANIST(PrimaryClassSkill.MONK, PrimaryClassSkill.SORCERER),
    VOIDFIST(PrimaryClassSkill.MONK, PrimaryClassSkill.WARLOCK),
    RUNIC(PrimaryClassSkill.MONK, PrimaryClassSkill.WIZARD),

    REDEEMER(PrimaryClassSkill.PALADIN, PrimaryClassSkill.RANGER),
    RAVAGER(PrimaryClassSkill.PALADIN, PrimaryClassSkill.ROGUE),
    EMBERBANE(PrimaryClassSkill.PALADIN, PrimaryClassSkill.SORCERER),
    HERALD(PrimaryClassSkill.PALADIN, PrimaryClassSkill.WARLOCK),
    AURAMANCER(PrimaryClassSkill.PALADIN, PrimaryClassSkill.WIZARD),

    NIGHTSTALKER(PrimaryClassSkill.RANGER, PrimaryClassSkill.ROGUE),
    DREADHUNTER(PrimaryClassSkill.RANGER, PrimaryClassSkill.SORCERER),
    FELWEAVER(PrimaryClassSkill.RANGER, PrimaryClassSkill.WARLOCK),
    HEXBANE(PrimaryClassSkill.RANGER, PrimaryClassSkill.WIZARD),

    SHADOWCASTER(PrimaryClassSkill.ROGUE, PrimaryClassSkill.SORCERER),
    FELSTALKER(PrimaryClassSkill.ROGUE, PrimaryClassSkill.WARLOCK),
    ILLUSIONIST(PrimaryClassSkill.ROGUE, PrimaryClassSkill.WIZARD),

    SCION(PrimaryClassSkill.SORCERER, PrimaryClassSkill.WARLOCK),
    ARCHMAGE(PrimaryClassSkill.SORCERER, PrimaryClassSkill.WIZARD),

    DARKSAGE(PrimaryClassSkill.WARLOCK, PrimaryClassSkill.WIZARD);

    private final PrimaryClassSkill firstPrimaryClass;
    private final PrimaryClassSkill secondPrimaryClass;

    AscendedClassSkill(PrimaryClassSkill firstPrimaryClass, PrimaryClassSkill secondPrimaryClass) {
        this.firstPrimaryClass = firstPrimaryClass;
        this.secondPrimaryClass = secondPrimaryClass;
    }

    public PrimaryClassSkill getFirstPrimaryClass() {
        return firstPrimaryClass;
    }
    public PrimaryClassSkill getSecondPrimaryClass() { return secondPrimaryClass; }

    public static Optional<AscendedClassSkill> getAscendedClassSkill(PrimaryClassSkill firstPrimaryClass, PrimaryClassSkill secondPrimaryClass) {
        return Arrays.stream(AscendedClassSkill.values())
                .filter(ascendedClassSkill -> ascendedClassSkill.getFirstPrimaryClass().equals(firstPrimaryClass) && ascendedClassSkill.getSecondPrimaryClass().equals(secondPrimaryClass)
                        || ascendedClassSkill.getFirstPrimaryClass().equals(secondPrimaryClass) && ascendedClassSkill.getSecondPrimaryClass().equals(firstPrimaryClass))
                .findFirst();
    }

    @Override
    public String getSkillName() {
        return ClassUtil.getSkillString(this);
    }

    @Override
    public ClassType getClassType() {
        return ClassType.ASCENDED;
    }
}