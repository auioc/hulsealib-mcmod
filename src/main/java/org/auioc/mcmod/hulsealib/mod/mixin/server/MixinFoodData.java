package org.auioc.mcmod.hulsealib.mod.mixin.server;

import org.auioc.mcmod.hulsealib.mod.common.network.HLPacketHandler;
import org.auioc.mcmod.hulsealib.mod.common.network.packet.client.ClientboundSetExhaustionPacket;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;

@Mixin(value = FoodData.class)
public class MixinFoodData {

    @Shadow
    private float exhaustionLevel;

    private float lastExhaustionLevel;

    @Inject(
        method = "Lnet/minecraft/world/food/FoodData;tick(Lnet/minecraft/world/entity/player/Player;)V",
        at = @At(value = "HEAD"),
        require = 1,
        allow = 1
    )
    public void tick_sendExhaustion(Player p_38711_, CallbackInfo ci) {
        if (this.lastExhaustionLevel != this.exhaustionLevel) {
            HLPacketHandler.sendToClient((ServerPlayer) p_38711_, new ClientboundSetExhaustionPacket(this.exhaustionLevel));
        }
    }

    @Inject(
        method = "Lnet/minecraft/world/food/FoodData;tick(Lnet/minecraft/world/entity/player/Player;)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/player/Player;level:Lnet/minecraft/world/level/Level;",
            opcode = Opcodes.GETFIELD,
            ordinal = 1
        ),
        require = 1,
        allow = 1
    )
    public void tick_setLastExhaustion(Player p_38711_, CallbackInfo ci) {
        this.lastExhaustionLevel = this.exhaustionLevel;
    }

}
