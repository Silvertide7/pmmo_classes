package net.silvertide.pmmo_classes.items;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.silvertide.pmmo_classes.data.AddClassSkillResult;
import net.silvertide.pmmo_classes.data.PlayerClassProfile;
import net.silvertide.pmmo_classes.utils.PlayerMessenger;
import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.items.SkillGrantItem;

import java.util.List;

public class ClassGrantItem extends SkillGrantItem {

    public ClassGrantItem() {
        super();
    }

    @Override
    public void applyEffects(String skill, ApplicationType applicationType, Long applicationValue, int experienceCost, ServerPlayer serverPlayer, ItemStack stack) {
        PlayerClassProfile playerClassProfile = new PlayerClassProfile(serverPlayer);
        AddClassSkillResult addClassSkillResult = playerClassProfile.canAddClassSkill(skill, applicationValue);
        if(addClassSkillResult.success()) {
            super.applyEffects(skill, applicationType, applicationValue, experienceCost, serverPlayer, stack);
        }
        PlayerMessenger.displayTranslatabelClientMessage(serverPlayer, addClassSkillResult.messageComponent());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Component information = Component.literal("Hey");
        tooltipComponents.add(information);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
