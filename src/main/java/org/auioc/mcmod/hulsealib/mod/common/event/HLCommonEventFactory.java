package org.auioc.mcmod.hulsealib.mod.common.event;

import org.auioc.mcmod.hulsealib.game.event.common.LivingEatEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public final class HLCommonEventFactory {

    private static final IEventBus BUS = MinecraftForge.EVENT_BUS;

    public static LivingEatEvent onLivingEat(LivingEntity living, FoodData foodData, ItemStack foodItemStack) {
        var event = new LivingEatEvent(living, foodData, foodItemStack);
        BUS.post(event);
        return event;
    }

}
