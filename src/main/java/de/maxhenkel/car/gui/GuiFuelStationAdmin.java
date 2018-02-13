package de.maxhenkel.car.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.entity.car.base.EntityCarFuelBase;
import de.maxhenkel.car.net.MessageEditSign;
import de.maxhenkel.car.net.MessageFuelStationAdminAmount;
import de.maxhenkel.car.proxy.CommonProxy;
import de.maxhenkel.tools.MathTools;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import scala.Int;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiFuelStationAdmin extends GuiBase {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_fuelstation_admin.png");

    private TileEntityFuelStation fuelStation;
    private IInventory inventoryPlayer;

    protected int xSize = 176;
    protected int ySize = 197;

    private static final int TITLE_COLOR = Color.WHITE.getRGB();
    private static final int FONT_COLOR = Color.DARK_GRAY.getRGB();
    private static final ChatFormatting INFO_COLOR = ChatFormatting.WHITE;

    protected int guiLeft;
    protected int guiTop;

    protected GuiTextField textField;

    public GuiFuelStationAdmin(TileEntityFuelStation fuelStation, IInventory inventoryPlayer) {
        super(new ContainerFuelStationAdmin(fuelStation, inventoryPlayer));
        this.fuelStation = fuelStation;
        this.inventoryPlayer = inventoryPlayer;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        textField = new GuiTextField(0, fontRenderer, guiLeft + 54, guiTop + 22, 100, 16);
        textField.setTextColor(-1);
        textField.setDisabledTextColour(-1);
        textField.setEnableBackgroundDrawing(true);
        textField.setMaxStringLength(20);
        textField.setText(String.valueOf(fuelStation.getField(2)));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // Background
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        // text
        drawCenteredString(fontRenderer, new TextComponentTranslation("gui.fuelstation").getFormattedText(),
                width / 2, guiTop + 5, TITLE_COLOR);

        fontRenderer.drawString(inventoryPlayer.getDisplayName().getUnformattedText(), guiLeft + 8, guiTop + ySize - 93, FONT_COLOR);

        textField.drawTextBox();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (textField.textboxKeyTyped(typedChar, keyCode)) {
            if(!textField.getText().isEmpty()){
                int i=0;
                try{
                    i=Integer.parseInt(textField.getText());
                }catch (Exception e){}

                textField.setText(String.valueOf(i));
                CommonProxy.simpleNetworkWrapper.sendToServer(new MessageFuelStationAdminAmount(fuelStation.getPos(), i));
            }
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}
