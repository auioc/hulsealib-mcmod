package org.auioc.mcmod.hulsealib.mod.server.event;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.auioc.mcmod.arnicalib.game.entity.MobStance;
import org.auioc.mcmod.hulsealib.game.event.server.CatMorningGiftChanceEvent;
import org.auioc.mcmod.hulsealib.game.event.server.EyeOfEnderSurvivableEvent;
import org.auioc.mcmod.hulsealib.game.event.server.ItemHurtEvent;
import org.auioc.mcmod.hulsealib.game.event.server.LivingEatAddEffectEvent;
import org.auioc.mcmod.hulsealib.game.event.server.PiglinStanceEvent;
import org.auioc.mcmod.hulsealib.game.event.server.PreBowReleaseEvent;
import org.auioc.mcmod.hulsealib.game.event.server.PreCrossbowReleaseEvent;
import org.auioc.mcmod.hulsealib.game.event.server.PreFishingRodCastEvent;
import org.auioc.mcmod.hulsealib.game.event.server.ServerPlayerSendMessageEvent;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
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

    public static double onCatSetMorningGiftChance(Cat cat, Player ownerPlayer) {
        var event = new CatMorningGiftChanceEvent(cat, ownerPlayer);
        BUS.post(event);
        return event.getChance();
    }

    public static Function<Random, Boolean> onEyeOfEnderSetSurvivable(ServerPlayer player, EyeOfEnder eye) {
        var event = new EyeOfEnderSurvivableEvent(player, eye);
        BUS.post(event);
        return event.getSurvivable();
    }

    // Coremod hulsealib.fishing_rod_item.use
    public static PreFishingRodCastEvent preFishingRodCast(Player player, ItemStack fishingRod, int speedBonus, int luckBonus) {
        var event = new PreFishingRodCastEvent((ServerPlayer) player, fishingRod, speedBonus, luckBonus);
        BUS.post(event);
        return event;
    }

    // Coremod hulsealib.item_stack.hurt
    public static int onItemHurt(ItemStack itemStack, int damage, Random random, @Nullable ServerPlayer player) {
        var event = new ItemHurtEvent(itemStack, damage, random, player);
        BUS.post(event);
        return event.getDamage();
    }

    public static List<MobEffectInstance> onLivingEatAddEffect(LivingEntity entity, ItemStack food, List<MobEffectInstance> effects) {
        LivingEatAddEffectEvent event = new LivingEatAddEffectEvent(entity, food, effects);
        if (BUS.post(event)) {
            event.getEffects().clear();
        }
        return event.getEffects();
    }

    public static MobStance onPiglinChooseStanceEvent(LivingEntity target) {
        var event = new PiglinStanceEvent(target);
        BUS.post(event);
        return event.getStance();
    }

    public static boolean onServerPlayerSendMessage(ServerPlayer player, Component message, ChatType type, UUID uuid) {
        return BUS.post(new ServerPlayerSendMessageEvent(player, message, type, uuid));
    }

    public static void preBowRelease(ServerPlayer player, ItemStack bow, AbstractArrow arrow) {
        BUS.post(new PreBowReleaseEvent(player, bow, arrow));
    }

    // Coremod hulsealib.cross_bow_item.shoot_projectile
    public static void preCrossbowRelease(LivingEntity living, ItemStack crossbow, Projectile projectile) {
        BUS.post(new PreCrossbowReleaseEvent(living, crossbow, projectile));
    }

}
