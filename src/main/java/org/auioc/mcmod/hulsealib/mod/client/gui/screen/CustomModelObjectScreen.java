package org.auioc.mcmod.hulsealib.mod.client.gui.screen;

import java.util.ArrayList;
import java.util.List;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.arnicalib.game.gui.screen.SimpleScreen;
import org.auioc.mcmod.arnicalib.game.resource.ResourceUtils;
import org.auioc.mcmod.hulsealib.HulseaLib;
import org.auioc.mcmod.hulsealib.mod.api.ICustomModelObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ForgeSlider;

@OnlyIn(Dist.CLIENT)
public class CustomModelObjectScreen<T extends ICustomModelObject> extends SimpleScreen {

    protected static final Component LABEL_MODEL_ID = i18n("model_id");
    protected static final Component LABEL_TRANSLATION = i18n("translation");
    protected static final Component LABEL_ROTATION = i18n("rotation");
    protected static final Component LABEL_SCALE = i18n("scale");
    protected static final Component LABEL_X = TextUtils.literal("X: ");
    protected static final Component LABEL_Y = TextUtils.literal("Y: ");
    protected static final Component LABEL_Z = TextUtils.literal("Z: ");
    protected static final Component LABEL_DEGREE = TextUtils.literal("°");
    protected static final Component LABEL_DATA_INVALID = i18n("data_invalid").withStyle(ChatFormatting.RED);
    protected static final Component LABEL_MODE = i18n("mode");
    protected static final Component LABEL_COPY_COMMAND_ONLY = i18n("copy_command_only");
    protected static final Component LABEL_MODIFY_DIRECTLY = i18n("modify_directly");
    protected static final Component LABEL_DONE = TextUtils.translatable("gui.done");
    protected static final Component LABEL_CANCEL = TextUtils.translatable("gui.cancel");
    protected static final Component LABEL_RELOAD = i18n("reload");
    protected static final Component LABEL_RESET = TextUtils.translatable("controls.reset");

    protected static final int DIV_WIDTH = 414;
    protected static final int DIV_HEIGHT = 225;
    protected static final int PADDING = 10;
    protected static final int ROW_GAP = 5;
    protected static final int COL_GAP = 2;
    protected static final int WIDGET_HEIGHT = 20;
    protected static final int LABEL_HEIGHT = 10;
    protected static final int ROW_HEIGHT = WIDGET_HEIGHT + LABEL_HEIGHT + ROW_GAP;
    protected static final int WIDGET_WIDTH_1_1 = DIV_WIDTH - (2 * PADDING);
    protected static final int WIDGET_WIDTH_1_2 = widgetWidth(2);
    protected static final int WIDGET_WIDTH_1_3 = widgetWidth(3);
    protected static final int WIDGET_WIDTH_1_6 = widgetWidth(6);

    protected int col1;
    protected int col2;
    protected int col3;
    protected int col4;
    protected int col5;
    protected int col6;
    protected int row1L;
    protected int row2L;
    protected int row3L;
    protected int row4L;
    protected int row5S;
    protected int row6S;
    protected int row7S;
    protected int row2W;
    protected int row3W;
    protected int row4W;

    protected final T cmObj;

    private final List<Label> labels = new ArrayList<>();
    private EditBox modelIdEditbox;
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

    public CustomModelObjectScreen(Component title, T customModelObject) {
        super(title, DIV_WIDTH, DIV_HEIGHT);
        this.cmObj = customModelObject;
    }

