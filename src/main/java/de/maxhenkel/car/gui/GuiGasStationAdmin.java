package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.car.net.MessageGasStationAdminAmount;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class GuiGasStationAdmin extends GuiBase<ContainerGasStationAdmin> {

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
    protected void func_231160_c_() {
        super.func_231160_c_();

        field_230706_i_.keyboardListener.enableRepeatEvents(true);
        textField = new TextFieldWidget(field_230712_o_, guiLeft + 54, guiTop + 22, 100, 16, new TranslationTextComponent("gas_station.admin.amount_text_field"));
        textField.setTextColor(-1);
        textField.setDisabledTextColour(-1);
        textField.setMaxStringLength(20);
        textField.setText(String.valueOf(gasStation.getTradeAmount()));
        textField.setResponder(this::onTextChanged);

        func_230480_a_(textField);
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

    public void func_231164_f_() {
        super.func_231164_f_();
        field_230706_i_.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public void func_231152_a_(Minecraft mc, int x, int y) {
        String text = textField.getText();
        func_231158_b_(mc, x, y);
        textField.setText(text);
    }

    @Override
    public boolean func_231046_a_(int key, int a, int b) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            field_230706_i_.player.closeScreen();
            return true;
        }

        return textField.func_231046_a_(key, a, b) || textField.canWrite() || super.func_231046_a_(key, a, b);
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.func_230451_b_(matrixStack, mouseX, mouseY);

        func_238471_a_(matrixStack, field_230712_o_, new TranslationTextComponent("gui.gas_station").getString(), xSize / 2, 5, TITLE_COLOR);

        field_230712_o_.func_238422_b_(matrixStack, inventoryPlayer.getDisplayName(), 8, ySize - 93, FONT_COLOR);
    }

    @Override
    public boolean func_231177_au__() {
        return false;
    }
}
