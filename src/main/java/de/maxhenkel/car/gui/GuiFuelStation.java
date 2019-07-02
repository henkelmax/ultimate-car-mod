package de.maxhenkel.car.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import de.maxhenkel.car.Main;
import de.maxhenkel.tools.FluidStackWrapper;
import de.maxhenkel.tools.ItemTools;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.entity.car.base.EntityCarFuelBase;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;

public class GuiFuelStation extends GuiBase<ContainerFuelStation> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_fuelstation.png");

    private TileEntityFuelStation fuelStation;
    private PlayerInventory playerInventory;

    private static final int TITLE_COLOR = Color.WHITE.getRGB();
    private static final int FONT_COLOR = Color.DARK_GRAY.getRGB();
    private static final TextFormatting INFO_COLOR = TextFormatting.WHITE;

    protected Button buttonStart;
    protected Button buttonStop;

    public GuiFuelStation(ContainerFuelStation fuelStation, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, fuelStation, playerInventory, title);
        this.fuelStation = fuelStation.getFuelStation();
        this.playerInventory = playerInventory;

        xSize = 176;
        ySize = 217;
    }

    @Override
    protected void init() {
        super.init();

        this.buttonStart = addButton(new Button((width / 2) - 20, guiTop + 100, 40, 20,
                new TranslationTextComponent("button.start").getFormattedText(), button -> {
            fuelStation.setFueling(true);
            fuelStation.sendStartFuelPacket(true);
        }));
        this.buttonStop = addButton(new Button(guiLeft + xSize - 40 - 7, guiTop + 100, 40, 20,
                new TranslationTextComponent("button.stop").getFormattedText(), button -> {
            fuelStation.setFueling(false);
            fuelStation.sendStartFuelPacket(false);
        }));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        // buttons
        buttonStart.active = !fuelStation.isFueling();
        buttonStop.active = fuelStation.isFueling();

        // text
        drawCenteredString(font, new TranslationTextComponent("gui.fuelstation").getFormattedText(),
                width / 2, guiTop + 5, TITLE_COLOR);

        // Car name
        EntityCarFuelBase car = fuelStation.getCarInFront();

        drawCarName(car);
        drawCarFuel(car);
        drawRefueled();
        drawBuffer();

        font.drawString(playerInventory.getDisplayName().getFormattedText(), guiLeft + 8, guiTop + ySize - 93, FONT_COLOR);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        ItemStack stack = fuelStation.getTradingInventory().getStackInSlot(0);

        if (ItemTools.isStackEmpty(stack)) {
            return;
        }

        if (mouseX >= guiLeft + 18 && mouseX <= guiLeft + 33) {
            if (mouseY >= guiTop + 99 && mouseY <= guiTop + 114) {
                List<String> list = new ArrayList<String>();
                list.add(new TranslationTextComponent("tooltip.trade", stack.getCount(), stack.getDisplayName(), fuelStation.getTradeAmount())
                        .getFormattedText());
                renderTooltip(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }
    }

    private void drawCarName(EntityCarFuelBase car) {
        String carText;
        if (car == null) {
            carText = new TranslationTextComponent("fuelstation.nocar").getFormattedText();
        } else {
            carText = new TranslationTextComponent("fuelstation.carinfo",
                    INFO_COLOR + car.getCarName().getFormattedText()).getFormattedText();
        }

        font.drawString(carText, guiLeft + 63, guiTop + 20, FONT_COLOR);
    }

    private void drawCarFuel(EntityCarFuelBase car) {
        if (car == null) {
            String empty = new TranslationTextComponent("fuelstation.fuel_empty").getFormattedText();
            font.drawString(empty, guiLeft + 63, guiTop + 30, FONT_COLOR);
            return;
        } else {
            String fuelText = new TranslationTextComponent("fuelstation.car_fuel_amount",
                    INFO_COLOR + String.valueOf(car.getFuelAmount()),
                    INFO_COLOR + String.valueOf(car.getMaxFuel())).getFormattedText();
            font.drawString(fuelText, guiLeft + 63, guiTop + 30, FONT_COLOR);
        }

        if (car.getFluid() == null) {
            //TODO
            return;
        } else {
            String typeText = new TranslationTextComponent("fuelstation.car_fuel_type", INFO_COLOR + car.getFluid().getLocalizedName(new FluidStackWrapper(car.getFluid(), 1))).getFormattedText();
            font.drawString(typeText, guiLeft + 63, guiTop + 40, FONT_COLOR);
        }
    }

    private void drawRefueled() {
        String refueledText = new TranslationTextComponent("fuelstation.refueled",
                INFO_COLOR + String.valueOf(fuelStation.getFuelCounter())).getFormattedText();

        font.drawString(refueledText, guiLeft + 63, guiTop + 60, FONT_COLOR);
    }

    private void drawBuffer() {
        FluidStack stack = fuelStation.getStorage();

        if (stack == null) {

            String bufferText = new TranslationTextComponent("fuelstation.fuel_empty").getFormattedText();

            font.drawString(bufferText, guiLeft + 63, guiTop + 70, FONT_COLOR);

            return;
        }

        int amount = fuelStation.getFuelAmount();

        String amountText = new TranslationTextComponent("fuelstation.fuel_buffer_amount",
                INFO_COLOR + String.valueOf(amount), INFO_COLOR + String.valueOf(fuelStation.maxStorageAmount)).getFormattedText();

        font.drawString(amountText, guiLeft + 63, guiTop + 70, FONT_COLOR);

        String fluidName = stack.getLocalizedName();

        String bufferText = new TranslationTextComponent("fuelstation.fuel_buffer_type", INFO_COLOR + fluidName)
                .getFormattedText();

        font.drawString(bufferText, guiLeft + 63, guiTop + 80, FONT_COLOR);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
