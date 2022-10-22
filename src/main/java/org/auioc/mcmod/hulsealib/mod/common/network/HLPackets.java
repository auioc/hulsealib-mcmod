package org.auioc.mcmod.hulsealib.mod.common.network;

import static org.auioc.mcmod.hulsealib.mod.common.network.HLPacketHandler.HANDLER;
import org.auioc.mcmod.hulsealib.mod.common.network.packet.client.ClientboundDrawParticleShapePacket;
import org.auioc.mcmod.hulsealib.mod.common.network.packet.client.ClientboundSetExhaustionPacket;

public final class HLPackets {

    protected static void register() {
        HANDLER.registerServerToClient(ClientboundDrawParticleShapePacket.class, ClientboundDrawParticleShapePacket::decode);
        HANDLER.registerServerToClient(ClientboundSetExhaustionPacket.class, ClientboundSetExhaustionPacket::decode);
    }

}
