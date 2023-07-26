package org.auioc.mcmod.hulsealib.game.event.server;

import org.auioc.mcmod.arnicalib.game.entity.MobStance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class PiglinStanceEvent extends LivingEvent {

    private MobStance stance = MobStance.DEFAULT;

    public PiglinStanceEvent(LivingEntity target) {
        super(target);
    }

    public LivingEntity getTarget() {
        return super.getEntity();
    }

    public MobStance getStance() {
        return this.stance;
    }

    public void setStance(MobStance stance) {
        this.stance = stance;
    }

}
