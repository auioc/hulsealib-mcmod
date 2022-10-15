package org.auioc.mcmod.hulsealib.game.event.server;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

public class PreCrossbowReleaseEvent extends LivingEvent {

    private final ItemStack crossbow;
    private final Projectile projectile;

    public PreCrossbowReleaseEvent(LivingEntity living, ItemStack crossbow, Projectile projectile) {
        super(living);
        this.crossbow = crossbow;
        this.projectile = projectile;
    }

    public ItemStack getBow() {
        return crossbow;
    }

    public Projectile getProjectile() {
        return projectile;
    }

}
