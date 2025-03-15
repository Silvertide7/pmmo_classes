package net.silvertide.pmmo_classes.data;

import harmonised.pmmo.core.Core;
import harmonised.pmmo.core.IDataStorage;
import harmonised.pmmo.storage.Experience;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.silvertide.pmmo_classes.config.ServerConfig;
import net.silvertide.pmmo_classes.utils.ClassUtil;

import java.util.*;

public class PlayerClassProfile {
    private Map<PrimaryClassSkill, Experience> primaryClassMap;
    private Map<SubClassSkill, Experience> subClassMap;
    private AscendedClassSkill ascendedClassSkill;

    public PlayerClassProfile(Player player) {
        primaryClassMap = new EnumMap<>(PrimaryClassSkill.class);
        subClassMap = new EnumMap<>(SubClassSkill.class);
        ascendedClassSkill = null;

        UUID playerUUID = (player instanceof ServerPlayer serverPlayer) ? serverPlayer.getUUID() : null;
        IDataStorage dataStorage = Core.get((player instanceof ServerPlayer) ? LogicalSide.SERVER : LogicalSide.CLIENT).getData();

        if (dataStorage != null) {
            var xpMap = dataStorage.getXpMap(playerUUID);

            // Loop through all player skills and see if they are a primary class, sub class, or ascended class and store that data.
            xpMap.forEach((skillKey, experience) -> {
                    ClassUtil.getPrimaryClass(skillKey).ifPresent(primaryClassSkill -> primaryClassMap.put(primaryClassSkill, experience));
                    ClassUtil.getSubClass(skillKey).ifPresent(subClassSkill -> subClassMap.put(subClassSkill, experience));

                    if(ascendedClassSkill == null) {
                        ClassUtil.getAscendedClass(skillKey).ifPresent(ascendedClass -> this.ascendedClassSkill = ascendedClass);
                    }
                }
            );
        }
    }

    // Returns a SubClassSkill if it exists and is >= level 1, and matches the passed PrimaryClassSkill.
    public Optional<SubClassSkill> findMatchingSubClass(PrimaryClassSkill primaryClassSkill) {
        return this.subClassMap.entrySet().stream().filter(entry ->
                entry.getValue().getLevel().getLevel() >= 1 &&
                primaryClassSkill.equals(entry.getKey().getParentClass())
        ).findFirst().map(Map.Entry::getKey);
    }

    public Optional<Map.Entry<PrimaryClassSkill, Experience>> findMatchingPrimaryClass(SubClassSkill subClassSkill) {
        return this.primaryClassMap.entrySet().stream().filter(entry ->
                entry.getValue().getLevel().getLevel() >= 1 &&
                        subClassSkill.getParentClass() == entry.getKey()
        ).findFirst();
    }

    public int getNumPrimaryClasses() {
        return primaryClassMap.size();
    }

    public List<PrimaryClassSkill> getPrimaryClassesAsList() {
        if(!primaryClassMap.isEmpty()) {
            return primaryClassMap.keySet().stream().toList();
        } else {
            return new ArrayList<>();
        }
    }

    public List<SubClassSkill> getSubClassesAsList() {
        if(!subClassMap.isEmpty()) {
            return subClassMap.keySet().stream().toList();
        } else {
            return new ArrayList<>();
        }
    }

    // GETTERS AND SETTERS
    public Map<PrimaryClassSkill, Experience> getPrimaryClassMap() {
        return primaryClassMap;
    }

    public void setPrimaryClassMap(Map<PrimaryClassSkill, Experience> primaryClassMap) {
        this.primaryClassMap = primaryClassMap;
    }

    public Map<SubClassSkill, Experience> getSubClassMap() {
        return subClassMap;
    }

    public void setSubClassMap(Map<SubClassSkill, Experience> subClassMap) {
        this.subClassMap = subClassMap;
    }

    public AscendedClassSkill getAscendedClassSkill() {
        return ascendedClassSkill;
    }

    public void setAscendedClassSkill(AscendedClassSkill ascendedClassSkill) {
        this.ascendedClassSkill = ascendedClassSkill;
    }

    public AddClassSkillResult canAddClassSkill(String skill, Long level) {
        // If the skill is a primary class, validate it
        Optional<PrimaryClassSkill> primaryClassSkill = ClassUtil.getPrimaryClass(skill);
        if(primaryClassSkill.isPresent()) {
            return validatePrimaryClassSkill(primaryClassSkill.get(), level);
        }

        // If the skill is a subclass, validate it
        Optional<SubClassSkill> subClassSkill = ClassUtil.getSubClass(skill);
        if(subClassSkill.isPresent()) {
           return validateSubClassSkill(subClassSkill.get(), level);
        }

        // If the skill is an ascended class, validate it
        Optional<AscendedClassSkill> ascendedClassSkill= ClassUtil.getAscendedClass(skill);
        if(ascendedClassSkill.isPresent()) {
            return validateAscendedClassSkill(ascendedClassSkill.get(), level);
        }

        // If it was no valid classes then fail.
        return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.fall_through"));
    }

