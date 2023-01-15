package org.auioc.mcmod.hulsealib.mod.common.blockentity.impl;

import java.util.Optional;
import javax.annotation.Nullable;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.HLBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomBlockBlockEntity extends BlockEntity {

    @Nullable
    private String modelId;
    @Nullable
    private double[] rawShape = new double[] {0, 0, 0, 16, 8, 16};
    @Nullable
    private VoxelShape shape;

    public CustomBlockBlockEntity(BlockPos pos, BlockState state) {
        super(HLBlockEntities.CUSTOM_BLOCK_BLOCK_ENTITY.get(), pos, state);
    }

    public Optional<String> getModelId() {
        return Optional.ofNullable(modelId);
    }

    // ====================================================================== //

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        if (modelId != null) nbt.putString("ModelId", modelId.toString());
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("ModelId", 8)) modelId = nbt.getString("ModelId");
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

}
