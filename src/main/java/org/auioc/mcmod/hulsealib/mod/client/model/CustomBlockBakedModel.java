package org.auioc.mcmod.hulsealib.mod.client.model;

import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.auioc.mcmod.hulsealib.mod.common.block.impl.CustomBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;

@OnlyIn(Dist.CLIENT)
public class CustomBlockBakedModel implements BakedModel {

    private static final Minecraft MC = Minecraft.getInstance();

    private final BakedModel defaultModel;
    private static final ModelProperty<BakedModel> MODEL = new ModelProperty<>();

    public CustomBlockBakedModel(BakedModel defaultModel) {
        this.defaultModel = defaultModel;
    }

    @Nonnull
    @Override
    public IModelData getModelData(BlockAndTintGetter level, BlockPos pos, BlockState state, IModelData modelData) {
        final var data = new ModelDataMap.Builder().withInitial(MODEL, this.defaultModel).build();
        CustomBlock.getBlockEntity(level, pos).ifPresent(
            (tile) -> {
                tile.getModelId()
                    .map((id) -> id.contains("#") ? new ModelResourceLocation(id) : new ResourceLocation(id))
                    .map((id) -> MC.getModelManager().getModel(id))
                    .ifPresent((id) -> data.setData(MODEL, id));
            }
        );
        return data;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
        return data.getData(MODEL).getQuads(state, side, rand, data);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(IModelData data) {
        var model = data.getData(MODEL);
        return (model != null) ? model.getParticleIcon(data) : getParticleIcon();
    }

    @Override
    public List<BakedQuad> getQuads(BlockState p_119123_, Direction p_119124_, Random p_119125_) {
        throw new AssertionError("BakedModel::getQuads should never be invoked, only IForgeBakedModel::getQuads");
    }

    @Override
    public boolean useAmbientOcclusion() {
        return defaultModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return defaultModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return defaultModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return defaultModel.isCustomRenderer();
    }

    @Override
    @SuppressWarnings("deprecation")
    public TextureAtlasSprite getParticleIcon() {
        return defaultModel.getParticleIcon();
    }

    @Override
    public ItemOverrides getOverrides() {
        return defaultModel.getOverrides();
    }

}
