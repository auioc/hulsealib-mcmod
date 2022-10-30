package org.auioc.mcmod.hulsealib.mod.mixin.server;

import org.auioc.mcmod.arnicalib.game.entity.player.PlayerUtils;
import org.auioc.mcmod.hulsealib.mod.common.network.packet.client.ClientboundSetExhaustionPacket;
import org.auioc.mcmod.hulsealib.mod.common.network.packet.client.ClientboundSetSaturationPacket;
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
    private float saturationLevel;
    @Shadow
    private float exhaustionLevel;

    private float lastExhaustionLevel;
    private float lastSaturationLevel;

    @Inject(
        method = "Lnet/minecraft/world/food/FoodData;tick(Lnet/minecraft/world/entity/player/Player;)V",
        at = @At(value = "HEAD"),
        require = 1,
        allow = 1
    )
    public void tick_send(Player p_38711_, CallbackInfo ci) {
        if (p_38711_ instanceof ServerPlayer player && PlayerUtils.isOp(player)) {
            if (this.lastSaturationLevel != this.saturationLevel) ClientboundSetSaturationPacket.sendToClient(player, this.saturationLevel);
            if (this.lastExhaustionLevel != this.exhaustionLevel) ClientboundSetExhaustionPacket.sendToClient(player, this.exhaustionLevel);
            this.lastSaturationLevel = this.saturationLevel;
            this.lastExhaustionLevel = this.exhaustionLevel;
        }
    }

}