    @Override
    protected final void init() {
        super.init();

        col1 = divX + colX(1);
        col2 = divX + colX(2);
        col3 = divX + colX(3);
        col4 = divX + colX(4);
        col5 = divX + colX(5);
        col6 = divX + colX(6);
        row1L = divY + rowY(1);
        row2L = divY + rowY(2);
        row3L = divY + rowY(3);
        row4L = divY + rowY(4);
        row5S = divY + rowY(5);
        row6S = row5S + WIDGET_HEIGHT + 1;
        row7S = row6S + WIDGET_HEIGHT + 1;
        row2W = row2L + LABEL_HEIGHT;
        row3W = row3L + LABEL_HEIGHT;
        row4W = row4L + LABEL_HEIGHT;

        addLabel(LABEL_MODEL_ID, col1, row1L);
        modelIdEditbox = renderableWidget(new EditBox(font, col1, row1L + LABEL_HEIGHT, WIDGET_WIDTH_1_1, WIDGET_HEIGHT, LABEL_MODEL_ID));
        modelIdEditbox.setMaxLength(60);
        modelIdEditbox.setFilter(ResourceUtils::isValidModelId);

        addLabel(LABEL_SCALE, col1, row2L);
        scaleXSlider = renderableWidget(slider2(col1, row2W, LABEL_X));
        scaleYSlider = renderableWidget(slider2(col2, row2W, LABEL_Y));
        scaleZSlider = renderableWidget(slider2(col3, row2W, LABEL_Z));

        addLabel(LABEL_ROTATION, col4, row2L);
        rotationXSlider = renderableWidget(slider360(col4, row2W, LABEL_X));
        rotationYSlider = renderableWidget(slider360(col5, row2W, LABEL_Y));
        rotationZSlider = renderableWidget(slider360(col6, row2W, LABEL_Z));

        addLabel(LABEL_TRANSLATION, col1, row3L);
        translationXSlider = renderableWidget(slider1(col1, row3W, LABEL_X));
        translationYSlider = renderableWidget(slider1(col2, row3W, LABEL_Y));
        translationZSlider = renderableWidget(slider1(col3, row3W, LABEL_Z));

        commandPreviewBox = renderableWidget(new CommandPreviewBox(col1, row5S, WIDGET_WIDTH_1_3 * 2 + COL_GAP, (WIDGET_HEIGHT * 3) + 2));

        copyOnlyCycleButton = renderableWidget(
            CycleButton
                .booleanBuilder(LABEL_COPY_COMMAND_ONLY, LABEL_MODIFY_DIRECTLY)
                .withInitialValue(false)
                .create(col5, row5S, WIDGET_WIDTH_1_3, WIDGET_HEIGHT, LABEL_MODE)
        );

        reloadButton = renderableWidget(new Button(col5, row6S, WIDGET_WIDTH_1_6, WIDGET_HEIGHT, LABEL_RELOAD, (b) -> load()));
        resetButton = renderableWidget(new Button(col6, row6S, WIDGET_WIDTH_1_6, WIDGET_HEIGHT, LABEL_RESET, (b) -> loadDefault()));
        doneButton = renderableWidget(new Button(col5, row7S, WIDGET_WIDTH_1_6, WIDGET_HEIGHT, LABEL_DONE, (b) -> done()));
        cancelButton = renderableWidget(new Button(col6, row7S, WIDGET_WIDTH_1_6, WIDGET_HEIGHT, LABEL_CANCEL, (b) -> closeScreen()));

        subInit();

        load();
    }

    protected void subInit() {}

