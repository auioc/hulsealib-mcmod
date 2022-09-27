package org.auioc.mcmod.hulsealib.mixin.common;

import org.auioc.mcmod.hulsealib.mixin.common.api.IMixinCreativeModeTab;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.world.item.CreativeModeTab;

@Mixin(value = CreativeModeTab.class)
public class MixinCreativeModeTab implements IMixinCreativeModeTab {

    @Shadow
    @Final
    private String langId;

    @Override
    public String getLangId() {
        return this.langId;
    }

}
