package org.auioc.mcmod.hulsealib.game.effect;

import net.minecraft.world.effect.MobEffectInstance;

public interface ICustomColorMobEffect {

    /**
     * @see net.minecraft.world.item.alchemy.PotionUtils#getColor PotionUtils#getColor
     */
    int getColor(MobEffectInstance instance);

}
