package net.silvertide.pmmo_classes.data;

import java.util.Arrays;
import java.util.Optional;


public enum AscendedClassSkill implements IClassSkill {
    RAGNAROK(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.BARBARIAN),
    ECHOIST(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.BARD), // TODO: RENAME
    RELIQUARIAN(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.CLERIC), // TODO: RENAME
    THRYNN(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.DRUID), // TODO: RENAME
    TITAN(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.FIGHTER),
    ZENITH(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.MONK),
    VALEBORN(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.PALADIN), // TODO: RENAME
    STRYVER(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.RANGER), // TODO: RENAME
    GLOOMSMITH(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.ROGUE),
    EIDOLON(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.SORCERER),
    HEXWRIGHT(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.WARLOCK), // TODO: RENAME
    RUNESAGE(PrimaryClassSkill.ARTIFICER, PrimaryClassSkill.WIZARD), // TODO: RENAME

    STORM_BLADE(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.BARD), // TODO: RENAME
    WARPRIEST(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.CLERIC),
    STORMBORN(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.DRUID),
    WARLORD(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.FIGHTER),
    FIST_OF_THE_WILD(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.MONK), // TODO: RENAME
    CRUSADER(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.PALADIN),
    BEASTMASTER(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.RANGER),
    REAVER(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.ROGUE),
    BLOODMAGE(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.SORCERER), // TODO: RENAME
    HEXBORN(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.WARLOCK), // TODO: RENAME
    RUNEBREAKER(PrimaryClassSkill.BARBARIAN, PrimaryClassSkill.WIZARD),

    HYMNCALLER(PrimaryClassSkill.BARD, PrimaryClassSkill.CLERIC),
    WINDSINGER(PrimaryClassSkill.BARD, PrimaryClassSkill.DRUID),
    BLADE_DANCER(PrimaryClassSkill.BARD, PrimaryClassSkill.FIGHTER),
    WAYFARER(PrimaryClassSkill.BARD, PrimaryClassSkill.MONK),
    CHRONICLER(PrimaryClassSkill.BARD, PrimaryClassSkill.PALADIN), // TODO: RENAME
    WANDERER(PrimaryClassSkill.BARD, PrimaryClassSkill.RANGER),
    SHADOW_MINSTREL(PrimaryClassSkill.BARD, PrimaryClassSkill.ROGUE), // TODO: RENAME
    SPELLWEAVER(PrimaryClassSkill.BARD, PrimaryClassSkill.SORCERER),
    FATESINGER(PrimaryClassSkill.BARD, PrimaryClassSkill.WARLOCK),
    ARCANIST(PrimaryClassSkill.BARD, PrimaryClassSkill.WIZARD), // TODO: RENAME

    HIEROPHANT(PrimaryClassSkill.CLERIC, PrimaryClassSkill.DRUID),
    TEMPLAR(PrimaryClassSkill.CLERIC, PrimaryClassSkill.FIGHTER),
    ASCETIC(PrimaryClassSkill.CLERIC, PrimaryClassSkill.MONK),
    PARAGON(PrimaryClassSkill.CLERIC, PrimaryClassSkill.PALADIN),
    WARDEN(PrimaryClassSkill.CLERIC, PrimaryClassSkill.RANGER),
    INQUISITOR(PrimaryClassSkill.CLERIC, PrimaryClassSkill.ROGUE),
    MYSTIC(PrimaryClassSkill.CLERIC, PrimaryClassSkill.SORCERER),
    OCCULTIST(PrimaryClassSkill.CLERIC, PrimaryClassSkill.WARLOCK),
    THAUMATURGE(PrimaryClassSkill.CLERIC, PrimaryClassSkill.WIZARD), // TODO: RENAME

    GLADIATOR_OF_THE_WILD(PrimaryClassSkill.DRUID, PrimaryClassSkill.FIGHTER), // TODO: RENAME
    FERAL_SAGE(PrimaryClassSkill.DRUID, PrimaryClassSkill.MONK),
    NATURES_VINDICATOR(PrimaryClassSkill.DRUID, PrimaryClassSkill.PALADIN), // TODO: RENAME
    WILDSTALKER(PrimaryClassSkill.DRUID, PrimaryClassSkill.RANGER),
    TRICKSTER_SHAMAN(PrimaryClassSkill.DRUID, PrimaryClassSkill.ROGUE), // TODO: RENAME
    STORMCALLER(PrimaryClassSkill.DRUID, PrimaryClassSkill.SORCERER),
    BLIGHTCASTER(PrimaryClassSkill.DRUID, PrimaryClassSkill.WARLOCK),
    ARCHDRUID(PrimaryClassSkill.DRUID, PrimaryClassSkill.WIZARD),

