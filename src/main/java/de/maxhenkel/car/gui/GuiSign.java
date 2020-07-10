package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import de.maxhenkel.car.net.MessageEditSign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

public class GuiSign extends GuiBase<ContainerSign> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_sign.png");

    private TileEntitySign sign;

    protected int guiLeft;
    protected int guiTop;

    protected Button buttonSwitch;
    protected Button buttonSubmit;
    protected Button buttonCancel;

    protected TextFieldWidget text1;
    protected TextFieldWidget text2;
    protected TextFieldWidget text3;
    protected TextFieldWidget text4;

    protected String[] text;

    protected boolean front = true;

    public GuiSign(ContainerSign containerSign, ITextComponent title) {
        super(GUI_TEXTURE, containerSign, null, title);
        this.sign = containerSign.getSign();
        this.xSize = 176;
        this.ySize = 142;
        this.text = sign.getSignText();
    }

    @Override
    protected void func_231160_c_() {
        super.func_231160_c_();

        guiLeft = (field_230708_k_ - xSize) / 2;
        guiTop = (field_230709_l_ - ySize) / 2;

        field_230706_i_.keyboardListener.enableRepeatEvents(true);

        text1 = initTextField(0, 0);
        text2 = initTextField(1, 20);
        text3 = initTextField(2, 40);
        text4 = initTextField(3, 60);

        setFocusedDefault(text1);

        buttonSubmit = func_230480_a_(new Button(guiLeft + 20, guiTop + ySize - 25, 50, 20, new TranslationTextComponent("button.car.submit"), button -> {
            save();
            Main.SIMPLE_CHANNEL.sendToServer(new MessageEditSign(sign.getPos(), text));
            Minecraft.getInstance().displayGuiScreen(null);
        }));
        buttonCancel = func_230480_a_(new Button(guiLeft + xSize - 50 - 15, guiTop + ySize - 25, 50, 20, new TranslationTextComponent("button.car.cancel"), button -> {
            Minecraft.getInstance().displayGuiScreen(null);
        }));
        buttonSwitch = func_230480_a_(new Button(guiLeft + 5, guiTop + 49 + 10, 46, 20, new TranslationTextComponent("button.car.back"), button -> {
            save();
            front = !front;

            if (front) {
                text1.setText(text[0]);
                text2.setText(text[1]);
                text3.setText(text[2]);
                text4.setText(text[3]);
                buttonSwitch.func_238482_a_(new TranslationTextComponent("button.car.back"));
            } else {
                text1.setText(text[4]);
                text2.setText(text[5]);
                text3.setText(text[6]);
                text4.setText(text[7]);
                buttonSwitch.func_238482_a_(new TranslationTextComponent("button.car.front"));
            }
        }));
    }

    private TextFieldWidget initTextField(int id, int height) {
        TextFieldWidget field = new TextFieldWidget(field_230712_o_, guiLeft + 54, guiTop + 30 + height, 114, 16, new StringTextComponent(""));
        field.setTextColor(-1);
        field.setDisabledTextColour(-1);
        field.setEnableBackgroundDrawing(true);
        field.setMaxStringLength(20);
        field.setText(text[id]);
        func_230480_a_(field);
        return field;
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.func_230451_b_(matrixStack, mouseX, mouseY);

        String s;
        if (front) {
            s = new TranslationTextComponent("gui.sign.front").getString();
        } else {
            s = new TranslationTextComponent("gui.sign.back").getString();
        }

        field_230712_o_.func_238422_b_(matrixStack, new TranslationTextComponent("gui.sign", s), 54, 10, FONT_COLOR);
    }

    public void func_231164_f_() {
        super.func_231164_f_();
        field_230706_i_.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public boolean func_231046_a_(int key, int a, int b) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            field_230706_i_.player.closeScreen();
            return true;
        }

        return text1.func_231046_a_(key, a, b) ||
                text1.canWrite() ||
                text2.func_231046_a_(key, a, b) ||
                text2.canWrite() ||
                text3.func_231046_a_(key, a, b) ||
                text3.canWrite() ||
                text4.func_231046_a_(key, a, b) ||
                text4.canWrite() || super.func_231046_a_(key, a, b);
    }

    private void save() {
        if (front) {
            text[0] = text1.getText();
            text[1] = text2.getText();
            text[2] = text3.getText();
            text[3] = text4.getText();
        } else {
            text[4] = text1.getText();
            text[5] = text2.getText();
            text[6] = text3.getText();
            text[7] = text4.getText();
        }
    }

    @Override
    public void func_231152_a_(Minecraft mc, int x, int y) {
        String txt1 = text1.getText();
        String txt2 = text2.getText();
        String txt3 = text3.getText();
        String txt4 = text4.getText();
        func_231158_b_(mc, x, y);
        text1.setText(txt1);
        text2.setText(txt2);
        text3.setText(txt3);
        text4.setText(txt4);
    }

    @Override
    public boolean func_231177_au__() {
        return false;
    }
}
