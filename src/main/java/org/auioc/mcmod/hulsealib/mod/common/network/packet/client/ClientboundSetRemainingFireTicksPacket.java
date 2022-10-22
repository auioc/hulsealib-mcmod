package org.auioc.mcmod.hulsealib.mod.common.network.packet.client;

import org.auioc.mcmod.arnicalib.game.network.IHPacket;
import org.auioc.mcmod.hulsealib.mod.mixinapi.common.IMixinPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundSetRemainingFireTicksPacket implements IHPacket {

    private final int remainingFireTicks;

    public ClientboundSetRemainingFireTicksPacket(int remainingFireTicks) {
        this.remainingFireTicks = remainingFireTicks;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("resource")
    public void handle(Context ctx) {
        ((IMixinPlayer) Minecraft.getInstance().player).setClientRemainingFireTicks(this.remainingFireTicks);
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.remainingFireTicks);
    }

    public static ClientboundSetRemainingFireTicksPacket decode(FriendlyByteBuf buffer) {
        return new ClientboundSetRemainingFireTicksPacket(buffer.readInt());
    }

}
