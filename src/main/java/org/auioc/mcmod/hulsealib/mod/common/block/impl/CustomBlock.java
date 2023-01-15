package org.auioc.mcmod.hulsealib.mod.common.block.impl;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CustomBlock extends Block {

    public CustomBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE));
    }

}
