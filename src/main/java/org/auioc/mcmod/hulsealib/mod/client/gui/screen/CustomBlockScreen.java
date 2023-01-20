package org.auioc.mcmod.hulsealib.mod.client.gui.screen;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.hulsealib.HulseaLib;
import org.auioc.mcmod.hulsealib.mod.api.ICustomBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomBlockScreen<T extends BlockEntity & ICustomBlock> extends CustomModelObjectScreen<T> {

    private static final Component TITLE = i18n("title");
    private static final Component LABEL_LIGHT = i18n("light");
    private static final Component LABEL_SHAPE = i18n("shape");

    private Slider lightSlider;
    private Slider shapeX1Slider;
    private Slider shapeY1Slider;
    private Slider shapeZ1Slider;
    private Slider shapeX2Slider;
    private Slider shapeY2Slider;
    private Slider shapeZ2Slider;

    public CustomBlockScreen(T tile) {
        super(TITLE, tile);
    }

    @Override
    protected void initAdditional() {
        addLabel(LABEL_SHAPE, col1, row4L);
        shapeX1Slider = renderableWidget(slider16(col1, row4W, LABEL_X));
        shapeY1Slider = renderableWidget(slider16(col2, row4W, LABEL_Y));
        shapeZ1Slider = renderableWidget(slider16(col3, row4W, LABEL_Z));
        shapeX2Slider = renderableWidget(slider16(col4, row4W, LABEL_X));
        shapeY2Slider = renderableWidget(slider16(col5, row4W, LABEL_Y));
        shapeZ2Slider = renderableWidget(slider16(col6, row4W, LABEL_Z));

        addLabel(LABEL_LIGHT, col4, row3L);
        lightSlider = renderableWidget(slider15(col4, row3W));
    }

    // ====================================================================== //

    @Override
    protected void loadAdditional(boolean useDefault) {
        var shape = useDefault ? ICustomBlock.DEFAULT_RAW_SHAPE : this.cmObj.getRawShape();
        shapeX1Slider.setRealValue(shape[0]);
        shapeY1Slider.setRealValue(shape[1]);
        shapeZ1Slider.setRealValue(shape[2]);
        shapeX2Slider.setRealValue(shape[3]);
        shapeY2Slider.setRealValue(shape[4]);
        shapeZ2Slider.setRealValue(shape[5]);
        lightSlider.setRealValue(useDefault ? ICustomBlock.DEFAULT_LIGHT : this.cmObj.getLight());
    }

    @Override
    protected boolean validateAdditional() {
        return isSliderGt0(shapeX2Slider, shapeX1Slider)
            & isSliderGt0(shapeY2Slider, shapeY1Slider)
            & isSliderGt0(shapeZ2Slider, shapeZ1Slider);
    }

    @Override
    protected void writeAdditionalData(CompoundTag nbt) {
        ICustomBlock.writeAdditionalData(
            nbt,
            new double[] {shapeX1Slider.getValue(), shapeY1Slider.getValue(), shapeZ1Slider.getValue(), shapeX2Slider.getValue(), shapeY2Slider.getValue(), shapeZ2Slider.getValue()},
            lightSlider.getValueInt()
        );
    }

    // ====================================================================== //

    private static MutableComponent i18n(String key) {
        return TextUtils.translatable(HulseaLib.i18n("gui.custom_block." + key));
    }

}
