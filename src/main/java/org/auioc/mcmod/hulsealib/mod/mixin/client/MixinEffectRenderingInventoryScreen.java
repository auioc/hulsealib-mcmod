package org.auioc.mcmod.hulsealib.mod.mixin.client;

import org.auioc.mcmod.hulsealib.game.effect.ICustomNameMobEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;

@Mixin(value = EffectRenderingInventoryScreen.class)
public class MixinEffectRenderingInventoryScreen {

    @Inject(
        method = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;getEffectName(Lnet/minecraft/world/effect/MobEffectInstance;)Lnet/minecraft/network/chat/Component;",
        at = @At(value = "HEAD"),
        require = 1,
        allow = 1,
        cancellable = true
    )
    private void getEffectName(MobEffectInstance p_194001_, CallbackInfoReturnable<Component> cir) {
        if (p_194001_.getEffect() instanceof ICustomNameMobEffect customNameEffect) {
            var name = customNameEffect.getEffectName(p_194001_);
            if (name != null) {
                cir.setReturnValue(name);
            }
        }
    }

}
