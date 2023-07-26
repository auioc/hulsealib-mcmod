package org.auioc.mcmod.hulsealib.mod.mixin.server;

import javax.annotation.Nullable;
import org.auioc.mcmod.hulsealib.mod.server.event.HLServerEventFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;

@Mixin(targets = "net.minecraft.world.entity.animal.Cat$CatRelaxOnOwnerGoal")
public abstract class MixinCatRelaxOnOwnerGoal {

    @Shadow
    @Final
    private Cat cat;

    @Nullable
    @Shadow
    private Player ownerPlayer;

    @Shadow
    private int onBedTicks;

    @Shadow
    private void giveMorningGift() {}

    // ====================================================================== //

    /**
     * @author WakelessSloth56
     * @reason {@link org.auioc.mcmod.hulsealib.mod.server.event.HLServerEventFactory#checkCatMorningGiftCondition}
     */
    @Overwrite // TODO better solution than overwrite?
    public void stop() {
        this.cat.setLying(false);
        if (HLServerEventFactory.checkCatMorningGiftCondition(cat, ownerPlayer)) {
            this.giveMorningGift();
        }

        this.onBedTicks = 0;
        this.cat.setRelaxStateOne(false);
        this.cat.getNavigation().stop();
    }

    @Redirect(
        method = "Lnet/minecraft/world/entity/animal/Cat$CatRelaxOnOwnerGoal;giveMorningGift()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;"
        ),
        require = 1,
        allow = 1
    )
    private ObjectArrayList<ItemStack> redirect_giveMorningGift_getRandomItems(LootTable lootTable, LootParams params) {
        var loots = lootTable.getRandomItems(params);
        HLServerEventFactory.preCatMorningGift(cat, ownerPlayer, loots);
        return loots;
    }

}
