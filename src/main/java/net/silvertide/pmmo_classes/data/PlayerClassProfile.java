package net.silvertide.pmmo_classes.data;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.core.Core;
import harmonised.pmmo.core.IDataStorage;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.silvertide.pmmo_classes.config.Config;
import net.silvertide.pmmo_classes.utils.ClassUtil;
import net.silvertide.pmmo_classes.utils.PMMOUtil;

import java.util.*;

public class PlayerClassProfile {
    private Map<PrimaryClassSkill, Integer> primaryClassMap;
    private Map<SubClassSkill, Integer> subClassMap;
    private AscendedClassSkill ascendedClassSkill;

    public PlayerClassProfile(Player player) {
        setupProfile(player);
    }

    private void setupProfile(Player player) {
        primaryClassMap = new EnumMap<>(PrimaryClassSkill.class);
        subClassMap = new EnumMap<>(SubClassSkill.class);
        ascendedClassSkill = null;

        UUID playerUUID = (player instanceof ServerPlayer serverPlayer) ? serverPlayer.getUUID() : null;
        IDataStorage dataStorage = Core.get((player instanceof ServerPlayer) ? LogicalSide.SERVER : LogicalSide.CLIENT).getData();

        if (dataStorage != null) {
            var xpMap = dataStorage.getXpMap(playerUUID);

            // Loop through all player skills and see if they are a primary class, sub class, or ascended class and store that data.
            xpMap.forEach((skillKey, experience) -> {
                        ClassUtil.getPrimaryClass(skillKey).ifPresent(primaryClassSkill -> primaryClassMap.put(primaryClassSkill, APIUtils.getLevel(skillKey, player)));
                        ClassUtil.getSubClass(skillKey).ifPresent(subClassSkill -> subClassMap.put(subClassSkill, APIUtils.getLevel(skillKey, player)));

                        if(ascendedClassSkill == null) {
                            ClassUtil.getAscendedClass(skillKey).ifPresent(ascendedClass -> this.ascendedClassSkill = ascendedClass);
                        }
                    }
            );
        }
    }

    public void refreshProfile(Player player) {
        setupProfile(player);
    }

    // Returns a SubClassSkill if it exists and is >= level 1, and matches the passed PrimaryClassSkill.
    public Optional<SubClassSkill> findMatchingSubClass(PrimaryClassSkill primaryClassSkill) {
        return this.subClassMap.entrySet().stream().filter(entry ->
                entry.getValue() >= 1 &&
                primaryClassSkill.equals(entry.getKey().getParentClass())
        ).findFirst().map(Map.Entry::getKey);
    }

