package org.auioc.mcmod.hulsealib.mod.mixin.server;

import org.auioc.mcmod.arnicalib.game.entity.MobStance;
import org.auioc.mcmod.hulsealib.mod.server.event.HLServerEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;

@Mixin(value = PiglinAi.class)
public class MixinPiglinAi {

    @Inject(
        method = "Lnet/minecraft/world/entity/monster/piglin/PiglinAi;isWearingGold(Lnet/minecraft/world/entity/LivingEntity;)Z",
        at = @At(value = "HEAD"),
        cancellable = true,
        require = 1,
        allow = 1
    )
    private static void isWearingGold(LivingEntity p_34809_, CallbackInfoReturnable<Boolean> cri) {
        var stance = HLServerEventFactory.onPiglinChooseStanceEvent(p_34809_);
        if (stance == MobStance.NEUTRAL) {
            cri.setReturnValue(true);
        } else if (stance == MobStance.HOSTILE) {
            cri.setReturnValue(false);
        }
    }

}
