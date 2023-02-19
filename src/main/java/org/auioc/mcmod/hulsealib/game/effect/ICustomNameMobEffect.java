package org.auioc.mcmod.hulsealib.game.effect;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;

public interface ICustomNameMobEffect {

    /**
     * @see org.auioc.mcmod.hulsealib.mod.mixin.client.MixinEffectRenderingInventoryScreen#getEffectName Mixin implemention
     * @see net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen#getEffectName EffectRenderingInventoryScreen#getEffectName
     */
    @Nullable
    default Component getEffectName(MobEffectInstance instance) {
        return null;
    }

}
