package org.auioc.mcmod.hulsealib.mod.mixinapi.server;

import java.util.function.Function;
import net.minecraft.util.RandomSource;

public interface IMixinEyeOfEnder {

    void setSurvivable(boolean survivable);

    void setSurvivable(Function<RandomSource, Boolean> survivable);

}