    @Override
    protected final void subRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        boolean dataValid = validate();
        this.commandPreviewBox.setText((dataValid) ? createCommandComponent() : LABEL_DATA_INVALID);
        for (var label : this.labels) font.draw(poseStack, label.text, label.x, label.y, 0xFF000000);
    }

    protected final void addLabel(Component text, int x, int y) {
        this.labels.add(new Label(text, x, y));
    }

    // ====================================================================== //

    private final void load(boolean loadDefault) {
        ICustomModelObject cmObj = (loadDefault) ? ICustomModelObject.DEFAULT : this.cmObj;
        modelIdEditbox.setValue(cmObj.getModelId());
        var scale = cmObj.getModelScale();
        scaleXSlider.setRealValue(scale.x());
        scaleYSlider.setRealValue(scale.y());
        scaleZSlider.setRealValue(scale.z());
        var translation = cmObj.getModelTranslation();
        translationXSlider.setRealValue(translation.x());
        translationYSlider.setRealValue(translation.y());
        translationZSlider.setRealValue(translation.z());
        var rotation = cmObj.getModelRotation();
        rotationXSlider.setRealValue(rotation.x());
        rotationYSlider.setRealValue(rotation.y());
        rotationZSlider.setRealValue(rotation.z());
        loadAdditional(loadDefault);
    }

    protected void loadAdditional(boolean loadDefault) {}

    private final void load() { load(false); }

    private final void loadDefault() { load(true); }

    // ====================================================================== //

    private final boolean validate() {
        return isSliderGt0(scaleXSlider) & isSliderGt0(scaleYSlider) & isSliderGt0(scaleZSlider)
            & validateAdditional();
    }

    protected boolean validateAdditional() { return true; }

    // ====================================================================== //

    private final void done() {
        if (validate()) {
            var commad = commandPreviewBox.getText().getString();
            if (copyOnlyCycleButton.getValue()) {
                minecraft.keyboardHandler.setClipboard(commad);
            } else {
                this.minecraft.player.chat(commad);
            }
            closeScreen();
        }
    }

    // ====================================================================== //

    private final CompoundTag createNbt() {
        var nbt = new CompoundTag();
        ICustomModelObject.writeModelData(
            nbt, modelIdEditbox.getValue(),
            new Vector3f((float) scaleXSlider.getValue(), (float) scaleYSlider.getValue(), (float) scaleZSlider.getValue()),
            new Vector3f((float) rotationXSlider.getValue(), (float) rotationYSlider.getValue(), (float) rotationZSlider.getValue()),
            new Vector3f((float) translationXSlider.getValue(), (float) translationYSlider.getValue(), (float) translationZSlider.getValue())
        );
        writeAdditionalData(nbt);
        return nbt;
    }

    protected void writeAdditionalData(CompoundTag nbt) {}

    private final Component createCommandComponent() {
        return TextUtils.empty().withStyle(ChatFormatting.WHITE)
            .append("/data merge ")
            .append(this.cmObj.getDataAccessorType().getName())
            .append(" ")
            .append(TextUtils.literal(this.cmObj.getDataAccessorSelector()).withStyle(ChatFormatting.AQUA))
            .append(" ")
            .append(((TextComponent) NbtUtils.toPrettyComponent(createNbt())).withStyle(ChatFormatting.YELLOW));
    }

    // ====================================================================== //

    private static MutableComponent i18n(String key) {
        return TextUtils.translatable(HulseaLib.i18n("gui.custom_model." + key));
    }

    protected static int widgetWidth(int d) {
        return (WIDGET_WIDTH_1_1 - COL_GAP * (d - 1)) / d;
    }

    protected static int rowY(int row) {
        return PADDING / 2 + PADDING + (ROW_HEIGHT * (row - 1));
    }

    protected static int colX(int col) {
        return PADDING / 2 + PADDING + (COL_GAP * (col - 1)) + (WIDGET_WIDTH_1_6 * (col - 1));
    }


    protected static Slider slider15(int x, int y) {
        return new Slider(x, y, WIDGET_WIDTH_1_2, WIDGET_HEIGHT, TextUtils.empty(), TextUtils.empty(), 0.0D, 15.0D);
    }

    protected static Slider slider61(int x, int y, Component prefix, double min, double max, double step) {
        return new Slider(x, y, WIDGET_WIDTH_1_6, WIDGET_HEIGHT, prefix, TextUtils.empty(), min, max, step);
    }

    protected static Slider slider16(int x, int y, Component prefix) {
        return slider61(x, y, prefix, 0.0D, 16.0D, 0.25D);
    }

    protected static Slider slider1(int x, int y, Component prefix) {
        return slider61(x, y, prefix, -1.0D, 1.0D, 0.01D);
    }

    protected static Slider slider2(int x, int y, Component prefix) {
        return slider61(x, y, prefix, 0.0D, 2.0D, 0.01D);
    }

    protected static Slider slider360(int x, int y, Component prefix) {
        return new Slider(x, y, WIDGET_WIDTH_1_6, WIDGET_HEIGHT, prefix, LABEL_DEGREE, -360.0D, 360.0D);
    }

    protected static boolean isSliderGt0(Slider a, Slider b) {
        if (a.getValue() - b.getValue() <= 0) {
            a.setFGColor(0xFFFF5555);
            b.setFGColor(0xFFFF5555);
            return false;
        }
        a.clearFGColor();
        b.clearFGColor();
        return true;
    }

    protected static boolean isSliderGt0(Slider a) {
        if (a.getValue() <= 0) {
            a.setFGColor(0xFFFF5555);
            return false;
        }
        a.clearFGColor();
        return true;
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    private record Label(Component text, int x, int y) {}

    // ====================================================================== //

    @OnlyIn(Dist.CLIENT)
    protected static class Slider extends ForgeSlider {

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

    // ====================================================================== //

    @OnlyIn(Dist.CLIENT)
    private class CommandPreviewBox extends AbstractWidget {

        private Component text = TextUtils.empty();

        public CommandPreviewBox(int x, int y, int w, int h) {
            super(x, y, w, h, TextUtils.literal(CommandPreviewBox.class.getSimpleName()));
        }

        public void setText(Component text) { this.text = text; }

        public Component getText() { return this.text; }

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
