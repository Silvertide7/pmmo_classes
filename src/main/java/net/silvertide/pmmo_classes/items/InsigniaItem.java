package net.silvertide.pmmo_classes.items;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.silvertide.pmmo_classes.PMMOClasses;
import net.silvertide.pmmo_classes.data.UseInsigniaResult;
import net.silvertide.pmmo_classes.utils.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InsigniaItem extends Item {
    private static final int USE_DURATION = 20;

    public InsigniaItem() {
        super(new Item.Properties().stacksTo(1).fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if(player instanceof ServerPlayer serverPlayer) {
            UseInsigniaResult useResult = InsigniaUtil.canPlayerUseInsignia(serverPlayer, stack);
            if (useResult.success()) {
                serverPlayer.startUsingItem(usedHand);
                return InteractionResultHolder.success(stack);
            } else {
                serverPlayer.sendSystemMessage(Component.translatable(useResult.message()));
            }
        }
        return InteractionResultHolder.fail(stack);
    }
//
//
//    @Override
//    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
//        if (livingEntity instanceof ServerPlayer serverPlayer) {
//            useSkillBook(serverPlayer, stack);
//            if (!serverPlayer.getAbilities().instabuild) {
//                stack.shrink(1);
//            }
//            ServerLevel serverlevel = (ServerLevel) level;
//            for(int i = 0; i < 30; ++i) {
//                serverlevel.sendParticles(ParticleTypes.ENCHANT, serverPlayer.getX() + level.random.nextDouble(), serverPlayer.getY() + 1, serverPlayer.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
//            }
//        }
//        return stack;
//    }
//
//    private void useSkillBook(ServerPlayer serverPlayer, ItemStack stack) {
//        DataComponentUtil.getInsigniaData(stack).ifPresent(skillBookData -> {
//            long currentLevel = APIUtils.getLevel(skillBookData.skill(), serverPlayer);
//            long maxLevel = Config.server().levels().maxLevel();
//
//            try {
//                long valueToAdd = skillBookData.applicationValue();
//
//
//            } catch(IllegalArgumentException | ArithmeticException ignored) {
//                serverPlayer.sendSystemMessage(Component.translatable("pmmo_classes.message.use_book_error"));
//            }
//        });
//    }
//
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CROSSBOW;
    }
//
//    @Override
//    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//        DataComponentUtil.getInsigniaData(stack).ifPresent(skillBookData -> {
//            tooltipComponents.add(Component.translatable(InsigniaUtil.getSkillBookEffectTranslationKey(skillBookData), skillBookData.applicationValue(), skillBookData.getSkillName()));
//        });
//        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//    }
//
    @Override
    public String getDescriptionId(ItemStack stack) {
        return DataComponentUtil.getInsigniaData(stack).map(skillBookData -> switch(skillBookData.getRank()) {
            case PLAIN -> super.getDescriptionId(stack);
            case IRON -> "item.pmmo_classes.insignia_iron";
            case GOLD -> "item.pmmo_classes.insignia_gold";
            case EMERALD -> "item.pmmo_classes.insignia_emerald";
            case DIAMOND -> "item.pmmo_classes.insignia_diamond";
            case null -> super.getDescriptionId(stack);
        }).orElse(super.getDescriptionId(stack));
    }
}
