package org.auioc.mcmod.hulsealib.mod.common.network;

import org.auioc.mcmod.arnicalib.game.entity.player.PlayerUtils;
import org.auioc.mcmod.arnicalib.game.network.HPacketHandler;
import org.auioc.mcmod.arnicalib.game.network.IHPacket;
import org.auioc.mcmod.arnicalib.game.server.ServerUtils;
import org.auioc.mcmod.hulsealib.HulseaLib;
import net.minecraft.server.level.ServerPlayer;

public final class HLPacketHandler {

    private static final String PROTOCOL_VERSION = Integer.toString(1);
    protected static final HPacketHandler HANDLER = new HPacketHandler(HulseaLib.id("main"), PROTOCOL_VERSION);

    public static void init() {
        HLPackets.register();
    }

    public static <MSG extends IHPacket> void sendToServer(MSG msg) {
        HANDLER.sendToServer(msg);
    }

    public static <MSG extends IHPacket> void sendToClient(ServerPlayer player, MSG msg) {
        HANDLER.sendToClient(player, msg);
    }

    @Deprecated
    public static <MSG extends IHPacket> void sendToClientAll(MSG msg) {
        for (var player : ServerUtils.getServer().getPlayerList().getPlayers()) sendToClient(player, msg);
    }

    @Deprecated
    public static <MSG extends IHPacket> void sendToClientOp(ServerPlayer player, MSG msg) {
        if (PlayerUtils.isOp(player)) sendToClient(player, msg);
    }

    @Deprecated
    public static <MSG extends IHPacket> void sendToClientAllOp(MSG msg) {
        for (var player : ServerUtils.getServer().getPlayerList().getPlayers()) sendToClientOp(player, msg);
    }

}
