package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.car.net.MessageEditLicensePlate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

public class GuiLicensePlate extends GuiBase<ContainerLicensePlate> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_license_plate.png");

    private static final int TITLE_COLOR = TextFormatting.WHITE.getColor();

    private ContainerLicensePlate containerLicensePlate;
    private PlayerEntity player;

    private TextFieldWidget textField;

    public GuiLicensePlate(ContainerLicensePlate containerLicensePlate, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, containerLicensePlate, playerInventory, title);
        this.containerLicensePlate = containerLicensePlate;
        this.player = playerInventory.player;
        this.xSize = 176;
        this.ySize = 84;
    }

    @Override
    protected void func_231160_c_() {
        super.func_231160_c_();

        field_230706_i_.keyboardListener.enableRepeatEvents(true);

        func_230480_a_(new Button(guiLeft + 20, guiTop + ySize - 25, 50, 20, new TranslationTextComponent("button.submit"), button -> {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageEditLicensePlate(player, textField.getText()));
            MessageEditLicensePlate.setItemText(player, textField.getText());
            Minecraft.getInstance().displayGuiScreen(null);
        }));
        func_230480_a_(new Button(guiLeft + xSize - 50 - 15, guiTop + ySize - 25, 50, 20, new TranslationTextComponent("button.cancel"), button -> {
            Minecraft.getInstance().displayGuiScreen(null);
        }));

        textField = new TextFieldWidget(field_230712_o_, guiLeft + 30, guiTop + 30, 116, 16, new StringTextComponent(""));
        textField.setTextColor(-1);
        textField.setDisabledTextColour(-1);
        textField.setEnableBackgroundDrawing(true);
        textField.setMaxStringLength(10);
        textField.setText(ItemLicensePlate.getText(containerLicensePlate.getLicensePlate()));

        func_230480_a_(textField);
        setFocusedDefault(textField);
    }

    @Override
    public void func_231152_a_(Minecraft mc, int x, int y) {
        String text = textField.getText();
        func_231158_b_(mc, x, y);
        textField.setText(text);
    }

    @Override
    public boolean func_231046_a_(int key, int a, int b) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            field_230706_i_.player.closeScreen();
            return true;
        }

        return textField.func_231046_a_(key, a, b) || textField.canWrite() || super.func_231046_a_(key, a, b);
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.func_230451_b_(matrixStack, mouseX, mouseY);
        func_238471_a_(matrixStack, field_230712_o_, containerLicensePlate.getLicensePlate().getDisplayName().getString(), xSize / 2, 5, TITLE_COLOR);
    }

    public void func_231164_f_() {
        super.func_231164_f_();
        field_230706_i_.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public boolean func_231177_au__() {
        return false;
    }
}
