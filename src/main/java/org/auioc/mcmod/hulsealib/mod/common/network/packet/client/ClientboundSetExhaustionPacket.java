package org.auioc.mcmod.hulsealib.mod.common.network.packet.client;

import org.auioc.mcmod.arnicalib.game.network.IHPacket;
import org.auioc.mcmod.hulsealib.mod.common.network.HLPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundSetExhaustionPacket implements IHPacket {

    private final float exhaustion;

    public ClientboundSetExhaustionPacket(float exhaustion) {
        this.exhaustion = exhaustion;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("resource")
    public void handle(Context ctx) {
        Minecraft.getInstance().player.getFoodData().setExhaustion(this.exhaustion);
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeFloat(this.exhaustion);
    }

    public static ClientboundSetExhaustionPacket decode(FriendlyByteBuf buffer) {
        return new ClientboundSetExhaustionPacket(buffer.readFloat());
    }


    public static void sendToClient(ServerPlayer player, float exhaustion) {
        HLPacketHandler.sendToClient(player, new ClientboundSetExhaustionPacket(exhaustion));
    }

}