    private AddClassSkillResult validatePrimaryClassSkill(PrimaryClassSkill primaryClassSkill, Long level) {
        // If adding a primary class, make sure you don't already have the maximum number of primary classes and that
        // you meet the level requirement to take a second class. Also make sure the level is not
        Optional<Map.Entry<PrimaryClassSkill, Experience>> matchingEntry = this.primaryClassMap.entrySet()
                .stream()
                .filter(primaryClassSkillExperienceEntry -> primaryClassSkillExperienceEntry.getKey() == primaryClassSkill).findFirst();

        // If the level you are trying to obtain is higher than the max allowed, deny it no matter what skill it is. If it passes this then it is a valid level.
        if(level > ServerConfig.MAX_LEVEL_ALLOWED.get()) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.above_max_level", ServerConfig.MAX_LEVEL_ALLOWED.get()));
        }

        // If you already have this class check if you are gaining exactly 1 level.
        if(matchingEntry.isPresent()) {
            if(level != matchingEntry.get().getValue().getLevel().getLevel() + 1) {
                if(matchingEntry.get().getValue().getLevel().getLevel() == ServerConfig.MAX_LEVEL_ALLOWED.get()) {
                    return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.max_level_reached", primaryClassSkill.getTranslatedSkillName()));
                }
                return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.invalid_level", primaryClassSkill.getTranslatedSkillName(), matchingEntry.get().getValue().getLevel().getLevel() + 1));
            }
            return new AddClassSkillResult(true, Component.translatable("pmmo_classes.use_class_grant.message.primary_success", primaryClassSkill.getTranslatedSkillName(), level));
        }

        // If we made it this far we know this is a brand new class, not an existing one.
        // Check to make sure we aren't going over the number of classes allowed.
        if(this.primaryClassMap.size() >= ServerConfig.NUM_CLASSES_ALLOWED.get()) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.class_capacity_reached"));
        }

        // If there is exactly 1 class then we need to check if we have reached the level requirement to gain another class.
        if(this.primaryClassMap.size() == 1) {
            Map.Entry<PrimaryClassSkill, Experience> entry = this.primaryClassMap.entrySet().iterator().next();
            if(entry.getKey() != primaryClassSkill && entry.getValue().getLevel().getLevel() < ServerConfig.SECOND_CLASS_LEVEL_REQ.get()) {
                return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.below_level_req", ServerConfig.SECOND_CLASS_LEVEL_REQ.get(), entry.getKey().getTranslatedSkillName()));
            }
        }

        // Make sure the new classes level is 1.
        if(level != 1) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.level_one_req", primaryClassSkill.getTranslatedSkillName()));
        }

        return new AddClassSkillResult(true, Component.translatable("pmmo_classes.use_class_grant.message.primary_success", primaryClassSkill.getTranslatedSkillName(), level));
    }


    private AddClassSkillResult validateSubClassSkill(SubClassSkill subClassSkill, Long level) {
        // First lets see if we already have a subclass with this type. If so fail, you can't get it again.
        Optional<Map.Entry<SubClassSkill, Experience>> matchingEntry = this.subClassMap.entrySet()
                .stream()
                .filter(subClassSkillEntry -> subClassSkillEntry.getKey() == subClassSkill).findFirst();
        if(matchingEntry.isPresent()) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.duplicate_subclass", subClassSkill.getTranslatedSkillName()));
        }

        // Check to make sure we have the primary class in order to take this subclass
        Optional<Map.Entry<PrimaryClassSkill, Experience>> parentPrimaryClassEntry = findMatchingPrimaryClass(subClassSkill);
        if(parentPrimaryClassEntry.isEmpty()) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.no_parent_class", subClassSkill.getParentClass().getTranslatedSkillName(), subClassSkill.getTranslatedSkillName()));
        }
        // If we do, check to make sure that primary class has the required level before you take a subclass.
        if(parentPrimaryClassEntry.get().getValue().getLevel().getLevel() < ServerConfig.SUB_CLASS_LEVEL_REQ.get()) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.sub_level_req_not_met", parentPrimaryClassEntry.get().getKey().getTranslatedSkillName(), ServerConfig.SUB_CLASS_LEVEL_REQ.get(), subClassSkill.getTranslatedSkillName()));
        }

        return new AddClassSkillResult(true, Component.translatable("pmmo_classes.use_class_grant.message.sub_success", subClassSkill.getParentClass().getTranslatedSkillName(), subClassSkill.getTranslatedSkillName()));
    }

    private AddClassSkillResult validateAscendedClassSkill(AscendedClassSkill ascendedClassSkill, Long level) {
        if(this.ascendedClassSkill != null) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.only_one_ascended_class"));
        }
        return new AddClassSkillResult(true, Component.translatable("pmmo_classes.use_class_grant.message.ascended_success"));
    }

}
