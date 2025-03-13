package net.silvertide.pmmo_classes.events.custom;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
/**
 * GainClassSkillEvent is fired before / after a {@link Player} gains a new class.<br>
 * <br>
 * The Pre event is not Cancelable.<br>
 * <br>
 * This event does not have a result.<br>
 * <br>
 * This event is fired on the Neoforge Event Bus (GAME).<br>
 **/
public abstract class GainClassSkillEvent extends PlayerEvent {
        private final String skill;

        public GainClassSkillEvent(Player player, String skill) {
            super(player);
            this.skill = skill;
        }

        public String getSkill() {
            return this.skill;
        }

        public static class Pre extends GainClassSkillEvent implements ICancellableEvent {
            public Pre(Player player, String skill) {
                super(player, skill);
            }
        }

        public static class Post extends GainClassSkillEvent {
            public Post(Player player, String skill) {
                super(player, skill);
            }
        }
}
