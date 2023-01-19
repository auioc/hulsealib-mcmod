package org.auioc.mcmod.hulsealib.mod.client.renderer;

import org.auioc.mcmod.arnicalib.game.render.RenderUtils;
import org.auioc.mcmod.hulsealib.mod.common.entity.HLEntities;
import org.auioc.mcmod.hulsealib.mod.common.entity.impl.CustomEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.model.data.EmptyModelData;

@OnlyIn(Dist.CLIENT)
public class CustomEntityRenderer extends EntityRenderer<CustomEntity> {

    protected CustomEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(CustomEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
        var modelId = entity.getModelId();
        // modelId = "minecraft:apple#inventory";
        var model = Minecraft.getInstance().getModelManager().getModel(
            modelId.contains("#")
                ? new ModelResourceLocation(modelId)
                : new ResourceLocation(modelId)
        );

        poseStack.pushPose();
        {
            RenderUtils.scale(poseStack, entity.getModelScale());
            RenderUtils.rotate(poseStack, entity.getModelRotation());
            RenderUtils.translate(poseStack, entity.getModelTranslation());
            RenderUtils.renderSingleBlock(null, model, EmptyModelData.INSTANCE, Minecraft.getInstance().getBlockRenderer(), RenderType.cutoutMipped(), poseStack, bufferSource, combinedLight, combinedLight);
        }
        poseStack.popPose();
    }


    @Override
    public ResourceLocation getTextureLocation(CustomEntity entity) { throw new UnsupportedOperationException(); }

    // ============================================================================================================== //

    public static void register(RegisterRenderers event) {
        event.registerEntityRenderer(HLEntities.CUSTOM_ENTITY.get(), CustomEntityRenderer::new);
    }

}
