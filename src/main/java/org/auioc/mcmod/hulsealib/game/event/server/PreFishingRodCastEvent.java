package org.auioc.mcmod.hulsealib.game.event.server;

import org.auioc.mcmod.arnicalib.game.event.ServerPlayerEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class PreFishingRodCastEvent extends ServerPlayerEvent {

    private final ItemStack fishingRod;
    private int speedBonus;
    private int luckBonus;

    public PreFishingRodCastEvent(ServerPlayer player, ItemStack fishingRod, int speedBonus, int luckBonus) {
        super(player);
        this.fishingRod = fishingRod;
        this.speedBonus = speedBonus;
        this.luckBonus = luckBonus;
    }

    public ItemStack getFishingRod() {
        return this.fishingRod;
    }

    public int getSpeedBonus() {
        return this.speedBonus;
    }

    public void setSpeedBonus(int speedBonus) {
        this.speedBonus = speedBonus;
    }

    public int getLuckBonus() {
        return this.luckBonus;
    }

    public void setLuckBonus(int luckBonus) {
        this.luckBonus = luckBonus;
    }

}
