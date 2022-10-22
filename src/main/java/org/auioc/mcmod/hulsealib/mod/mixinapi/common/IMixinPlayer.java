package org.auioc.mcmod.hulsealib.mod.mixinapi.common;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IMixinPlayer {

    @OnlyIn(Dist.CLIENT)
    void setClientRemainingFireTicks(int ticks);

}
