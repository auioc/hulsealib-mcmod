package org.auioc.mcmod.hulsealib.mod.common.block;

import java.util.function.Supplier;
import org.auioc.mcmod.hulsealib.HulseaLib;
import org.auioc.mcmod.hulsealib.mod.common.block.impl.CustomBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class HLBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HulseaLib.MOD_ID);

    private static RegistryObject<Block> register(String id, Supplier<? extends Block> sup) { return BLOCKS.register(id, sup); }

    // ============================================================================================================== //

    public final static RegistryObject<Block> CUSTOM_BLOCK = register("custom_block", CustomBlock::new);

}
