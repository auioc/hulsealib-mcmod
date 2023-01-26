package org.auioc.mcmod.hulsealib.mod;

import org.auioc.mcmod.hulsealib.game.command.HLCommandArguments;
import org.auioc.mcmod.hulsealib.mod.client.ClientInitialization;
import org.auioc.mcmod.hulsealib.mod.common.block.HLBlocks;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.HLBlockEntities;
import org.auioc.mcmod.hulsealib.mod.common.entity.HLEntities;
import org.auioc.mcmod.hulsealib.mod.common.itemgroup.HLCreativeModeTabs;
import org.auioc.mcmod.hulsealib.mod.common.network.HLPacketHandler;
import org.auioc.mcmod.hulsealib.mod.server.event.HLServerEventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

public final class Initialization {

    public static void init() {
        registerConfig();
        modSetup();
        forgeSetup();
        if (FMLEnvironment.dist == Dist.CLIENT) ClientInitialization.init();
    }

    private static final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    private static final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

    private static void registerConfig() {}

    private static void modSetup() {
        HLPacketHandler.init();
        HLCommandArguments.init();
        HLBlocks.BLOCKS.register(modEventBus);
        HLBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        HLEntities.ENTITY_TYPES.register(modEventBus);
    }

    private static void forgeSetup() {
        HLCreativeModeTabs.init();
        forgeEventBus.register(HLServerEventHandler.class);
    }

}
