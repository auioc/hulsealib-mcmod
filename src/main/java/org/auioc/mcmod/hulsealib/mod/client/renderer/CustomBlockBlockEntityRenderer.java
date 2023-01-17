package org.auioc.mcmod.hulsealib.mod.client.renderer;

import org.auioc.mcmod.arnicalib.game.render.RenderUtils;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.HLBlockEntities;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.impl.CustomBlockBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.model.data.EmptyModelData;

@OnlyIn(Dist.CLIENT)
public class CustomBlockBlockEntityRenderer implements BlockEntityRenderer<CustomBlockBlockEntity> {

    private final BlockRenderDispatcher blockRenderer;

    public CustomBlockBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.blockRenderer = ctx.getBlockRenderDispatcher();
    }

    @Override
    public void render(CustomBlockBlockEntity tile, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        var blockState = tile.getBlockState();
        var model = blockRenderer.getBlockModel(blockState);
        var modelData = model.getModelData(tile.getLevel(), tile.getBlockPos(), blockState, EmptyModelData.INSTANCE);

        poseStack.pushPose();
        {
            RenderUtils.scale(poseStack, tile.getScale());
            RenderUtils.translate(poseStack, new Vec3(tile.getTranslation())); // TODO arincalib
            RenderUtils.rotate(poseStack, tile.getRotation());
            RenderUtils.renderSingleBlock(blockState, model, modelData, blockRenderer, poseStack, bufferSource, combinedLight, combinedOverlay);
        }
        poseStack.popPose();
    }

    // ============================================================================================================== //

    public static void register(RegisterRenderers event) {
        event.registerBlockEntityRenderer(HLBlockEntities.CUSTOM_BLOCK_BLOCK_ENTITY.get(), CustomBlockBlockEntityRenderer::new);
    }

}
