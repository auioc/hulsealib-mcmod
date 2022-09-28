package org.auioc.mcmod.hulsealib.mixin.server;

import java.util.ArrayList;
import java.util.List;
import org.auioc.mcmod.hulsealib.mod.server.event.HLServerEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(value = LivingEntity.class)
public class MixinLivingEntity {

    /**
     * @author WakelessSloth56
     * @reason {@link org.auioc.mcmod.hulsealib.game.event.server.LivingEatAddEffectEvent}
     */
    @Overwrite()
    private void addEatEffect(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide() && stack.isEdible()) {
            List<MobEffectInstance> effects = new ArrayList<>();

            for (Pair<MobEffectInstance, Float> pair : stack.getItem().getFoodProperties(stack, entity).getEffects()) {
                if (pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
                    effects.add(new MobEffectInstance(pair.getFirst()));
                }
            }

            effects = HLServerEventFactory.onLivingEatAddEffect(entity, stack, effects);

            for (MobEffectInstance instance : effects) {
                entity.addEffect(instance);
            }
        }
    }

}
