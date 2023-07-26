package org.auioc.mcmod.hulsealib.game.event.server;

import java.util.function.Function;
import org.auioc.mcmod.arnicalib.game.event.server.ServerPlayerEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.EyeOfEnder;

public class EyeOfEnderSurvivableEvent extends ServerPlayerEvent {

    private final EyeOfEnder eye;
    private Function<RandomSource, Boolean> survivable;

    public EyeOfEnderSurvivableEvent(ServerPlayer player, EyeOfEnder eye) {
        super(player);
        this.eye = eye;
        this.survivable = (random) -> (random.nextInt(5) > 0);
    }

    public EyeOfEnder getEye() {
        return this.eye;
    }

    public Function<RandomSource, Boolean> getSurvivable() {
        return this.survivable;
    }

    public void setSurvivable(Function<RandomSource, Boolean> survivable) {
        this.survivable = survivable;
    }

    public void setSurvivable(boolean survivable) {
        this.survivable = (random) -> survivable;
    }

}
