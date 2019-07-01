package de.maxhenkel.car.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.net.MessageFuelStationAdminAmount;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.Color;

public class GuiFuelStationAdmin extends GuiBase {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_fuelstation_admin.png");

    private TileEntityFuelStation fuelStation;
    private PlayerInventory inventoryPlayer;

    private static final int TITLE_COLOR = Color.WHITE.getRGB();
    private static final int FONT_COLOR = Color.DARK_GRAY.getRGB();

    protected TextFieldWidget textField;

    public GuiFuelStationAdmin(ContainerFuelStationAdmin fuelStation, PlayerInventory playerInventory) {
        super(GUI_TEXTURE, fuelStation, playerInventory, new TranslationTextComponent("block.car.fuelstation"));
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
        textField.setText(String.valueOf(fuelStation.getField(2)));

        children.add(textField);
        func_212928_a(textField);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // Background
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i, j, 0, 0, this.xSize, this.ySize);

        // text
        drawCenteredString(font, new TranslationTextComponent("gui.fuelstation").getFormattedText(),
                width / 2, guiTop + 5, TITLE_COLOR);

        font.drawString(inventoryPlayer.getDisplayName().getFormattedText(), guiLeft + 8, guiTop + ySize - 93, FONT_COLOR);
    }

    @Override
    public boolean keyReleased(int p_keyReleased_1_, int p_keyReleased_2_, int p_keyReleased_3_) {
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
        return super.keyReleased(p_keyReleased_1_, p_keyReleased_2_, p_keyReleased_3_);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
