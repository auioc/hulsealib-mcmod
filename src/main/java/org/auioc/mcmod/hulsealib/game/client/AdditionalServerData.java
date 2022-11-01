package org.auioc.mcmod.hulsealib.game.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AdditionalServerData {

    private static double tps = -1.0D;
    private static double mspt = -1.0D;

    public static double getTps() {
        return tps;
    }

    public static double getMspt() {
        return mspt;
    }

    public static void setTps(double _tps) {
        tps = _tps;
    }

    public static void setMspt(double _mspt) {
        mspt = _mspt;
    }

}
