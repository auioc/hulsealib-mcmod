package org.auioc.mcmod.hulsealib.game.effect;

import net.minecraft.world.effect.MobEffectInstance;

public interface ICustomColorMobEffect {

    /**
     * @see #coremod_getColor CoreMod implemention
     * @see net.minecraft.world.item.alchemy.PotionUtils#getColor PotionUtils#getColor
     */
    int getColor(MobEffectInstance instance);

    /**
     * Coremod hulsealib.potion_utils.get_color
     */
    static int coremod_getColor(MobEffectInstance instance, int _j) {
        var effect = instance.getEffect();
        if (effect instanceof ICustomColorMobEffect customColorEffect) {
            return customColorEffect.getColor(instance);
        }
        return effect.getColor();
    }

}