    public Optional<Map.Entry<PrimaryClassSkill, Integer>> findMatchingPrimaryClass(SubClassSkill subClassSkill) {
        return this.primaryClassMap.entrySet().stream().filter(entry ->
                entry.getValue() >= 1 && subClassSkill.getParentClass() == entry.getKey()
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
    public Map<PrimaryClassSkill, Integer> getPrimaryClassMap() {
        return this.primaryClassMap;
    }

    public Map<SubClassSkill, Integer> getSubClassMap() {
        return this.subClassMap;
    }

    public AscendedClassSkill getAscendedClassSkill() {
        return this.ascendedClassSkill;
    }

    // Check if the player has more classes than is allowed. If so remove the excess classes.
    public boolean checkAndUpdatePrimaryClasses(ServerPlayer serverPlayer) {
        if(this.getPrimaryClassMap().size() > Config.NUM_CLASSES_ALLOWED.get()) {
            List<Map.Entry<PrimaryClassSkill, Integer>> sortedEntries = this.getPrimaryClassMap().entrySet()
                    .stream()
                    .sorted(Comparator.comparingInt(entry -> entry.getValue())) // Sort by level
                    .toList();

            // Remove the lowest level entries (if necessary)
            List<Map.Entry<PrimaryClassSkill, Integer>> entriesToRemove = sortedEntries.subList(0, sortedEntries.size() - Config.NUM_CLASSES_ALLOWED.get());
            PMMOUtil.deleteSkills(serverPlayer, entriesToRemove.stream().map(entry -> entry.getKey().getSkillName()).toList());
            return !entriesToRemove.isEmpty();
        }
        return false;
    }

    // This method checks if multiple subclasses exist for a single primary class type, which is a no no. If it does it just
    // deletes off all but the first in the list because it has no other way of knowing which to delete, they will all be level 1.
    public boolean checkAndUpdateSubClasses(ServerPlayer serverPlayer, SubClassSkill subClassSkillAdded) {
        List<PrimaryClassSkill> primaryClassSkills = this.getPrimaryClassesAsList();

        Set<String> subClassesToRemove = new HashSet<>();
        Map<PrimaryClassSkill, List<SubClassSkill>> primaryToSubMap = new HashMap<>();
        for (SubClassSkill subClassSkill : this.getSubClassesAsList()) {
            // Check to see if the subclass has a proper parent class.
            if (!primaryClassSkills.contains(subClassSkill.getParentClass())) {
                subClassesToRemove.add(subClassSkill.getSkillName());
            } else {
                // If it wasn't removed from the previous call then it does have a proper parent, check if there
                // are too many subclasses for any one parent.
                primaryToSubMap.computeIfAbsent(subClassSkill.getParentClass(), k -> new ArrayList<>()).add(subClassSkill);
            }
        }

        for (Map.Entry<PrimaryClassSkill, List<SubClassSkill>> entry : primaryToSubMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                if(subClassSkillAdded != null && entry.getKey() == subClassSkillAdded.getParentClass()) {
                    subClassesToRemove.addAll(entry.getValue().stream().filter(subClass -> subClass != subClassSkillAdded).map(SubClassSkill::getSkillName).toList());
                } else {
                    subClassesToRemove.addAll(entry.getValue().subList(1, entry.getValue().size()).stream().map(SubClassSkill::getSkillName).toList());
                }
            }
        }

        if (!subClassesToRemove.isEmpty()) {
            PMMOUtil.deleteSkills(serverPlayer, new ArrayList<>(subClassesToRemove));
            return true;
        }

        return false;
    }

    public void checkAndUpdateAscendedClasses(ServerPlayer serverPlayer) {
        // If you have an ascended class but ascended classes are not active, delete it
        if(!Config.ASCENDED_CLASSES_ACTIVE.get() && this.getAscendedClassSkill() != null) {
            PMMOUtil.deleteSkills(serverPlayer, List.of(this.getAscendedClassSkill().getSkillName()));
        } else if(Config.ASCENDED_CLASSES_ACTIVE.get()) {
            // If ascended classes are active and you currently don't have one, and you have 2 primary classes, check if one should be granted.
            List<PrimaryClassSkill> primaryClassesMeetingRequirements = this.getPrimaryClassMap().entrySet().stream()
                    .filter(entry -> entry.getValue() >= Config.ASCENDED_CLASS_LEVEL_REQ.get())
                    .map(Map.Entry::getKey)
                    .toList();

            if(primaryClassesMeetingRequirements.size() == 2) {
                AscendedClassSkill.getAscendedClassSkill(primaryClassesMeetingRequirements.get(0), primaryClassesMeetingRequirements.get(1))
                        .ifPresent(newAscendedClassSkill -> {
                            if(this.ascendedClassSkill == null || this.ascendedClassSkill != newAscendedClassSkill) {
                                if(this.ascendedClassSkill != null) {
                                    PMMOUtil.deleteSkills(serverPlayer, List.of(this.getAscendedClassSkill().getSkillName()));
                                }
                                APIUtils.setLevel(newAscendedClassSkill.getSkillName(), serverPlayer, 1);
                                serverPlayer.sendSystemMessage(Component.translatable("pmmo_classes.message.gained_ascended_class", newAscendedClassSkill.getTranslatedSkillName()));
                            }
                        });
            } else if(this.ascendedClassSkill != null) {
                PMMOUtil.deleteSkills(serverPlayer, List.of(this.getAscendedClassSkill().getSkillName()));
            }
        }
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
           return validateSubClassSkill(subClassSkill.get());
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
        Optional<Map.Entry<PrimaryClassSkill, Integer>> matchingEntry = this.primaryClassMap.entrySet()
                .stream()
                .filter(primaryClassSkillExperienceEntry -> primaryClassSkillExperienceEntry.getKey() == primaryClassSkill)
                .findFirst();

        // If the level you are trying to obtain is higher than the max allowed, deny it no matter what skill it is. If it passes this then it is a valid level.
        if(level > Config.MAX_LEVEL_ALLOWED.get()) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.above_max_level", Config.MAX_LEVEL_ALLOWED.get()));
        }

        // If you already have this class check if you are gaining exactly 1 level.
        if(matchingEntry.isPresent()) {
            if(level != matchingEntry.get().getValue() + 1) {
                if(matchingEntry.get().getValue() == Config.MAX_LEVEL_ALLOWED.get().longValue()) {
                    return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.max_level_reached", primaryClassSkill.getTranslatedSkillName()));
                }
                return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.invalid_level", primaryClassSkill.getTranslatedSkillName(), matchingEntry.get().getValue() + 1));
            }
            return new AddClassSkillResult(true, Component.translatable("pmmo_classes.use_class_grant.message.primary_success", primaryClassSkill.getTranslatedSkillName(), level));
        }

        // If we made it this far we know this is a brand new class, not an existing one.
        // Check to make sure we aren't going over the number of classes allowed.
        if(this.primaryClassMap.size() >= Config.NUM_CLASSES_ALLOWED.get()) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.class_capacity_reached"));
        }

