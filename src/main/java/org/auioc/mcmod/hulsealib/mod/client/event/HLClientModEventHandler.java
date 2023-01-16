package org.auioc.mcmod.hulsealib.mod.client.event;

import org.auioc.mcmod.hulsealib.mod.client.model.CustomBlockBakedModel;
import org.auioc.mcmod.hulsealib.mod.client.renderer.CustomBlockBlockEntityRenderer;
import org.auioc.mcmod.hulsealib.mod.client.resource.CustomBlockModelReloadListener;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@OnlyIn(Dist.CLIENT)
public final class HLClientModEventHandler {

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        CustomBlockBakedModel.register(event);
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ModelBakery modelBakery = ForgeModelBakery.instance();
        ResourceManager resourceManager = ObfuscationReflectionHelper.getPrivateValue(ModelBakery.class, modelBakery, "f_119243_");
        {
            CustomBlockModelReloadListener.apply(resourceManager);
        }
    }

    @SubscribeEvent
    public static void onRendererRegister(RegisterRenderers event) {
        CustomBlockBlockEntityRenderer.register(event);
    }

}
