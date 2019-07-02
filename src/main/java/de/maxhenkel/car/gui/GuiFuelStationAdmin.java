package de.maxhenkel.car.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.net.MessageFuelStationAdminAmount;
import net.minecraft.client.Minecraft;
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

        minecraft.keyboardListener.enableRepeatEvents(true);
        textField = new TextFieldWidget(font, guiLeft + 54, guiTop + 26, 100, 8, new TranslationTextComponent("fuelstation.admin.amount_text_field").getFormattedText());
        textField.setCanLoseFocus(false);
        textField.changeFocus(true);
        textField.setTextColor(-1);
        textField.setDisabledTextColour(-1);
        textField.setEnableBackgroundDrawing(false);
        textField.setMaxStringLength(20);
        textField.func_212954_a(this::onTextChanged);
        children.add(textField);
        setFocused(textField);
    }

    public void render(int x, int y, float t) {
        super.render(x, y, t);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        textField.render(x, y, t);
        renderHoveredToolTip(x, y);
    }

    public void onTextChanged(String text) {
        if (textField.isFocused()) {
            if (!text.isEmpty()) {
                int i = 0;
                try {
                    i = Integer.parseInt(text);
                } catch (Exception e) {
                }
                Main.SIMPLE_CHANNEL.sendToServer(new MessageFuelStationAdminAmount(fuelStation.getPos(), i));
            }
        }
    }

    @Override
    public void removed() {
        super.removed();
        minecraft.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public void resize(Minecraft mc, int x, int y) {
        String text = textField.getText();
        init(mc, x, y);
        textField.setText(text);
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
    public boolean isPauseScreen() {
        return false;
    }
}