        // If there is exactly 1 class then we need to check if we have reached the level requirement to gain another class.
        if(this.primaryClassMap.size() == 1) {
            Map.Entry<PrimaryClassSkill, Integer> entry = this.primaryClassMap.entrySet().iterator().next();
            if(entry.getKey() != primaryClassSkill && entry.getValue() < Config.SECOND_CLASS_LEVEL_REQ.get()) {
                return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.below_level_req", Config.SECOND_CLASS_LEVEL_REQ.get(), entry.getKey().getTranslatedSkillName()));
            }
        }

        // Make sure the new classes level is 1.
        if(level != 1) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.level_one_req", primaryClassSkill.getTranslatedSkillName()));
        }

        return new AddClassSkillResult(true, Component.translatable("pmmo_classes.use_class_grant.message.primary_success", primaryClassSkill.getTranslatedSkillName(), level));
    }


    private AddClassSkillResult validateSubClassSkill(SubClassSkill subClassSkill) {
        // First lets see if we already have a subclass with this type. If so fail, you can't get it again.
        Optional<Map.Entry<SubClassSkill, Integer>> matchingEntry = this.subClassMap.entrySet()
                .stream()
                .filter(subClassSkillEntry -> subClassSkillEntry.getKey() == subClassSkill).findFirst();
        if(matchingEntry.isPresent()) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.duplicate_subclass", subClassSkill.getTranslatedSkillName()));
        }

        // Check to make sure we have the primary class in order to take this subclass
        Optional<Map.Entry<PrimaryClassSkill, Integer>> parentPrimaryClassEntry = findMatchingPrimaryClass(subClassSkill);
        if(parentPrimaryClassEntry.isEmpty()) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.no_parent_class", subClassSkill.getParentClass().getTranslatedSkillName(), subClassSkill.getTranslatedSkillName()));
        }
        // If we do, check to make sure that primary class has the required level before you take a subclass.
        if(parentPrimaryClassEntry.get().getValue() < Config.SUB_CLASS_LEVEL_REQ.get()) {
            return new AddClassSkillResult(false, Component.translatable("pmmo_classes.use_class_grant.message.sub_level_req_not_met", parentPrimaryClassEntry.get().getKey().getTranslatedSkillName(), Config.SUB_CLASS_LEVEL_REQ.get(), subClassSkill.getTranslatedSkillName()));
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
