package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.car.net.MessageGasStationAdminAmount;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.awt.*;

public class GuiGasStationAdmin extends ScreenBase<ContainerGasStationAdmin> {

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(CarMod.MODID, "textures/gui/gui_gas_station_admin.png");

    private TileEntityGasStation gasStation;
    private Inventory inventoryPlayer;

    private static final int TITLE_COLOR = Color.WHITE.getRGB();
    private static final int FONT_COLOR = Color.DARK_GRAY.getRGB();

    protected EditBox textField;

    public GuiGasStationAdmin(ContainerGasStationAdmin gasStation, Inventory playerInventory, Component title) {
        super(GUI_TEXTURE, gasStation, playerInventory, title);
        this.gasStation = gasStation.getGasStation();
        this.inventoryPlayer = playerInventory;

        imageWidth = 176;
        imageHeight = 197;
    }

    @Override
    protected void init() {
        super.init();

        textField = new EditBox(font, leftPos + 54, topPos + 22, 100, 16, Component.translatable("gas_station.admin.amount_text_field"));
        textField.setTextColor(-1);
        textField.setTextColorUneditable(-1);
        textField.setMaxLength(20);
        textField.setValue(String.valueOf(gasStation.getTradeAmount()));
        textField.setResponder(this::onTextChanged);

        addRenderableWidget(textField);
    }

    public void onTextChanged(String text) {
        if (!text.isEmpty()) {
            try {
                int i = Integer.parseInt(text);
                ClientPacketDistributor.sendToServer(new MessageGasStationAdminAmount(gasStation.getBlockPos(), i));
            } catch (Exception e) {
            }
        }
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

        guiGraphics.drawCenteredString(font, Component.translatable("gui.gas_station").getString(), imageWidth / 2, 5, TITLE_COLOR);

        guiGraphics.drawString(font, inventoryPlayer.getDisplayName().getVisualOrderText(), 8, imageHeight - 93, FONT_COLOR, false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
