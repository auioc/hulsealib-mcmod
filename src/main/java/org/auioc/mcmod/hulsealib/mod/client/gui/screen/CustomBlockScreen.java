package org.auioc.mcmod.hulsealib.mod.client.gui.screen;

import java.util.ArrayList;
import java.util.List;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.arnicalib.game.gui.screen.SimpleScreen;
import org.auioc.mcmod.hulsealib.HulseaLib;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.impl.CustomBlockBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ForgeSlider;

@OnlyIn(Dist.CLIENT)
public class CustomBlockScreen extends SimpleScreen {

    private static final Component TITLE = i18n("title");
    private static final Component LABEL_MODEL_ID = i18n("model_id");
    private static final Component LABEL_LIGHT = i18n("light");
    private static final Component LABEL_SHAPE = i18n("shape");
    private static final Component LABEL_TRANSLATION = i18n("translation");
    private static final Component LABEL_ROTATION = i18n("rotation");
    private static final Component LABEL_SCALE = i18n("scale");
    private static final Component LABEL_X = TextUtils.literal("X: ");
    private static final Component LABEL_Y = TextUtils.literal("Y: ");
    private static final Component LABEL_Z = TextUtils.literal("Z: ");
    private static final Component LABEL_DEGREE = TextUtils.literal("°");
    private static final Component LABEL_DATA_INVALID = i18n("data_invalid").withStyle(ChatFormatting.RED);
    private static final Component LABEL_MODE = i18n("mode");
    private static final Component LABEL_COPY_COMMAND_ONLY = i18n("copy_command_only");
    private static final Component LABEL_MODIFY_DIRECTLY = i18n("modify_directly");
    private static final Component LABEL_DONE = TextUtils.translatable("gui.done");
    private static final Component LABEL_CANCEL = TextUtils.translatable("gui.cancel");
    private static final Component LABEL_RELOAD = i18n("reload");
    private static final Component LABEL_RESET = TextUtils.translatable("controls.reset");

    private static final int DIV_WIDTH = 414;
    private static final int DIV_HEIGHT = 225;
    private static final int PADDING = 10;
    private static final int ROW_GAP = 5;
    private static final int COL_GAP = 2;
    private static final int WIDGET_HEIGHT = 20;
    private static final int LABEL_HEIGHT = 10;
    private static final int ROW_HEIGHT = WIDGET_HEIGHT + LABEL_HEIGHT + ROW_GAP;
    private static final int WIDGET_WIDTH_1_1 = DIV_WIDTH - (2 * PADDING);
    private static final int WIDGET_WIDTH_1_2 = widgetWidth(2);
    private static final int WIDGET_WIDTH_1_3 = widgetWidth(3);
    private static final int WIDGET_WIDTH_1_6 = widgetWidth(6);

    private final CustomBlockBlockEntity tile;
    private final BlockPos blockPos;

    private String originalCommand = "";

    private EditBox modelIdEditbox;
    private Slider lightSlider;
    private Slider shapeX1Slider;
    private Slider shapeY1Slider;
    private Slider shapeZ1Slider;
    private Slider shapeX2Slider;
    private Slider shapeY2Slider;
    private Slider shapeZ2Slider;
    private Slider translationXSlider;
    private Slider translationYSlider;
    private Slider translationZSlider;
    private Slider scaleXSlider;
    private Slider scaleYSlider;
    private Slider scaleZSlider;
    private Slider rotationXSlider;
    private Slider rotationYSlider;
    private Slider rotationZSlider;
    private CommandPreviewBox commandPreviewBox;
    private CycleButton<Boolean> copyOnlyCycleButton;
    @SuppressWarnings("unused")
    private Button doneButton;
    @SuppressWarnings("unused")
    private Button cancelButton;
    @SuppressWarnings("unused")
    private Button reloadButton;
    @SuppressWarnings("unused")
    private Button resetButton;

    private final List<Label> labels = new ArrayList<>();

    public CustomBlockScreen(CustomBlockBlockEntity tile) {
        super(TITLE, DIV_WIDTH, DIV_HEIGHT);
        this.tile = tile;
        this.blockPos = tile.getBlockPos();
    }

