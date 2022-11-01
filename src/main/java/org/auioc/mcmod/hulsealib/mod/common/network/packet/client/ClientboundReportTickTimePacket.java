package org.auioc.mcmod.hulsealib.mod.common.network.packet.client;

import org.auioc.mcmod.arnicalib.game.network.IHPacket;
import org.auioc.mcmod.arnicalib.game.server.ServerUtils;
import org.auioc.mcmod.hulsealib.mod.common.network.HLPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundReportTickTimePacket implements IHPacket {

    private final double tps;
    private final double mspt;

    public ClientboundReportTickTimePacket(double tps, double mspt) {
        this.tps = tps;
        this.mspt = mspt;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle(Context ctx) {

    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeDouble(this.tps);
        buffer.writeDouble(this.mspt);
    }

    public static ClientboundReportTickTimePacket decode(FriendlyByteBuf buffer) {
        return new ClientboundReportTickTimePacket(buffer.readDouble(), buffer.readDouble());
    }


    public static void sendToAllClient() {
        var tps_mspt = ServerUtils.getTpsMspt();
        HLPacketHandler.sendToClientAll(new ClientboundReportTickTimePacket(tps_mspt.getLeft(), tps_mspt.getRight()));
    }

}
