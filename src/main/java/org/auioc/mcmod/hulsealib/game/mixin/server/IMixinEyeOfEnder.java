package org.auioc.mcmod.hulsealib.game.mixin.server;

import java.util.Random;
import java.util.function.Function;

public interface IMixinEyeOfEnder {

    void setSurvivable(boolean survivable);

    void setSurvivable(Function<Random, Boolean> survivable);

}
