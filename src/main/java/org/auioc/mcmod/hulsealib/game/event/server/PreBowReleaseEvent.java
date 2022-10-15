package org.auioc.mcmod.hulsealib.game.event.server;

import org.auioc.mcmod.arnicalib.game.event.ServerPlayerEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;

public class PreBowReleaseEvent extends ServerPlayerEvent {

    private final ItemStack bow;
    private final AbstractArrow arrow;

    public PreBowReleaseEvent(ServerPlayer player, ItemStack bow, AbstractArrow arrow) {
        super(player);
        this.bow = bow;
        this.arrow = arrow;
    }

    public ItemStack getBow() {
        return bow;
    }

    public AbstractArrow getArrow() {
        return arrow;
    }

}
