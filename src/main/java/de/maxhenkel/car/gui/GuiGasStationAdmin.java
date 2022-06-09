package de.maxhenkel.car.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.car.net.MessageGasStationAdminAmount;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class GuiGasStationAdmin extends ScreenBase<ContainerGasStationAdmin> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_gas_station_admin.png");

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

        minecraft.keyboardHandler.setSendRepeatsToGui(true);
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
                Main.SIMPLE_CHANNEL.sendToServer(new MessageGasStationAdminAmount(gasStation.getBlockPos(), i));
            } catch (Exception e) {
            }
        }
    }

    public void onClose() {
        super.onClose();
        minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void resize(Minecraft mc, int x, int y) {
        String text = textField.getValue();
        init(mc, x, y);
        textField.setValue(text);
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            minecraft.player.closeContainer();
            return true;
        }

        return textField.keyPressed(key, scanCode, modifiers) || textField.canConsumeInput() || super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

        drawCenteredString(matrixStack, font, Component.translatable("gui.gas_station").getString(), imageWidth / 2, 5, TITLE_COLOR);

        font.draw(matrixStack, inventoryPlayer.getDisplayName().getVisualOrderText(), 8, imageHeight - 93, FONT_COLOR);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
