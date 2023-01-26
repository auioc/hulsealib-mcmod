package org.auioc.mcmod.hulsealib.mod.mixin.common;

import org.auioc.mcmod.hulsealib.mod.common.event.HLCommonEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(value = ItemStack.class)
public class MixinItemStack {

    @Inject(
        method = "Lnet/minecraft/world/item/ItemStack;inventoryTick(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;IZ)V",
        at = @At(value = "HEAD"),
        require = 1,
        allow = 1,
        cancellable = true
    )
    private void onInventoryTick(Level p_41667_, Entity p_41668_, int p_41669_, boolean p_41670_, CallbackInfo ci) {
        if (p_41668_ instanceof Player player) {
            if (HLCommonEventFactory.onItemInventoryTick(player, p_41667_, ((ItemStack) (Object) this), p_41669_, p_41670_)) {
                ci.cancel();
            }
        }
    }

}
