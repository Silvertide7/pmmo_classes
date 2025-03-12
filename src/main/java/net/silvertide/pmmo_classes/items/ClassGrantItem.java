package net.silvertide.pmmo_classes.items;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.silvertide.pmmo_classes.data.PlayerClassProfile;
import net.silvertide.pmmo_skill_books.items.SkillGrantItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClassGrantItem extends SkillGrantItem {

    public ClassGrantItem() {
        super();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        if(player instanceof ServerPlayer) {
            PlayerClassProfile playerClassProfile = new PlayerClassProfile(player);


            return super.use(level, player, usedHand);

        }
        return InteractionResultHolder.fail(stack);

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Component information = Component.literal("Hey");
        tooltipComponents.add(information);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
