package org.auioc.mcmod.hulsealib.mod.server.event;

import org.auioc.mcmod.hulsealib.game.event.server.CatMorningGiftChanceEvent;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public final class HLServerEventFactory {

    private static final IEventBus BUS = MinecraftForge.EVENT_BUS;

    public static double onCatSetMorningGiftChance(Cat cat, Player ownerPlayer) {
        var event = new CatMorningGiftChanceEvent(cat, ownerPlayer);
        BUS.post(event);
        return event.getChance();
    }

}
