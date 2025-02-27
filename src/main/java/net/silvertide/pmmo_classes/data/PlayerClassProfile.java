package net.silvertide.pmmo_classes.data;

import harmonised.pmmo.core.Core;
import harmonised.pmmo.core.IDataStorage;
import harmonised.pmmo.storage.Experience;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
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
}
