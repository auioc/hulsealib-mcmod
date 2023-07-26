package org.auioc.mcmod.hulsealib.game.event.server;

import javax.annotation.Nullable;
import org.auioc.mcmod.arnicalib.game.event.server.ServerPlayerEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class ItemHurtEvent extends ServerPlayerEvent {

    private final ItemStack itemStack;
    private final RandomSource random;
    private int damage;

    public ItemHurtEvent(ItemStack itemStack, int damage, RandomSource random, @Nullable ServerPlayer player) {
        super(player);
        this.itemStack = itemStack;
        this.damage = damage;
        this.random = random;
    }

    @Override
    @Nullable
    public ServerPlayer getServerPlayer() {
        return super.getServerPlayer();
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public RandomSource getRandom() {
        return this.random;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}

