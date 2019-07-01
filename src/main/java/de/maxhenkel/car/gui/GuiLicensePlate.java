package de.maxhenkel.car.gui;

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
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.Color;

public class GuiLicensePlate extends GuiBase<ContainerLicensePlate> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_license_plate.png");

    private static final int TITLE_COLOR = Color.WHITE.getRGB();

    private ContainerLicensePlate containerLicensePlate;
    private PlayerEntity player;

    protected Button buttonSubmit;
    protected Button buttonCancel;

    protected TextFieldWidget textField;

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

        //Keyboard.enableRepeatEvents(true);  //TODO
        this.buttonSubmit = addButton(new Button(guiLeft + 20, guiTop + ySize - 25, 50, 20,
                new TranslationTextComponent("button.submit").getFormattedText(), button -> {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageEditLicensePlate(player, textField.getText()));
            MessageEditLicensePlate.setItemText(player, textField.getText());
            Minecraft.getInstance().displayGuiScreen(null);
        }));
        this.buttonCancel = addButton(new Button(guiLeft + xSize - 50 - 15, guiTop + ySize - 25, 50, 20,
                new TranslationTextComponent("button.cancel").getFormattedText(), button -> {
            Minecraft.getInstance().displayGuiScreen(null);
        }));

        textField = new TextFieldWidget(font, guiLeft + 30, guiTop + 30, 116, 16, ""); //TODO name
        textField.setTextColor(-1);
        textField.setDisabledTextColour(-1);
        textField.setEnableBackgroundDrawing(true);
        textField.setMaxStringLength(10);
        textField.setText(ItemLicensePlate.getText(containerLicensePlate.getLicensePlate()));

        children.add(textField);
        func_212928_a(textField);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    public void onClose() {
        super.onClose();
        //Keyboard.enableRepeatEvents(false); //TODO
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        drawCenteredString(font, new TranslationTextComponent("gui.license_plate").getFormattedText(),
                width / 2, guiTop + 5, TITLE_COLOR);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
