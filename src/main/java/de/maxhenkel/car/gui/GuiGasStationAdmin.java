package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.car.net.MessageGasStationAdminAmount;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class GuiGasStationAdmin extends ScreenBase<ContainerGasStationAdmin> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_gas_station_admin.png");

    private TileEntityGasStation gasStation;
    private PlayerInventory inventoryPlayer;

    private static final int TITLE_COLOR = Color.WHITE.getRGB();
    private static final int FONT_COLOR = Color.DARK_GRAY.getRGB();

    protected TextFieldWidget textField;

    public GuiGasStationAdmin(ContainerGasStationAdmin gasStation, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, gasStation, playerInventory, title);
        this.gasStation = gasStation.getGasStation();
        this.inventoryPlayer = playerInventory;

        xSize = 176;
        ySize = 197;
    }

    @Override
    protected void init() {
        super.init();

        minecraft.keyboardListener.enableRepeatEvents(true);
        textField = new TextFieldWidget(font, guiLeft + 54, guiTop + 22, 100, 16, new TranslationTextComponent("gas_station.admin.amount_text_field"));
        textField.setTextColor(-1);
        textField.setDisabledTextColour(-1);
        textField.setMaxStringLength(20);
        textField.setText(String.valueOf(gasStation.getTradeAmount()));
        textField.setResponder(this::onTextChanged);

        addButton(textField);
    }

    public void onTextChanged(String text) {
        if (!text.isEmpty()) {
            try {
                int i = Integer.parseInt(text);
                Main.SIMPLE_CHANNEL.sendToServer(new MessageGasStationAdminAmount(gasStation.getPos(), i));
            } catch (Exception e) {
            }
        }
    }

    public void onClose() {
        super.onClose();
        minecraft.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public void resize(Minecraft mc, int x, int y) {
        String text = textField.getText();
        init(mc, x, y);
        textField.setText(text);
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            minecraft.player.closeScreen();
            return true;
        }

        return textField.keyPressed(key, scanCode, modifiers) || textField.canWrite() || super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);

        drawCenteredString(matrixStack, font, new TranslationTextComponent("gui.gas_station").getString(), xSize / 2, 5, TITLE_COLOR);

        font.func_238422_b_(matrixStack, inventoryPlayer.getDisplayName().func_241878_f(), 8, ySize - 93, FONT_COLOR);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
