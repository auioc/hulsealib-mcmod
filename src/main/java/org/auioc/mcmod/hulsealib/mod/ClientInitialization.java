package org.auioc.mcmod.hulsealib.mod;

import org.auioc.mcmod.hulsealib.mod.client.event.HLClientModEventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused")
public final class ClientInitialization {

    public static void init() {
        registerConfig();
        modSetup();
        forgeSetup();
    }

    private static final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    private static final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

    private static void registerConfig() {}

    private static void modSetup() {
        modEventBus.register(HLClientModEventHandler.class);
    }

    private static void forgeSetup() {}


}
