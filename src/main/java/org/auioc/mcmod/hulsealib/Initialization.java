package org.auioc.mcmod.hulsealib;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("unused")
public final class Initialization {

    private Initialization() {}

    public static void init() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        final ClientSideOnlySetup ClientSideOnlySetup = new ClientSideOnlySetup(modEventBus, forgeEventBus);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSideOnlySetup::registerConfig);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSideOnlySetup::modSetup);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSideOnlySetup::forgeSetup);

        final CommonSetup CommonSetup = new CommonSetup(modEventBus, forgeEventBus);
        CommonSetup.registerConfig();
        CommonSetup.modSetup();
        CommonSetup.forgeSetup();
    }


    private final static class CommonSetup {

        private final IEventBus modEventBus;
        private final IEventBus forgeEventBus;

        public CommonSetup(final IEventBus modEventBus, final IEventBus forgeEventBus) {
            this.modEventBus = modEventBus;
            this.forgeEventBus = forgeEventBus;
        }

        public void registerConfig() {}

        private void modSetup() {}

        private void forgeSetup() {

        }

    }


    private final static class ClientSideOnlySetup {

        private final IEventBus modEventBus;
        private final IEventBus forgeEventBus;

        public ClientSideOnlySetup(final IEventBus modEventBus, final IEventBus forgeEventBus) {
            this.modEventBus = modEventBus;
            this.forgeEventBus = forgeEventBus;
        }

        public void registerConfig() {}

        public void modSetup() {}

        public void forgeSetup() {}

    }

}