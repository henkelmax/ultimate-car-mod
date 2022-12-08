package de.maxhenkel.car.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import de.maxhenkel.car.net.MessageEditSign;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

public class GuiSign extends ScreenBase<ContainerSign> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_sign.png");

    private TileEntitySign sign;

    protected int guiLeft;
    protected int guiTop;

    protected Button buttonSwitch;
    protected Button buttonSubmit;
    protected Button buttonCancel;

    protected EditBox text1;
    protected EditBox text2;
    protected EditBox text3;
    protected EditBox text4;

    protected String[] text;

    protected boolean front = true;

    public GuiSign(ContainerSign containerSign, Inventory playerInventory, Component title) {
        super(GUI_TEXTURE, containerSign, playerInventory, title);
        this.sign = containerSign.getSign();
        this.imageWidth = 176;
        this.imageHeight = 142;
        this.text = sign.getSignText();
    }

    @Override
    protected void init() {
        super.init();

        guiLeft = (width - imageWidth) / 2;
        guiTop = (height - imageHeight) / 2;

        text1 = initTextField(0, 0);
        text2 = initTextField(1, 20);
        text3 = initTextField(2, 40);
        text4 = initTextField(3, 60);

        setInitialFocus(text1);

        buttonSubmit = addRenderableWidget(Button.builder(Component.translatable("button.car.submit"), button -> {
            save();
            Main.SIMPLE_CHANNEL.sendToServer(new MessageEditSign(sign.getBlockPos(), text));
            Minecraft.getInstance().setScreen(null);
        }).bounds(guiLeft + 20, guiTop + imageHeight - 25, 50, 20).build());
        buttonCancel = addRenderableWidget(Button.builder(Component.translatable("button.car.cancel"), button -> {
            Minecraft.getInstance().setScreen(null);
        }).bounds(guiLeft + imageWidth - 50 - 15, guiTop + imageHeight - 25, 50, 20).build());
        buttonSwitch = addRenderableWidget(Button.builder(Component.translatable("button.car.back"), button -> {
            save();
            front = !front;

            if (front) {
                text1.setValue(text[0]);
                text2.setValue(text[1]);
                text3.setValue(text[2]);
                text4.setValue(text[3]);
                buttonSwitch.setMessage(Component.translatable("button.car.back"));
            } else {
                text1.setValue(text[4]);
                text2.setValue(text[5]);
                text3.setValue(text[6]);
                text4.setValue(text[7]);
                buttonSwitch.setMessage(Component.translatable("button.car.front"));
            }
        }).bounds(guiLeft + 5, guiTop + 49 + 10, 46, 20).build());
    }

    private EditBox initTextField(int id, int height) {
        EditBox field = new EditBox(font, guiLeft + 54, guiTop + 30 + height, 114, 16, Component.empty());
        field.setTextColor(-1);
        field.setTextColorUneditable(-1);
        field.setBordered(true);
        field.setMaxLength(20);
        field.setValue(text[id]);
        addRenderableWidget(field);
        return field;
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

        String s;
        if (front) {
            s = Component.translatable("gui.sign.front").getString();
        } else {
            s = Component.translatable("gui.sign.back").getString();
        }

        font.draw(matrixStack, Component.translatable("gui.sign", s).getVisualOrderText(), 54, 10, FONT_COLOR);
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            minecraft.player.closeContainer();
            return true;
        }

        return text1.keyPressed(key, scanCode, modifiers) ||
                text1.canConsumeInput() ||
                text2.keyPressed(key, scanCode, modifiers) ||
                text2.canConsumeInput() ||
                text3.keyPressed(key, scanCode, modifiers) ||
                text3.canConsumeInput() ||
                text4.keyPressed(key, scanCode, modifiers) ||
                text4.canConsumeInput() || super.keyPressed(key, scanCode, modifiers);
    }

    private void save() {
        if (front) {
            text[0] = text1.getValue();
            text[1] = text2.getValue();
            text[2] = text3.getValue();
            text[3] = text4.getValue();
        } else {
            text[4] = text1.getValue();
            text[5] = text2.getValue();
            text[6] = text3.getValue();
            text[7] = text4.getValue();
        }
    }

    @Override
    public void resize(Minecraft mc, int x, int y) {
        String txt1 = text1.getValue();
        String txt2 = text2.getValue();
        String txt3 = text3.getValue();
        String txt4 = text4.getValue();
        init(mc, x, y);
        text1.setValue(txt1);
        text2.setValue(txt2);
        text3.setValue(txt3);
        text4.setValue(txt4);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
