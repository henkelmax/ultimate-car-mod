package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.net.MessageFuelStationAdminAmount;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.Color;

public class GuiFuelStationAdmin extends GuiBase<ContainerFuelStationAdmin> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_fuelstation_admin.png");

    private TileEntityFuelStation fuelStation;
    private PlayerInventory inventoryPlayer;

    private static final int TITLE_COLOR = Color.WHITE.getRGB();
    private static final int FONT_COLOR = Color.DARK_GRAY.getRGB();

    protected TextFieldWidget textField;

    public GuiFuelStationAdmin(ContainerFuelStationAdmin fuelStation, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, fuelStation, playerInventory, title);
        this.fuelStation = fuelStation.getFuelStation();
        this.inventoryPlayer = playerInventory;

        xSize = 176;
        ySize = 197;
    }

    @Override
    protected void init() {
        super.init();

        textField = new TextFieldWidget(font, guiLeft + 54, guiTop + 22, 100, 16, ""); //TODO name
        textField.setTextColor(-1);
        textField.setDisabledTextColour(-1);
        textField.setEnableBackgroundDrawing(true);
        textField.setMaxStringLength(20);
        textField.setText(String.valueOf(fuelStation.getTradeAmount()));

        children.add(textField);
        func_212928_a(textField);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        // text
        drawCenteredString(font, new TranslationTextComponent("gui.fuelstation").getFormattedText(),
                width / 2, guiTop + 5, TITLE_COLOR);

        font.drawString(inventoryPlayer.getDisplayName().getFormattedText(), guiLeft + 8, guiTop + ySize - 93, FONT_COLOR);
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (textField.isFocused()) {
            if (!textField.getText().isEmpty()) {
                int i = 0;
                try {
                    i = Integer.parseInt(textField.getText());
                } catch (Exception e) {
                }

                textField.setText(String.valueOf(i));
                Main.SIMPLE_CHANNEL.sendToServer(new MessageFuelStationAdminAmount(fuelStation.getPos(), i));
            }
        }
        return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
