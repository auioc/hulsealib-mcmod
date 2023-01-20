package org.auioc.mcmod.hulsealib.mod.api;

import org.auioc.mcmod.arnicalib.game.nbt.NbtUtils;
import com.mojang.math.Vector3f;
import net.minecraft.nbt.CompoundTag;

public interface ICustomModelObject {

    String DEFAULT_MODEL_ID = "hulsealib:custom_block#";
    Vector3f DEFAULT_MODEL_TRANSLATION = new Vector3f(0.0F, 0.0F, 0.0F);
    Vector3f DEFAULT_MODEL_ROTATION = new Vector3f(0.0F, 0.0F, 0.0F);
    Vector3f DEFAULT_MODEL_SCALE = new Vector3f(1.0F, 1.0F, 1.0F);

    String NBT_MODEL_ID = "ModelId";
    String NBT_MODEL_SCALE = "ModelScale";
    String NBT_MODEL_TRANSLATION = "ModelTranslation";
    String NBT_MODEL_ROTATION = "ModelRotation";

    Type getDataAccessorType();

    String getDataAccessorSelector();

    String getModelId();

    Vector3f getModelScale();

    Vector3f getModelTranslation();

    Vector3f getModelRotation();

    default void writeModelData(CompoundTag nbt) {
        writeModelData(nbt, getModelId(), getModelScale(), getModelRotation(), getModelTranslation());
    }

    static void writeModelData(CompoundTag nbt, String id, Vector3f scale, Vector3f rotation, Vector3f translation) {
        if (id != null) nbt.putString(NBT_MODEL_ID, id.toString());
        if (scale != null) nbt.put(NBT_MODEL_SCALE, NbtUtils.writeVector3f(scale));
        if (rotation != null) nbt.put(NBT_MODEL_ROTATION, NbtUtils.writeVector3f(rotation));
        if (translation != null) nbt.put(NBT_MODEL_TRANSLATION, NbtUtils.writeVector3f(translation));
    }

    // ====================================================================== //

    ICustomModelObject DEFAULT = new ICustomModelObject() {
        @Override
        public Type getDataAccessorType() { throw new UnsupportedOperationException(); }

        @Override
        public String getDataAccessorSelector() { throw new UnsupportedOperationException(); }

        @Override
        public String getModelId() { return DEFAULT_MODEL_ID; }

        @Override
        public Vector3f getModelScale() { return DEFAULT_MODEL_SCALE; }

        @Override
        public Vector3f getModelTranslation() { return DEFAULT_MODEL_TRANSLATION; }

        @Override
        public Vector3f getModelRotation() { return DEFAULT_MODEL_ROTATION; }
    };

    // ============================================================================================================== //

    public static enum Type {
        BLOCK("block"), ENTITY("entity");

        private final String name;

        Type(String name) { this.name = name; }

        public String getName() { return this.name; }
    }

}
