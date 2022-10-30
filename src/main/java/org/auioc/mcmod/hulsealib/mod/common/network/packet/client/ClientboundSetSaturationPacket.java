package org.auioc.mcmod.hulsealib.mod.common.network.packet.client;

import org.auioc.mcmod.arnicalib.game.network.IHPacket;
import org.auioc.mcmod.hulsealib.mod.common.network.HLPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundSetSaturationPacket implements IHPacket {

    private final float saturation;

    public ClientboundSetSaturationPacket(float saturation) {
        this.saturation = saturation;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("resource")
    public void handle(Context ctx) {
        Minecraft.getInstance().player.getFoodData().setSaturation(this.saturation);
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeFloat(this.saturation);
    }

    public static ClientboundSetSaturationPacket decode(FriendlyByteBuf buffer) {
        return new ClientboundSetSaturationPacket(buffer.readFloat());
    }


    public static void sendToClient(ServerPlayer player, float saturation) {
        HLPacketHandler.sendToClient(player, new ClientboundSetSaturationPacket(saturation));
    }

}
