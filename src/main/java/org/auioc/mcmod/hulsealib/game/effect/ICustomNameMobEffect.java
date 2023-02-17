package org.auioc.mcmod.hulsealib.game.effect;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;

public interface ICustomNameMobEffect {

    @Nullable
    default Component getEffectName(MobEffectInstance instance) {
        return null;
    }

}
