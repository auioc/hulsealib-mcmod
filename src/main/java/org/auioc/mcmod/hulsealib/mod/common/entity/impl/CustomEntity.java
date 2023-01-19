package org.auioc.mcmod.hulsealib.mod.common.entity.impl;

import org.auioc.mcmod.arnicalib.game.entity.HEntityDataSerializers;
import org.auioc.mcmod.arnicalib.game.nbt.NbtUtils;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.impl.CustomBlockBlockEntity;
import com.mojang.math.Vector3f;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class CustomEntity extends Entity {

    public static final String DEFAULT_MODEL_ID = CustomBlockBlockEntity.DEFAULT_MODEL_ID;
    public static final Vector3f DEFAULT_MODEL_SCALE = new Vector3f(1.0F, 1.0F, 1.0F);
    public static final Vector3f DEFAULT_MODEL_TRANSLATION = new Vector3f(0.0F, 0.0F, 0.0F);
    public static final Vector3f DEFAULT_MODEL_ROTATION = new Vector3f(0.0F, 0.0F, 0.0F);

    private static final String NBT_MODEL_ID = "ModelId";
    private static final String NBT_MODEL_SCALE = "ModelScale";
    private static final String NBT_MODEL_TRANSLATION = "ModelTranslation";
    private static final String NBT_MODEL_ROTATION = "ModelRotation";

    private static final EntityDataAccessor<String> MODEL_ID = SynchedEntityData.defineId(CustomEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Vector3f> MODEL_SCALE = SynchedEntityData.defineId(CustomEntity.class, HEntityDataSerializers.VECTOR3F);
    private static final EntityDataAccessor<Vector3f> MODEL_TRANSLATION = SynchedEntityData.defineId(CustomEntity.class, HEntityDataSerializers.VECTOR3F);
    private static final EntityDataAccessor<Vector3f> MODEL_ROTATION = SynchedEntityData.defineId(CustomEntity.class, HEntityDataSerializers.VECTOR3F);

    public CustomEntity(EntityType<? extends CustomEntity> type, Level level) {
        super(type, level);
    }

    // public CustomEntity(Level level) { super(HLEntities.CUSTOM_ENTITY.get(), level); }

    // ====================================================================== //

    public String getModelId() {
        return this.entityData.get(MODEL_ID);
    }

    public void setModelId(String modelId) {
        this.entityData.set(MODEL_ID, modelId);
    }

    public Vector3f getModelScale() {
        return this.entityData.get(MODEL_SCALE);
    }

    public void setModelScale(Vector3f scale) {
        this.entityData.set(MODEL_SCALE, scale);
    }

    public Vector3f getModelTranslation() {
        return this.entityData.get(MODEL_TRANSLATION);
    }

    public void setModelTranslation(Vector3f translation) {
        this.entityData.set(MODEL_TRANSLATION, translation);
    }

    public Vector3f getModelRotation() {
        return this.entityData.get(MODEL_ROTATION);
    }

    public void setModelRotation(Vector3f rotation) {
        this.entityData.set(MODEL_ROTATION, rotation);
    }

    // ====================================================================== //

    @Override
    protected void defineSynchedData() {
        this.entityData.define(MODEL_ID, DEFAULT_MODEL_ID);
        this.entityData.define(MODEL_SCALE, DEFAULT_MODEL_SCALE);
        this.entityData.define(MODEL_ROTATION, DEFAULT_MODEL_ROTATION);
        this.entityData.define(MODEL_TRANSLATION, DEFAULT_MODEL_TRANSLATION);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        if (nbt.contains(NBT_MODEL_ID, Tag.TAG_STRING)) setModelId(nbt.getString(NBT_MODEL_ID));
        setModelScale(NbtUtils.getVector3fOrElse(nbt, NBT_MODEL_SCALE, DEFAULT_MODEL_SCALE));
        setModelRotation(NbtUtils.getVector3fOrElse(nbt, NBT_MODEL_ROTATION, DEFAULT_MODEL_ROTATION));
        setModelTranslation(NbtUtils.getVector3fOrElse(nbt, NBT_MODEL_TRANSLATION, DEFAULT_MODEL_TRANSLATION));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        saveData(nbt, getModelId(), getModelScale(), getModelTranslation(), getModelRotation());
    }

    public static CompoundTag saveData(CompoundTag nbt, String modelId, Vector3f scale, Vector3f translation, Vector3f modelRotation) {
        nbt.putString(NBT_MODEL_ID, modelId.toString());
        nbt.put(NBT_MODEL_SCALE, NbtUtils.writeVector3f(scale));
        nbt.put(NBT_MODEL_ROTATION, NbtUtils.writeVector3f(modelRotation));
        nbt.put(NBT_MODEL_TRANSLATION, NbtUtils.writeVector3f(translation));
        return nbt;
    }

    // ====================================================================== //

    @Override
    public Packet<?> getAddEntityPacket() { return NetworkHooks.getEntitySpawningPacket(this); }

    @Override
    public boolean isPickable() { return !this.isRemoved(); }

    @Override
    protected boolean canAddPassenger(Entity p_20354_) { return false; }

    @Override
    protected boolean canRide(Entity p_20339_) { return false; }

}
