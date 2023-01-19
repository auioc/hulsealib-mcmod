package org.auioc.mcmod.hulsealib.mod.common.entity;

import java.util.function.Supplier;
import org.auioc.mcmod.hulsealib.HulseaLib;
import org.auioc.mcmod.hulsealib.mod.common.entity.impl.CustomEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class HLEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, HulseaLib.MOD_ID);

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String id, Supplier<? extends EntityType<T>> sup) { return ENTITY_TYPES.register(id, sup); }

    // ============================================================================================================== //

    public final static RegistryObject<EntityType<CustomEntity>> CUSTOM_ENTITY =
        register("custom_entity", () -> EntityType.Builder.of(CustomEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).build("custom_entity"));

}
