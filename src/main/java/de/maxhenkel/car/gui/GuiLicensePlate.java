package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.car.net.MessageEditLicensePlate;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class GuiLicensePlate extends ScreenBase<ContainerLicensePlate> {

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(CarMod.MODID, "textures/gui/gui_license_plate.png");

    private ContainerLicensePlate containerLicensePlate;
    private Player player;

    private EditBox textField;

    public GuiLicensePlate(ContainerLicensePlate containerLicensePlate, Inventory playerInventory, Component title) {
        super(GUI_TEXTURE, containerLicensePlate, playerInventory, title);
        this.containerLicensePlate = containerLicensePlate;
        this.player = playerInventory.player;
        this.imageWidth = 176;
        this.imageHeight = 84;
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(Button.builder(Component.translatable("button.car.submit"), button -> {
            ClientPacketDistributor.sendToServer(new MessageEditLicensePlate(player, textField.getValue()));
            MessageEditLicensePlate.setItemText(player, textField.getValue());
            Minecraft.getInstance().setScreen(null);
        }).bounds(leftPos + 20, topPos + imageHeight - 25, 50, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("button.car.cancel"), button -> {
            Minecraft.getInstance().setScreen(null);
        }).bounds(leftPos + imageWidth - 50 - 15, topPos + imageHeight - 25, 50, 20).build());

        textField = new EditBox(font, leftPos + 30, topPos + 30, 116, 16, Component.empty());
        textField.setTextColor(-1);
        textField.setTextColorUneditable(-1);
        textField.setBordered(true);
        textField.setMaxLength(10);
        textField.setValue(ItemLicensePlate.getText(containerLicensePlate.getLicensePlate()));

        addRenderableWidget(textField);
        setInitialFocus(textField);
    }

    @Override
    public void resize(int x, int y) {
        String text = textField.getValue();
        init(x, y);
        textField.setValue(text);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.isEscape()) {
            minecraft.player.closeContainer();
            return true;
        }

        return textField.keyPressed(event) || textField.canConsumeInput() || super.keyPressed(event);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.drawCenteredString(font, containerLicensePlate.getLicensePlate().getHoverName().getString(), imageWidth / 2, 5, 0xFFFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
