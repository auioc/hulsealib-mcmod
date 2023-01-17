package org.auioc.mcmod.hulsealib.mod.common.blockentity.impl;

import javax.annotation.Nullable;
import org.auioc.mcmod.arnicalib.game.nbt.NbtUtils;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.HLBlockEntities;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomBlockBlockEntity extends BlockEntity {

    public static final String DEFAULT_MODEL_ID = "hulsealib:custom_block#";
    private static final VoxelShape DEFAULT_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final double[] DEFAULT_RAW_SHAPE = new double[] {0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D};
    private static final Vector3f DEFAULT_TRANSLATION = new Vector3f(0.0F, 0.0F, 0.0F);
    private static final Quaternion DEFAULT_ROTATION = new Quaternion(0.0F, 0.0F, 0.0F, true);
    private static final Vector3f DEFAULT_RAW_ROTATION = new Vector3f(0.0F, 0.0F, 0.0F);
    private static final Vector3f DEFAULT_SCALE = new Vector3f(1.0F, 1.0F, 1.0F);

    @Nullable
    private String modelId;
    @Nullable
    private double[] rawShape;
    @Nullable
    private VoxelShape shape;
    @Nullable
    private Vector3f scale;
    @Nullable
    private Vector3f translation;
    @Nullable
    private Vector3f rawRotation;
    @Nullable
    private Quaternion rotation;
    private int light = 0;

    public CustomBlockBlockEntity(BlockPos pos, BlockState state) {
        super(HLBlockEntities.CUSTOM_BLOCK_BLOCK_ENTITY.get(), pos, state);
    }

    public String getModelId() { return (modelId != null) ? modelId : DEFAULT_MODEL_ID; }

    public VoxelShape getShape() { return (shape != null) ? shape : DEFAULT_SHAPE; }

    public double[] getRawShape() { return (rawShape != null) ? rawShape : DEFAULT_RAW_SHAPE; }

    public Vector3f getScale() { return (scale != null) ? scale : DEFAULT_SCALE; }

    public Vector3f getTranslation() { return (translation != null) ? translation : DEFAULT_TRANSLATION; }

    public Quaternion getRotation() { return (rotation != null) ? rotation : DEFAULT_ROTATION; }

    public Vector3f getRawRotation() { return (rawRotation != null) ? rawRotation : DEFAULT_RAW_ROTATION; }

    public int getLight() { return light; }

    // ====================================================================== //

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        saveData(nbt, modelId, rawShape, scale, translation, rawRotation, light);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("Model", Tag.TAG_STRING)) this.modelId = nbt.getString("Model");
        var rawShape = NbtUtils.getDoubleArray(nbt, "Shape");
        if (rawShape.length == 6) {
            this.rawShape = rawShape;
            this.shape = Block.box(rawShape[0], rawShape[1], rawShape[2], rawShape[3], rawShape[4], rawShape[5]);
        } else {
            this.rawShape = DEFAULT_RAW_SHAPE;
            this.shape = DEFAULT_SHAPE;
        }
        this.scale = NbtUtils.getVector3fOrElse(nbt, "Scale", DEFAULT_SCALE);
        this.translation = NbtUtils.getVector3fOrElse(nbt, "Translation", DEFAULT_TRANSLATION);
        var rawRotation = NbtUtils.getVector3fOrElse(nbt, "Rotation", DEFAULT_RAW_ROTATION);
        this.rawRotation = rawRotation;
        this.rotation = new Quaternion(rawRotation.x(), rawRotation.y(), rawRotation.z(), true);
        this.light = Mth.clamp(nbt.getInt("Light"), 0, 15);
    }

    public static CompoundTag saveData(CompoundTag nbt, @Nullable String modelId, @Nullable double[] rawShape, @Nullable Vector3f scale, @Nullable Vector3f translation, @Nullable Vector3f rawRotation, int light) {
        if (modelId != null) nbt.putString("Model", modelId.toString());
        if (rawShape != null && rawShape.length == 6) nbt.put("Shape", NbtUtils.writeDoubleArray(rawShape));
        if (scale != null) nbt.put("Scale", NbtUtils.writeVector3f(scale));
        if (translation != null) nbt.put("Translation", NbtUtils.writeVector3f(translation));
        if (rawRotation != null) nbt.put("Rotation", NbtUtils.writeVector3f(rawRotation));
        nbt.putInt("Light", light);
        return nbt;
    }

    // ====================================================================== //

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }

    @Override
    public CompoundTag getUpdateTag() { return this.saveWithoutMetadata(); }

}