    @Override
    protected void init() {
        // TODO arnicalib
        final int divX = center(this.width, this.divWidth);
        final int divY = center(this.height, this.divHeight);

        final int col1 = divX + colX(1);
        final int col2 = divX + colX(2);
        final int col3 = divX + colX(3);
        final int col4 = divX + colX(4);
        final int col5 = divX + colX(5);
        final int col6 = divX + colX(6);

        final int row1L = divY + rowY(1);
        final int row2L = divY + rowY(2);
        final int row3L = divY + rowY(3);
        final int row4L = divY + rowY(4);
        final int row5S = divY + rowY(5);
        final int row6S = row5S + WIDGET_HEIGHT + 1;
        final int row7S = row6S + WIDGET_HEIGHT + 1;
        final int row2W = row2L + LABEL_HEIGHT;
        final int row3W = row3L + LABEL_HEIGHT;
        final int row4W = row4L + LABEL_HEIGHT;

        labels.add(new Label(LABEL_MODEL_ID, col1, row1L));
        modelIdEditbox = renderable(new EditBox(font, col1, row1L + LABEL_HEIGHT, WIDGET_WIDTH_1_1, WIDGET_HEIGHT, LABEL_MODEL_ID));
        modelIdEditbox.setMaxLength(60);
        modelIdEditbox.setFilter(CustomBlockScreen::isValidModelId);

        labels.add(new Label(LABEL_SHAPE, col1, row2L));
        shapeX1Slider = renderable(slider16(col1, row2W, LABEL_X));
        shapeY1Slider = renderable(slider16(col2, row2W, LABEL_Y));
        shapeZ1Slider = renderable(slider16(col3, row2W, LABEL_Z));
        shapeX2Slider = renderable(slider16(col4, row2W, LABEL_X));
        shapeY2Slider = renderable(slider16(col5, row2W, LABEL_Y));
        shapeZ2Slider = renderable(slider16(col6, row2W, LABEL_Z));

        labels.add(new Label(LABEL_SCALE, col1, row3L));
        scaleXSlider = renderable(slider2(col1, row3W, LABEL_X));
        scaleYSlider = renderable(slider2(col2, row3W, LABEL_Y));
        scaleZSlider = renderable(slider2(col3, row3W, LABEL_Z));

        labels.add(new Label(LABEL_TRANSLATION, col4, row3L));
        translationXSlider = renderable(slider1(col4, row3W, LABEL_X));
        translationYSlider = renderable(slider1(col5, row3W, LABEL_Y));
        translationZSlider = renderable(slider1(col6, row3W, LABEL_Z));

        labels.add(new Label(LABEL_ROTATION, col1, row4L));
        rotationXSlider = renderable(slider360(col1, row4W, LABEL_X));
        rotationYSlider = renderable(slider360(col2, row4W, LABEL_Y));
        rotationZSlider = renderable(slider360(col3, row4W, LABEL_Z));

        labels.add(new Label(LABEL_LIGHT, col4, row4L));
        lightSlider = renderable(slider15(col4, row4W));

        commandPreviewBox = renderable(new CommandPreviewBox(col1, row5S, colX(5) - PADDING - COL_GAP, (WIDGET_HEIGHT * 3) + 2));

        copyOnlyCycleButton = renderable(
            CycleButton
                .booleanBuilder(LABEL_COPY_COMMAND_ONLY, LABEL_MODIFY_DIRECTLY)
                .withInitialValue(false)
                .create(col5, row5S, WIDGET_WIDTH_1_3, WIDGET_HEIGHT, LABEL_MODE)
        );

        reloadButton = renderable(new Button(col5, row6S, WIDGET_WIDTH_1_6, WIDGET_HEIGHT, LABEL_RELOAD, (b) -> loadData(true)));
        resetButton = renderable(new Button(col6, row6S, WIDGET_WIDTH_1_6, WIDGET_HEIGHT, LABEL_RESET, (b) -> loadData(false)));
        doneButton = renderable(new Button(col5, row7S, WIDGET_WIDTH_1_6, WIDGET_HEIGHT, LABEL_DONE, (b) -> done()));
        cancelButton = renderable(new Button(col6, row7S, WIDGET_WIDTH_1_6, WIDGET_HEIGHT, LABEL_CANCEL, (b) -> closeScreen()));

        loadData(true);
        originalCommand = getCommandString();
    }

