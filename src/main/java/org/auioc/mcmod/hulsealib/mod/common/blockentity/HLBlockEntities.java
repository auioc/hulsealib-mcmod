package org.auioc.mcmod.hulsealib.mod.common.blockentity;

import java.util.function.Supplier;
import org.auioc.mcmod.hulsealib.HulseaLib;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.impl.CustomBlockBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class HLBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, HulseaLib.MOD_ID);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String id, Supplier<? extends BlockEntityType<T>> sup) { return BLOCK_ENTITIES.register(id, sup); }

    // ============================================================================================================== //

    public final static RegistryObject<BlockEntityType<CustomBlockBlockEntity>> CUSTOM_BLOCK_BLOCK_ENTITY = register("custom_block", () -> CustomBlockBlockEntity.TYPE);

}
