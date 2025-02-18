package net.silvertide.pmmo_classes.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_classes.data.UseInsigniaResult;
import net.silvertide.pmmo_classes.items.components.InsigniaData;

public final class InsigniaUtil {
    private InsigniaUtil() {}

//    public static UseInsigniaResult canPlayerUseInsignia(ServerPlayer player, ItemStack stack) {
//        return DataComponentUtil.getSkillBookData(stack).map(insigniaData -> {
//            if(StringUtil.isBlank(insigniaData.skill())){
//                return new UseInsigniaResult(false, "pmmo_classes.message.no_skill_specified");
//            }
//
//            if(insigniaData.applicationValue() <= 0) {
//                return new UseInsigniaResult(false, "pmmo_classes.message.value_zero_or_less");
//            }
//
//            if(StringUtil.isBlank(insigniaData.applicationType())) {
//                return new UseInsigniaResult(false, "pmmo_classes.message.no_application_type");
//            }
//
//            try {
//                ApplicationType.valueOf(insigniaData.applicationType().toUpperCase());
//            } catch(IllegalArgumentException ex) {
//                return new UseInsigniaResult(false, "pmmo_classes.message.wrong_application_type");
//            }
//
//            if (PMMOUtil.isPlayerAtMaxLevel(player, insigniaData.skill())) {
//                return new UseInsigniaResult(false, "pmmo_classes.message.max_level");
//            }
//            return new UseInsigniaResult(true, "");
//        }).orElse(new UseInsigniaResult(false, "pmmo_classes.message.no_data_found"));
//
//    }
//
//    public static String getSkillBookEffectTranslationKey(InsigniaData insigniaData) {
//        try {
//            String translateKey = "pmmo_classes.message.experience_effect";
//            if(insigniaData.getApplicationType().equals(ApplicationType.LEVEL)) {
//                translateKey = insigniaData.applicationValue() > 1 ? "pmmo_classes.message.levels_effect" : "pmmo_classes.message.level_effect";
//            }
//            return translateKey;
//        } catch (IllegalArgumentException ignored) {
//            return "pmmo_classes.message.wrong_effect";
//        }
//    }
}
