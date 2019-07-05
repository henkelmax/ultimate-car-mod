package de.maxhenkel.car.gui;

import com.mojang.blaze3d.platform.GlStateManager;
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

    private Button buttonSubmit;
    private Button buttonCancel;

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

        textField = new TextFieldWidget(font, guiLeft + 30, guiTop + 30, 116, 16, "");
        textField.setTextColor(-1);
        textField.setDisabledTextColour(-1);
        textField.setEnableBackgroundDrawing(true);
        textField.setMaxStringLength(10);
        textField.setText(ItemLicensePlate.getText(containerLicensePlate.getLicensePlate()));

        children.add(textField);
        setFocused(textField);
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        textField.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void resize(Minecraft mc, int x, int y) {
        String text = textField.getText();
        init(mc, x, y);
        textField.setText(text);
    }

    @Override
    public boolean keyPressed(int key, int a, int b) {
        if (key == 256) {
            this.minecraft.player.closeScreen();
        }

        return !textField.keyPressed(key, a, b) && !textField.func_212955_f() ? super.keyPressed(key, a, b) : true;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        drawCenteredString(font, containerLicensePlate.getLicensePlate().getDisplayName().getFormattedText(),
                xSize / 2, 5, TITLE_COLOR);
    }

    @Override
    public void removed() {
        super.removed();
        minecraft.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
