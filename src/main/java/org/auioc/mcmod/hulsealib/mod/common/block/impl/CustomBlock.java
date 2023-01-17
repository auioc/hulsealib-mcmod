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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomBlock extends BaseEntityBlock {

    public CustomBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).noOcclusion().dynamicShape().noDrops());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return CustomBlock.getBlockEntity(level, pos).map(CustomBlockBlockEntity::getShape).orElse(Shapes.block());
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return CustomBlock.getBlockEntity(level, pos).map(CustomBlockBlockEntity::getLight).orElse(0);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomBlockBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    public static Optional<CustomBlockBlockEntity> getBlockEntity(BlockGetter level, BlockPos pos) {
        return level.getBlockEntity(pos, HLBlockEntities.CUSTOM_BLOCK_BLOCK_ENTITY.get());
    }

}
