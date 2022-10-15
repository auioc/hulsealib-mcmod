package org.auioc.mcmod.hulsealib.mod;

import org.auioc.mcmod.hulsealib.game.command.HLCommandArguments;
import org.auioc.mcmod.hulsealib.mod.common.itemgroup.HLCreativeModeTabs;
import org.auioc.mcmod.hulsealib.mod.common.network.HLPacketHandler;
import org.auioc.mcmod.hulsealib.mod.server.event.HLServerEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("unused")
public final class Initialization {

    public static void init() {
        registerConfig();
        modSetup();
        forgeSetup();
        // if (FMLEnvironment.dist == Dist.CLIENT) ClientInitialization.init();
    }

    private static final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    private static final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

    private static void registerConfig() {}

    private static void modSetup() {
        HLPacketHandler.init();
        HLCommandArguments.init();
    }

    private static void forgeSetup() {
        HLCreativeModeTabs.init();
        forgeEventBus.register(HLServerEventHandler.class);
    }

}
