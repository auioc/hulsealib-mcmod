package org.auioc.mcmod.hulsealib.mod.client.gui.screen;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.hulsealib.HulseaLib;
import org.auioc.mcmod.hulsealib.mod.api.ICustomEntity;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomEntityScreen<T extends Entity & ICustomEntity> extends CustomModelObjectScreen<T> {

    private static final Component TITLE = i18n("title");
    private static final Component LABEL_KILL = i18n("kill");

    public CustomEntityScreen(T tile) {
        super(TITLE, tile);
    }

    @Override
    protected void initAdditional() {
        renderableWidget(
            new Button(
                col5, row4W, WIDGET_WIDTH_1_3, WIDGET_HEIGHT, LABEL_KILL,
                (b) -> {
                    closeScreen();
                    this.minecraft.player.chat(String.format("/kill %s", this.cmObj.getDataAccessorSelector()));
                }
            )
        );
    }


    // ====================================================================== //

    // ====================================================================== //

    private static MutableComponent i18n(String key) {
        return TextUtils.translatable(HulseaLib.i18n("gui.custom_entity." + key));
    }

}
