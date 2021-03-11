package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.car.net.MessageEditLicensePlate;
import de.maxhenkel.corelib.inventory.ScreenBase;
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

public class GuiLicensePlate extends ScreenBase<ContainerLicensePlate> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_license_plate.png");

    private static final int TITLE_COLOR = TextFormatting.WHITE.getColor();

    private ContainerLicensePlate containerLicensePlate;
    private PlayerEntity player;

    private TextFieldWidget textField;

    public GuiLicensePlate(ContainerLicensePlate containerLicensePlate, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, containerLicensePlate, playerInventory, title);
        this.containerLicensePlate = containerLicensePlate;
        this.player = playerInventory.player;
        this.imageWidth = 176;
        this.imageHeight = 84;
    }

    @Override
    protected void init() {
        super.init();

        minecraft.keyboardHandler.setSendRepeatsToGui(true);

        addButton(new Button(leftPos + 20, topPos + imageHeight - 25, 50, 20, new TranslationTextComponent("button.car.submit"), button -> {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageEditLicensePlate(player, textField.getValue()));
            MessageEditLicensePlate.setItemText(player, textField.getValue());
            Minecraft.getInstance().setScreen(null);
        }));
        addButton(new Button(leftPos + imageWidth - 50 - 15, topPos + imageHeight - 25, 50, 20, new TranslationTextComponent("button.car.cancel"), button -> {
            Minecraft.getInstance().setScreen(null);
        }));

        textField = new TextFieldWidget(font, leftPos + 30, topPos + 30, 116, 16, new StringTextComponent(""));
        textField.setTextColor(-1);
        textField.setTextColorUneditable(-1);
        textField.setBordered(true);
        textField.setMaxLength(10);
        textField.setValue(ItemLicensePlate.getText(containerLicensePlate.getLicensePlate()));

        addButton(textField);
        setInitialFocus(textField);
    }

    @Override
    public void resize(Minecraft mc, int x, int y) {
        String text = textField.getValue();
        init(mc, x, y);
        textField.setValue(text);
    }

    @Override
    public boolean keyPressed(int key, int a, int b) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            minecraft.player.closeContainer();
            return true;
        }

        return textField.keyPressed(key, a, b) || textField.canConsumeInput() || super.keyPressed(key, a, b);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
        drawCenteredString(matrixStack, font, containerLicensePlate.getLicensePlate().getHoverName().getString(), imageWidth / 2, 5, TITLE_COLOR);
    }

    @Override
    public void onClose() {
        super.onClose();
        minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
