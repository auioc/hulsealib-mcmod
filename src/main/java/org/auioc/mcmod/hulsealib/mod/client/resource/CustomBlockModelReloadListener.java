package org.auioc.mcmod.hulsealib.mod.client.resource;

import static org.auioc.mcmod.hulsealib.HulseaLib.LOGGER;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.base.log.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * This is a FAKE resource reload listener, do NOT register it!
 *
 * @see org.auioc.mcmod.hulsealib.mod.client.event.HLClientModEventHandler#onModelRegister
 */
@OnlyIn(Dist.CLIENT)
public class CustomBlockModelReloadListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    private static final Marker MARKER = LogUtil.getMarker("CustomBlockModel");


    public CustomBlockModelReloadListener() {
        super(GSON, "models/block/custom");
    }

    public List<ResourceLocation> apply(ResourceManager resourceManager, ProfilerFiller profiler) {
        var map = super.prepare(resourceManager, profiler);
        return map
            .keySet()
            .stream()
            .map((id) -> new ResourceLocation(id.getNamespace(), "block/custom/" + id.getPath()))
            .peek((id) -> LOGGER.info(MARKER, "Found custom model '{}'", id.toString()))
            .toList();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler) {}

}
