package net.silvertide.pmmo_classes.data;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.core.Core;
import harmonised.pmmo.core.IDataStorage;
import harmonised.pmmo.storage.Experience;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.silvertide.pmmo_classes.config.ServerConfig;
import net.silvertide.pmmo_classes.utils.ClassUtil;
import org.checkerframework.checker.units.qual.A;

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
                        subClassSkill.getParentClass().equals(entry.getKey())
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
        // If adding a primary class, make sure you don't already have the maximum number of primary classes and that
        // you meet the level requirement to take a second class. Also make sure the level is not
        Optional<PrimaryClassSkill> primaryClassSkill = ClassUtil.getPrimaryClass(skill);
        if(primaryClassSkill.isPresent()) {
            // Make sure you can add a new class of higher level
            PrimaryClassSkill primaryClassSkillToAdd = primaryClassSkill.get();

            Optional<Map.Entry<PrimaryClassSkill, Experience>> matchingEntry = this.primaryClassMap.entrySet()
                    .stream()
                    .filter(primaryClassSkillExperienceEntry -> primaryClassSkillExperienceEntry.getKey() == primaryClassSkillToAdd).findFirst();

            // If the level you are trying to obtain is higher than the max allowed, deny it no matter what skill it is. If it passes this then it is a valid level.
            if(level > ServerConfig.MAX_LEVEL_ALLOWED.get()) {
                return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.above_max_level", ServerConfig.MAX_LEVEL_ALLOWED.get()));
            }

            // If you already have this class check if you are gaining exactly 1 level.
            if(matchingEntry.isPresent()) {
                if(level != matchingEntry.get().getValue().getLevel().getLevel() + 1) {
                    if(matchingEntry.get().getValue().getLevel().getLevel() == ServerConfig.MAX_LEVEL_ALLOWED.get()) {
                        return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.max_level_reached", primaryClassSkillToAdd.getTranslatedSkillName()));
                    }
                    return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.invalid_level", primaryClassSkillToAdd.getTranslatedSkillName(), matchingEntry.get().getValue().getLevel().getLevel() + 1));
                }
                return new AddClassSkillResult(true, Component.translatable("pmmo_classes.use_class_grant.message.primary_success", primaryClassSkillToAdd.getTranslatedSkillName(), level));
            }

            // If we made it this far we know this is a brand new class, not an existing one.
            // Check to make sure we aren't going over the number of classes allowed.
            if(this.primaryClassMap.size() >= ServerConfig.NUM_CLASSES_ALLOWED.get()) {
                return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.class_capacity_reached"));
            }

            // If there is exactly 1 class then we need to check if we have reached the level requirement to gain another class.
            if(this.primaryClassMap.size() == 1) {
                Map.Entry<PrimaryClassSkill, Experience> entry = this.primaryClassMap.entrySet().iterator().next();
                if(entry.getKey() != primaryClassSkillToAdd && entry.getValue().getLevel().getLevel() < ServerConfig.SECOND_CLASS_LEVEL_REQ.get()) {
                    return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.below_level_req", ServerConfig.SECOND_CLASS_LEVEL_REQ.get(), entry.getKey().getTranslatedSkillName()));
                }
            }

            // Make sure the new classes level is 1.
            if(level != 1) {
                return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.level_one_req", primaryClassSkillToAdd.getTranslatedSkillName()));
            }

            return new AddClassSkillResult(true, Component.translatable("pmmo_classes.use_class_grant.message.primary_success", primaryClassSkillToAdd.getTranslatedSkillName(), level));
        }

        // If adding a subclass, make sure you have the requisite primary class, it is of the required level, and you don't already have
        // a subclass for that primary class.
        Optional<SubClassSkill> subClassSkill = ClassUtil.getSubClass(skill);
        if(subClassSkill.isPresent()) {
            SubClassSkill subClassSkillToAdd = subClassSkill.get();
            Optional<Map.Entry<PrimaryClassSkill, Experience>> parentPrimaryClassEntry = findMatchingPrimaryClass(subClassSkillToAdd);
            if(parentPrimaryClassEntry.isEmpty()) {
                return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.no_parent_class", subClassSkillToAdd.getParentClass().getTranslatedSkillName(), subClassSkillToAdd.getTranslatedSkillName()));
            } else {
                if(parentPrimaryClassEntry.get().getValue().getLevel().getLevel() < ServerConfig.SUB_CLASS_LEVEL_REQ.get()) {
                    return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.sub_level_req_not_met", parentPrimaryClassEntry.get().getKey().getTranslatedSkillName(), ServerConfig.SUB_CLASS_LEVEL_REQ.get(), subClassSkillToAdd.getTranslatedSkillName()));
                }
            }
            return new AddClassSkillResult(true, Component.translatable("pmmo_classes.use_class_grant.message.sub_success", subClassSkillToAdd.getParentClass().getTranslatedSkillName(), subClassSkillToAdd.getTranslatedSkillName()));
        }

        Optional<AscendedClassSkill> ascendedClassSkill= ClassUtil.getAscendedClass(skill);
        if(ascendedClassSkill.isPresent()) {
            if(this.ascendedClassSkill != null) {
                return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.only_one_ascended_class"));
            }
            return new AddClassSkillResult(true, Component.translatable("pmmo_classes.use_class_grant.message.ascended_success"));
        }

        return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.fall_through"));
    }
}