    @Override
    protected void subRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        boolean dataValid = validateData();
        commandPreviewBox.setText((dataValid) ? getCommandComponent() : LABEL_DATA_INVALID);
        for (var label : labels) font.draw(poseStack, label.text, label.x, label.y, 0);
    }

    // ====================================================================== //

    private void loadData(boolean useTile) {
        modelIdEditbox.setValue(useTile ? tile.getModelId() : CustomBlockBlockEntity.DEFAULT_MODEL_ID);
        var shape = useTile ? tile.getRawShape() : CustomBlockBlockEntity.DEFAULT_RAW_SHAPE;
        shapeX1Slider.setRealValue(shape[0]);
        shapeY1Slider.setRealValue(shape[1]);
        shapeZ1Slider.setRealValue(shape[2]);
        shapeX2Slider.setRealValue(shape[3]);
        shapeY2Slider.setRealValue(shape[4]);
        shapeZ2Slider.setRealValue(shape[5]);
        var scale = useTile ? tile.getScale() : CustomBlockBlockEntity.DEFAULT_SCALE;
        scaleXSlider.setRealValue(scale.x());
        scaleYSlider.setRealValue(scale.y());
        scaleZSlider.setRealValue(scale.z());
        var translation = useTile ? tile.getTranslation() : CustomBlockBlockEntity.DEFAULT_TRANSLATION;
        translationXSlider.setRealValue(translation.x());
        translationYSlider.setRealValue(translation.y());
        translationZSlider.setRealValue(translation.z());
        var rotation = useTile ? tile.getRawRotation() : CustomBlockBlockEntity.DEFAULT_RAW_ROTATION;
        rotationXSlider.setRealValue(rotation.x());
        rotationYSlider.setRealValue(rotation.y());
        rotationZSlider.setRealValue(rotation.z());
        lightSlider.setRealValue(useTile ? tile.getLight() : CustomBlockBlockEntity.DEFAULT_LIGHT);
    }

    private boolean validateData() {
        return isSliderGt0(shapeX2Slider, shapeX1Slider)
            & isSliderGt0(shapeY2Slider, shapeY1Slider)
            & isSliderGt0(shapeZ2Slider, shapeZ1Slider)
            & isSliderGt0(scaleXSlider)
            & isSliderGt0(scaleYSlider)
            & isSliderGt0(scaleZSlider);
    }

    private CompoundTag createNbt() {
        return CustomBlockBlockEntity.saveData(
            new CompoundTag(),
            modelIdEditbox.getValue(),
            new double[] {shapeX1Slider.getValue(), shapeY1Slider.getValue(), shapeZ1Slider.getValue(), shapeX2Slider.getValue(), shapeY2Slider.getValue(), shapeZ2Slider.getValue()},
            new Vector3f((float) scaleXSlider.getValue(), (float) scaleYSlider.getValue(), (float) scaleZSlider.getValue()),
            new Vector3f((float) translationXSlider.getValue(), (float) translationYSlider.getValue(), (float) translationZSlider.getValue()),
            new Vector3f((float) rotationXSlider.getValue(), (float) rotationYSlider.getValue(), (float) rotationZSlider.getValue()),
            lightSlider.getValueInt()
        );
    }

    private Component getCommandComponent() {
        return TextUtils.empty().withStyle(ChatFormatting.WHITE)
            .append("/data merge block ")
            .append(TextUtils.literal(String.format("%d %d %d ", blockPos.getX(), blockPos.getY(), blockPos.getZ())).withStyle(ChatFormatting.AQUA))
            .append(((TextComponent) NbtUtils.toPrettyComponent(createNbt())).withStyle(ChatFormatting.YELLOW));
    }

    private String getCommandString() {
        return String.format("/data merge block %d %d %d %s", blockPos.getX(), blockPos.getY(), blockPos.getZ(), createNbt().toString());
    }

    private void done() {
        if (validateData()) {
            var commad = getCommandString();
            if (copyOnlyCycleButton.getValue()) {
                minecraft.keyboardHandler.setClipboard(commad);
            } else if (!commad.equals(originalCommand)) {
                minecraft.player.chat(commad);
            }
            closeScreen();
        }
    }

    // ====================================================================== //

    private <T extends GuiEventListener & Widget & NarratableEntry> T renderable(T _widget) {
        addRenderableWidget(_widget);
        return _widget;
    }

    private static int widgetWidth(int d) {
        return (WIDGET_WIDTH_1_1 - COL_GAP * (d - 1)) / d;
    }

    private static int rowY(int row) {
        return PADDING + (ROW_HEIGHT * (row - 1));
    }

    private static int colX(int col) {
        return PADDING + (COL_GAP * (col - 1)) + (WIDGET_WIDTH_1_6 * (col - 1));
    }

    private static boolean isValidModelId(String str) {
        try {
            if (str.contains("#")) {
                new ModelResourceLocation(str);
            } else {
                new ResourceLocation(str);
            }
            return true;
        } catch (ResourceLocationException e) {
            return false;
        }
    }

    private static Slider slider15(int x, int y) {
        return new Slider(x, y, WIDGET_WIDTH_1_2, WIDGET_HEIGHT, TextUtils.empty(), TextUtils.empty(), 0.0D, 15.0D);
    }

    private static Slider slider61(int x, int y, Component prefix, double min, double max, double step) {
        return new Slider(x, y, WIDGET_WIDTH_1_6, WIDGET_HEIGHT, prefix, TextUtils.empty(), min, max, step);
    }

    private static Slider slider16(int x, int y, Component prefix) {
        return slider61(x, y, prefix, 0.0D, 16.0D, 0.25D);
    }

    private static Slider slider1(int x, int y, Component prefix) {
        return slider61(x, y, prefix, -1.0D, 1.0D, 0.01D);
    }

    private static Slider slider2(int x, int y, Component prefix) {
        return slider61(x, y, prefix, 0.0D, 2.0D, 0.01D);
    }

    private static Slider slider360(int x, int y, Component prefix) {
        return new Slider(x, y, WIDGET_WIDTH_1_6, WIDGET_HEIGHT, prefix, LABEL_DEGREE, -360.0D, 360.0D);
    }

    private static boolean isSliderGt0(Slider a, Slider b) {
        if (a.getValue() - b.getValue() <= 0) {
            a.setFGColor(0xFFFF5555);
            b.setFGColor(0xFFFF5555);
            return false;
        }
        a.clearFGColor();
        b.clearFGColor();
        return true;
    }

    private static boolean isSliderGt0(Slider a) {
        if (a.getValue() <= 0) {
            a.setFGColor(0xFFFF5555);
            return false;
        }
        a.clearFGColor();
        return true;
    }

    private static MutableComponent i18n(String key) {
        return TextUtils.translatable(HulseaLib.i18n("gui.custom_block." + key));
    }

    // ====================================================================== //

    @OnlyIn(Dist.CLIENT)
    private record Label(Component text, int x, int y) {}

    private static class Slider extends ForgeSlider {

        private boolean isEditable = true;
        private double realValue = 0.0D;

        public Slider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double stepSize) {
            super(x, y, width, height, prefix, suffix, minValue, maxValue, 0.0D, stepSize, 0, true);
        }

        public Slider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue) {
            this(x, y, width, height, prefix, suffix, minValue, maxValue, 1.0D);
        }

        public void setRealValue(double value) {
            if (value > maxValue || value < minValue) {
                isEditable = false;
                realValue = value;
                value = 0.0D;
            } else {
                isEditable = true;
            }
            super.setValue(value);
        }

        @Override
        public double getValue() {
            return (isEditable) ? super.getValue() : realValue;
        }

        @Override
        public int getFGColor() { return (isEditable) ? super.getFGColor() : 0xFFFFFF00; }

        @Override
        public void renderBg(PoseStack poseStack, Minecraft minecraft, int mouseX, int mouseY) { if (isEditable) super.renderBg(poseStack, minecraft, mouseX, mouseY); }

        @Override
        public boolean changeFocus(boolean focus) { return (isEditable) ? super.changeFocus(focus) : false; }

        @Override
        public void onClick(double mouseX, double mouseY) { if (isEditable) super.onClick(mouseX, mouseY); }

        @Override
        protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) { if (isEditable) super.onDrag(mouseX, mouseY, dragX, dragY); }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) { return (isEditable) ? super.keyPressed(keyCode, scanCode, modifiers) : false; }

    }

    @OnlyIn(Dist.CLIENT)
    private class CommandPreviewBox extends AbstractWidget {

        private Component text = TextUtils.empty();

        public CommandPreviewBox(int x, int y, int w, int h) {
            super(x, y, w, h, TextUtils.literal(CommandPreviewBox.class.getSimpleName()));
        }

        public void setText(Component text) { this.text = text; }

        @Override
        public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
            fill(poseStack, x, y, x + width, y + height, 0xFFA0A0A0);
            fill(poseStack, x + 1, y + 1, x + width - 1, y + height - 1, 0xFF000000);
            font.drawWordWrap(text, x + 3, y + 3, width - 6, height - 6);
        }

        @Override
        public boolean changeFocus(boolean focus) { return false; }

        @Override
        public void updateNarration(NarrationElementOutput p_169152_) {}

    }

}
