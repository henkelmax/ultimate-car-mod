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
        this.xSize = 176;
        this.ySize = 84;
    }

    @Override
    protected void init() {
        super.init();

        minecraft.keyboardListener.enableRepeatEvents(true);

        addButton(new Button(guiLeft + 20, guiTop + ySize - 25, 50, 20, new TranslationTextComponent("button.car.submit"), button -> {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageEditLicensePlate(player, textField.getText()));
            MessageEditLicensePlate.setItemText(player, textField.getText());
            Minecraft.getInstance().displayGuiScreen(null);
        }));
        addButton(new Button(guiLeft + xSize - 50 - 15, guiTop + ySize - 25, 50, 20, new TranslationTextComponent("button.car.cancel"), button -> {
            Minecraft.getInstance().displayGuiScreen(null);
        }));

        textField = new TextFieldWidget(font, guiLeft + 30, guiTop + 30, 116, 16, new StringTextComponent(""));
        textField.setTextColor(-1);
        textField.setDisabledTextColour(-1);
        textField.setEnableBackgroundDrawing(true);
        textField.setMaxStringLength(10);
        textField.setText(ItemLicensePlate.getText(containerLicensePlate.getLicensePlate()));

        addButton(textField);
        setFocusedDefault(textField);
    }

    @Override
    public void resize(Minecraft mc, int x, int y) {
        String text = textField.getText();
        init(mc, x, y);
        textField.setText(text);
    }

    @Override
    public boolean keyPressed(int key, int a, int b) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            minecraft.player.closeScreen();
            return true;
        }

        return textField.keyPressed(key, a, b) || textField.canWrite() || super.keyPressed(key, a, b);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
        drawCenteredString(matrixStack, font, containerLicensePlate.getLicensePlate().getDisplayName().getString(), xSize / 2, 5, TITLE_COLOR);
    }

    @Override
    public void onClose() {
        super.onClose();
        minecraft.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
