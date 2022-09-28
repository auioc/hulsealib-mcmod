package org.auioc.mcmod.hulsealib.mod.server.event;

import org.auioc.mcmod.hulsealib.mod.server.command.HLServerCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class HLServerEventHandler {

    @SubscribeEvent
    public static void registerCommands(final RegisterCommandsEvent event) {
        HLServerCommands.register(event.getDispatcher());
    }

}
