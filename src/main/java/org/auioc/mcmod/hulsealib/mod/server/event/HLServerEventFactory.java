package org.auioc.mcmod.hulsealib.mod.server.event;

import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.auioc.mcmod.arnicalib.game.entity.MobStance;
import org.auioc.mcmod.hulsealib.game.event.server.CatMorningGiftEvent;
import org.auioc.mcmod.hulsealib.game.event.server.EyeOfEnderSurvivableEvent;
import org.auioc.mcmod.hulsealib.game.event.server.ItemHurtEvent;
import org.auioc.mcmod.hulsealib.game.event.server.LivingEatAddEffectEvent;
import org.auioc.mcmod.hulsealib.game.event.server.PiglinStanceEvent;
import org.auioc.mcmod.hulsealib.game.event.server.PreBowReleaseEvent;
import org.auioc.mcmod.hulsealib.game.event.server.PreCrossbowReleaseEvent;
import org.auioc.mcmod.hulsealib.game.event.server.PreFishingRodCastEvent;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public final class HLServerEventFactory {

    private static final IEventBus BUS = MinecraftForge.EVENT_BUS;

    /**
     * @see org.auioc.mcmod.hulsealib.mod.mixin.server.MixinCatRelaxOnOwnerGoal#stop
     */
    public static boolean checkCatMorningGiftCondition(Cat cat, Player owner) {
        var event = new CatMorningGiftEvent.Check(cat, owner);
        boolean canceled = BUS.post(event);
        if (canceled) {
            return false;
        }
        return event.check();
    }

    /**
     * @see org.auioc.mcmod.hulsealib.mod.mixin.server.MixinCatRelaxOnOwnerGoal#redirect_giveMorningGift_getRandomItems
     */
    public static ObjectArrayList<ItemStack> preCatMorningGift(Cat cat, Player owner, ObjectArrayList<ItemStack> loots) {
        var event = new CatMorningGiftEvent.Pre(cat, owner, loots);
        BUS.post(event);
        return event.getLoots();
    }

    /**
     * @see org.auioc.mcmod.hulsealib.mod.mixin.server.MixinEnderEyeItem#use
     */
    public static Function<RandomSource, Boolean> onEyeOfEnderSetSurvivable(ServerPlayer player, EyeOfEnder eye) {
        var event = new EyeOfEnderSurvivableEvent(player, eye);
        BUS.post(event);
        return event.getSurvivable();
    }

    /**
     * Coremod hulsealib.fishing_rod_item.use
     */
    public static PreFishingRodCastEvent preFishingRodCast(Player player, ItemStack fishingRod, int speedBonus, int luckBonus) {
        var event = new PreFishingRodCastEvent((ServerPlayer) player, fishingRod, speedBonus, luckBonus);
        BUS.post(event);
        return event;
    }

    /**
     * Coremod hulsealib.item_stack.hurt
     */
    public static int onItemHurt(ItemStack itemStack, int damage, RandomSource random, @Nullable ServerPlayer player) {
        var event = new ItemHurtEvent(itemStack, damage, random, player);
        BUS.post(event);
        return event.getDamage();
    }

    /**
     * @see org.auioc.mcmod.hulsealib.mod.mixin.server.MixinLivingEntity#addEatEffect
     */
    public static List<MobEffectInstance> onLivingEatAddEffect(LivingEntity entity, ItemStack food, List<MobEffectInstance> effects) {
        LivingEatAddEffectEvent event = new LivingEatAddEffectEvent(entity, food, effects);
        if (BUS.post(event)) {
            event.getEffects().clear();
        }
        return event.getEffects();
    }

    /**
     * @see org.auioc.mcmod.hulsealib.mod.mixin.server.MixinPiglinAi#isWearingGold
     */
    public static MobStance onPiglinChooseStanceEvent(LivingEntity target) {
        var event = new PiglinStanceEvent(target);
        BUS.post(event);
        return event.getStance();
    }

    /**
     * @see org.auioc.mcmod.hulsealib.mod.mixin.server.MixinBowItem#releaseUsing
     */
    public static void preBowRelease(ServerPlayer player, ItemStack bow, AbstractArrow arrow) {
        BUS.post(new PreBowReleaseEvent(player, bow, arrow));
    }

    /**
     * Coremod hulsealib.cross_bow_item.shoot_projectile
     */
    public static void preCrossbowRelease(LivingEntity living, ItemStack crossbow, Projectile projectile) {
        BUS.post(new PreCrossbowReleaseEvent(living, crossbow, projectile));
    }

}
