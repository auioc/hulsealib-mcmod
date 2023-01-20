package org.auioc.mcmod.hulsealib.mod.common.blockentity.impl;

import javax.annotation.Nullable;
import org.auioc.mcmod.arnicalib.game.nbt.NbtUtils;
import org.auioc.mcmod.hulsealib.mod.api.ICustomBlock;
import org.auioc.mcmod.hulsealib.mod.common.block.HLBlocks;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.HLBlockEntities;
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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomBlockBlockEntity extends BlockEntity implements ICustomBlock {

    public static final BlockEntityType<CustomBlockBlockEntity> TYPE = BlockEntityType.Builder.of(CustomBlockBlockEntity::new, HLBlocks.CUSTOM_BLOCK.get()).build(null);

    private static final String NBT_SHAPE = "Shape";
    private static final String NBT_LIGHT = "Light";

    @Nullable
    private String modelId;
    @Nullable
    private Vector3f modelScale;
    @Nullable
    private Vector3f modelTranslation;
    @Nullable
    private Vector3f modelRotation;
    @Nullable
    private double[] rawShape;
    @Nullable
    private VoxelShape shape;
    private int light = 0;

    public CustomBlockBlockEntity(BlockPos pos, BlockState state) {
        super(HLBlockEntities.CUSTOM_BLOCK_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public String getModelId() { return (modelId != null) ? modelId : DEFAULT_MODEL_ID; }

    @Override
    public Vector3f getModelScale() { return (modelScale != null) ? modelScale : DEFAULT_MODEL_SCALE; }

    @Override
    public Vector3f getModelTranslation() { return (modelTranslation != null) ? modelTranslation : DEFAULT_MODEL_TRANSLATION; }

    @Override
    public Vector3f getModelRotation() { return (modelRotation != null) ? modelRotation : DEFAULT_MODEL_ROTATION; }

    @Override
    public VoxelShape getShape() { return (shape != null) ? shape : DEFAULT_SHAPE; }

    @Override
    public double[] getRawShape() { return (rawShape != null) ? rawShape : DEFAULT_RAW_SHAPE; }

    @Override
    public int getLight() { return light; }

    // ====================================================================== //

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        writeModelData(nbt);
        writeAdditionalData(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        var rawShape = NbtUtils.getDoubleArray(nbt, NBT_SHAPE);
        this.light = Mth.clamp(nbt.getInt(NBT_LIGHT), 0, 15);
        if (nbt.contains(NBT_MODEL_ID, Tag.TAG_STRING)) this.modelId = nbt.getString(NBT_MODEL_ID);
        if (rawShape.length == 6) {
            this.rawShape = rawShape;
            this.shape = Block.box(rawShape[0], rawShape[1], rawShape[2], rawShape[3], rawShape[4], rawShape[5]);
        } else {
            this.rawShape = DEFAULT_RAW_SHAPE;
            this.shape = DEFAULT_SHAPE;
        }
        this.modelScale = NbtUtils.getVector3fOrElse(nbt, NBT_MODEL_SCALE, DEFAULT_MODEL_SCALE);
        this.modelTranslation = NbtUtils.getVector3fOrElse(nbt, NBT_MODEL_TRANSLATION, DEFAULT_MODEL_TRANSLATION);
        this.modelRotation = NbtUtils.getVector3fOrElse(nbt, NBT_MODEL_ROTATION, DEFAULT_MODEL_ROTATION);

    }

    // ====================================================================== //

    @Override
    public String getDataAccessorSelector() { return String.format("%d %d %d", worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()); }

    // ====================================================================== //

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }

    @Override
    public CompoundTag getUpdateTag() { return this.saveWithoutMetadata(); }

}
