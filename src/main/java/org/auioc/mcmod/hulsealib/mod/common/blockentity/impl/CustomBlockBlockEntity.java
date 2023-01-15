package org.auioc.mcmod.hulsealib.mod.common.blockentity.impl;

import java.util.Optional;
import javax.annotation.Nullable;
import org.auioc.mcmod.arnicalib.game.nbt.NbtUtils;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.HLBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomBlockBlockEntity extends BlockEntity {

    @Nullable
    private String modelId;
    @Nullable
    private double[] rawShape;
    @Nullable
    private VoxelShape shape;

    public CustomBlockBlockEntity(BlockPos pos, BlockState state) {
        super(HLBlockEntities.CUSTOM_BLOCK_BLOCK_ENTITY.get(), pos, state);
    }

    public Optional<String> getModelId() {
        return Optional.ofNullable(modelId);
    }

    public VoxelShape getShape() {
        return (shape != null) ? shape : Shapes.block();
    }

    // ====================================================================== //

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        if (modelId != null) nbt.putString("ModelId", modelId.toString());
        if (isValidShape(rawShape)) nbt.put("Shape", NbtUtils.writeDoubleArray(rawShape));
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("ModelId", 8)) modelId = nbt.getString("ModelId");
        var rawShape = NbtUtils.getDoubleArray(nbt, "Shape");
        if (rawShape.length == 6) {
            this.rawShape = rawShape;
            this.shape = Block.box(rawShape[0], rawShape[1], rawShape[2], rawShape[3], rawShape[4], rawShape[5]);
        } else {
            this.rawShape = null;
            this.shape = null;
        }
    }

    // ====================================================================== //

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    // ====================================================================== //

    private static boolean isValidShape(double[] rawShape) {
        return (rawShape != null && rawShape.length == 6);
    }

}
