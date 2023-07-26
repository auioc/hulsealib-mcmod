package org.auioc.mcmod.hulsealib;

import org.apache.logging.log4j.Logger;
import org.auioc.mcmod.arnicalib.base.log.LogUtil;
import org.auioc.mcmod.arnicalib.game.mod.HModUtil;
import org.auioc.mcmod.hulsealib.mod.Initialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod(HulseaLib.MOD_ID)
public final class HulseaLib {

    public static final String MOD_ID = "hulsealib";
    public static final String MOD_NAME = "HulseaLib";
    public static final String MAIN_VERSION;
    public static final String FULL_VERSION;

    public static final Logger LOGGER = LogUtil.getLogger(MOD_NAME);

    public HulseaLib() {
        Initialization.init();
    }

    static {
        final var version = HModUtil.getVersion(HulseaLib.class, LOGGER);
        MAIN_VERSION = version.main;
        FULL_VERSION = version.full;
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static String i18n(String key) {
        return MOD_ID + "." + key;
    }

}