    IRON_FIST(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.MONK), // TODO: RENAME
    KNIGHT_ERRANT(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.PALADIN), // TODO: RENAME
    VANGAURD(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.RANGER),
    BLADEMASTER(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.ROGUE), // TODO: RENAME
    SPELLBLADE(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.SORCERER), // TODO: RENAME
    HEXBLADE(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.WARLOCK), // TODO: RENAME
    BATTLEMAGE(PrimaryClassSkill.FIGHTER, PrimaryClassSkill.WIZARD), // TODO: RENAME

    FIST_OF_THE_LIGHT(PrimaryClassSkill.MONK, PrimaryClassSkill.PALADIN), // TODO: RENAME
    SHADOWSTRIKER(PrimaryClassSkill.MONK, PrimaryClassSkill.RANGER), // TODO: RENAME
    SILENT_FIST(PrimaryClassSkill.MONK, PrimaryClassSkill.ROGUE), // TODO: RENAME
    CHI_ARCANIST(PrimaryClassSkill.MONK, PrimaryClassSkill.SORCERER),
    WAY_OF_THE_FORSAKEN(PrimaryClassSkill.MONK, PrimaryClassSkill.WARLOCK), // TODO: RENAME
    RUNED_DISCIPLE(PrimaryClassSkill.MONK, PrimaryClassSkill.WIZARD),

    HUNGER_KNIGHT(PrimaryClassSkill.PALADIN, PrimaryClassSkill.RANGER), // TODO: RENAME
    DIVINE_ASSASSIN(PrimaryClassSkill.PALADIN, PrimaryClassSkill.ROGUE), // TODO: RENAME
    DRAGON_KNIGHT(PrimaryClassSkill.PALADIN, PrimaryClassSkill.SORCERER), // TODO: RENAME
    HERALD(PrimaryClassSkill.PALADIN, PrimaryClassSkill.WARLOCK),
    SPELL_TEMPLAR(PrimaryClassSkill.PALADIN, PrimaryClassSkill.WIZARD), // TODO: RENAME

    NIGHTSTALKER(PrimaryClassSkill.RANGER, PrimaryClassSkill.ROGUE),
    ELEMENTAL_ARCHER(PrimaryClassSkill.RANGER, PrimaryClassSkill.SORCERER), // TODO: RENAME
    CURSE_HUNTER(PrimaryClassSkill.RANGER, PrimaryClassSkill.WARLOCK),
    ARCANE_ARCHER(PrimaryClassSkill.RANGER, PrimaryClassSkill.WIZARD), // TODO: RENAME

    SHADOWCASTER(PrimaryClassSkill.ROGUE, PrimaryClassSkill.SORCERER),
    VOID_STALKER(PrimaryClassSkill.ROGUE, PrimaryClassSkill.WARLOCK),
    ILLUSIONIST(PrimaryClassSkill.ROGUE, PrimaryClassSkill.WIZARD),

    ELDRITCH_SCION(PrimaryClassSkill.SORCERER, PrimaryClassSkill.WARLOCK),
    ARCHMAGE(PrimaryClassSkill.SORCERER, PrimaryClassSkill.WIZARD),

    OCCULT_ARCANIST(PrimaryClassSkill.WARLOCK, PrimaryClassSkill.WIZARD); // TODO: RENAME


    private final PrimaryClassSkill firstPrimaryClass;
    private final PrimaryClassSkill secondPrimaryClass;

    AscendedClassSkill(PrimaryClassSkill firstPrimaryClass, PrimaryClassSkill secondPrimaryClass) {
        this.firstPrimaryClass = firstPrimaryClass;
        this.secondPrimaryClass = secondPrimaryClass;
    }

    public PrimaryClassSkill getFirstPrimaryClass() {
        return firstPrimaryClass;
    }
    public PrimaryClassSkill getSecondPrimaryClass() {
        return secondPrimaryClass;
    }

    public static Optional<AscendedClassSkill> getAscendedClassSkill(PrimaryClassSkill firstPrimaryClass, PrimaryClassSkill secondPrimaryClass) {
        return Arrays.stream(AscendedClassSkill.values())
                .filter(ascendedClassSkill -> ascendedClassSkill.getFirstPrimaryClass().equals(firstPrimaryClass) && ascendedClassSkill.getSecondPrimaryClass().equals(secondPrimaryClass)
                        || ascendedClassSkill.getFirstPrimaryClass().equals(secondPrimaryClass) && ascendedClassSkill.getSecondPrimaryClass().equals(firstPrimaryClass))
                .findFirst();
    }
}