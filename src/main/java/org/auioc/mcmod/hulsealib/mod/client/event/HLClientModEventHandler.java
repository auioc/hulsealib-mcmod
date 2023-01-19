package org.auioc.mcmod.hulsealib.mod.client.event;

import org.auioc.mcmod.hulsealib.mod.client.model.CustomBlockBakedModel;
import org.auioc.mcmod.hulsealib.mod.client.renderer.CustomBlockBlockEntityRenderer;
import org.auioc.mcmod.hulsealib.mod.client.renderer.CustomEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public final class HLClientModEventHandler {

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        CustomBlockBakedModel.register(event);
    }

    @SubscribeEvent
    public static void onRendererRegister(RegisterRenderers event) {
        CustomBlockBlockEntityRenderer.register(event);
        CustomEntityRenderer.register(event);
    }

}
