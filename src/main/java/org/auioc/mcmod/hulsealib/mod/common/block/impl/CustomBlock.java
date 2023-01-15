package org.auioc.mcmod.hulsealib.mod.common.block.impl;

import java.util.Optional;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.HLBlockEntities;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.impl.CustomBlockBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CustomBlock extends BaseEntityBlock {

    public CustomBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).noOcclusion());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomBlockBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public static Optional<CustomBlockBlockEntity> getBlockEntity(BlockGetter level, BlockPos pos) {
        return level.getBlockEntity(pos, HLBlockEntities.CUSTOM_BLOCK_BLOCK_ENTITY.get());
    }

}
