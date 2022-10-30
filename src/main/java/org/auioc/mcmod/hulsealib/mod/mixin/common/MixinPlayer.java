package org.auioc.mcmod.hulsealib.mod.mixin.common;

import org.auioc.mcmod.arnicalib.game.entity.player.PlayerUtils;
import org.auioc.mcmod.hulsealib.mod.common.network.packet.client.ClientboundSetRemainingFireTicksPacket;
import org.auioc.mcmod.hulsealib.mod.mixinapi.common.IMixinPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Mixin(value = Player.class)
public abstract class MixinPlayer extends LivingEntity implements IMixinPlayer {

    private MixinPlayer(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Shadow
    @Final
    private Abilities abilities;

    private int lastRemainingFireTicks;

    /**
     * @author WakelessSloth56
     * @reason {@link ClientboundSetRemainingFireTicksPacket}
     */
    @Overwrite()
    public void setRemainingFireTicks(int p_36353_) {
        if (((Player) (Object) this) instanceof ServerPlayer player && PlayerUtils.isOp(player)) {
            int t = this.abilities.invulnerable ? Math.min(p_36353_, 1) : p_36353_;
            super.setRemainingFireTicks(t);
            if (this.lastRemainingFireTicks != t) ClientboundSetRemainingFireTicksPacket.sendToClient(player, t);
            this.lastRemainingFireTicks = t;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setClientRemainingFireTicks(int ticks) {
        super.setRemainingFireTicks(ticks);
    }

}
