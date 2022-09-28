package org.auioc.mcmod.hulsealib.mod.mixinapi.server;

import java.util.Random;
import java.util.function.Function;

public interface IMixinEyeOfEnder {

    void setSurvivable(boolean survivable);

    void setSurvivable(Function<Random, Boolean> survivable);

}
