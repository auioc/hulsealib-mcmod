package org.auioc.mcmod.hulsealib.mod.server.event;

import org.auioc.mcmod.arnicalib.game.server.ServerUtils;
import org.auioc.mcmod.hulsealib.mod.common.network.packet.client.ClientboundReportTickTimePacket;
import org.auioc.mcmod.hulsealib.mod.server.command.HLServerCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class HLServerEventHandler {

    @SubscribeEvent
    public static void registerCommands(final RegisterCommandsEvent event) {
        HLServerCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onServerTick(final TickEvent.ServerTickEvent event) {
        int ticks = ServerUtils.getServer().getTickCount();
        if (event.phase == TickEvent.Phase.END) {
            if (ticks % 20 == 0) {
                ClientboundReportTickTimePacket.sendToAllClient();
            }
        }
    }

}
