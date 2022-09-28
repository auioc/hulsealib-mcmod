package org.auioc.mcmod.hulsealib.game.event.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class PistonPushableEvent extends BlockEvent {

    public PistonPushableEvent(BlockState blockState, Level level, BlockPos blockPos, Direction facing, boolean destroyBlocks, Direction direction) {
        super(level, blockPos, blockState);
    }

}
