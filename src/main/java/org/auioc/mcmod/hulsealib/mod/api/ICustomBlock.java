package org.auioc.mcmod.hulsealib.mod.api;

import org.auioc.mcmod.arnicalib.game.nbt.NbtUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface ICustomBlock extends ICustomModelObject {

    VoxelShape DEFAULT_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    double[] DEFAULT_RAW_SHAPE = new double[] {0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D};
    int DEFAULT_LIGHT = 0;

    String NBT_SHAPE = "Shape";
    String NBT_LIGHT = "Light";

    @Override
    default Type getDataAccessorType() { return Type.BLOCK; }

    double[] getRawShape();

    VoxelShape getShape();

    int getLight();

    default void writeAdditionalData(CompoundTag nbt) {
        writeAdditionalData(nbt, getRawShape(), getLight());
    }

    static void writeAdditionalData(CompoundTag nbt, double[] rawShape, int light) {
        if (rawShape != null && rawShape.length == 6) nbt.put(NBT_SHAPE, NbtUtils.writeDoubleArray(rawShape));
        nbt.putInt(NBT_LIGHT, light);
    }

}
