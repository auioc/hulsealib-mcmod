package org.auioc.mcmod.hulsealib.mod.mixin.server;

import javax.annotation.Nullable;
import org.auioc.mcmod.hulsealib.mod.server.event.HLServerEventFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;

@Mixin(targets = "net.minecraft.world.entity.animal.Cat$CatRelaxOnOwnerGoal")
public class MixinCatRelaxOnOwnerGoal {

    @Shadow
    @Final
    private Cat cat;

    @Nullable
    @Shadow
    private Player ownerPlayer;

    @ModifyConstant(
        method = "Lnet/minecraft/world/entity/animal/Cat$CatRelaxOnOwnerGoal;stop()V",
        constant = @Constant(doubleValue = 0.7D),
        require = 1,
        allow = 1
    )
    private double modifyConstant_stop(double title) {
        return HLServerEventFactory.onCatSetMorningGiftChance(this.cat, this.ownerPlayer);
    }

}
