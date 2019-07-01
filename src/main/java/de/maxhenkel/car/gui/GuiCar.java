package de.maxhenkel.car.gui;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.tools.MathTools;
import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiCar extends GuiBase<ContainerCar> {

    private static final ResourceLocation CAR_GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_car.png");

    private static final int fontColor = 4210752;

    private PlayerInventory playerInv;
    private EntityCarInventoryBase car;

    public GuiCar(ContainerCar containerCar, PlayerInventory playerInv, ITextComponent title) {
        super(CAR_GUI_TEXTURE, containerCar, playerInv, title);
        this.playerInv = playerInv;
        this.car = containerCar.getCar();

        xSize = 176;
        ySize = 248;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        //Titles
        font.drawString(car.getCarName().getFormattedText(), 7, 87, fontColor);
        font.drawString(playerInv.getDisplayName().getFormattedText(), 8, this.ySize - 96 + 2, fontColor);

        font.drawString(getFuelString(), 7, 9, fontColor);
        font.drawString(getDamageString(), 7, 35, fontColor);
        font.drawString(getBatteryString(), 95, 9, fontColor);
        font.drawString(getTempString(), 95, 35, fontColor);

    }

    public float getFuelPercent() {
        float fuelPerc = ((float) car.getFuelAmount()) / ((float) car.getMaxFuel()) * 100F;
        return MathTools.round(fuelPerc, 2);
    }

    public int getBatteryPercent() {
        return (int) (car.getBatteryPercentage() * 100F);
    }

    public float getTemperatureCelsius() {
        return MathTools.round(car.getTemperature(), 2);
    }

    public float getTemperatureFarenheit() {
        return MathTools.round((car.getTemperature() * 1.8F) + 32F, 2);
    }

    public float getTemperaturePercent() {
        float temp = car.getTemperature();
        if (temp > 100F) {
            temp = 100F;
        }
        if (temp < 0F) {
            temp = 0F;
        }
        return temp / 100F;
    }

    public float getDamagePercent() {
        float dmg = car.getDamage();
        dmg = Math.min(dmg, 100);
        return MathTools.round(dmg, 2);
    }

    public String getFuelString() {
        return new TranslationTextComponent("gui.car_fuel", String.valueOf(getFuelPercent())).getFormattedText();
    }

    public String getDamageString() {
        return new TranslationTextComponent("gui.car_damage", String.valueOf(getDamagePercent())).getFormattedText();
    }

    public String getBatteryString() {
        return new TranslationTextComponent("gui.car_battery", String.valueOf(getBatteryPercent())).getFormattedText();
    }

    public String getTempString() {
        if (Config.tempInFarenheit) {
            return new TranslationTextComponent("gui.car_temperature_farenheit", String.valueOf(getTemperatureFarenheit())).getFormattedText();
        } else {
            return new TranslationTextComponent("gui.car_temperature_celsius", String.valueOf(getTemperatureCelsius())).getFormattedText();
        }
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        drawFuel(getFuelPercent());
        drawDamage(100F - getDamagePercent());
        drawBattery(car.getBatteryPercentage());
        drawTemp(getTemperaturePercent());
    }

    public void drawFuel(float percent) {
        //72x10
        int scaled = (int) (72F * percent / 100D);
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i + 8, j + 20, 176, 0, scaled, 10);
    }

    public void drawDamage(float percent) {
        int scaled = (int) (72F * percent / 100D);
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i + 8, j + 46, 176, 10, scaled, 10);
    }

    public void drawTemp(float percent) {
        int scaled = (int) (72F * percent);
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i + 96, j + 46, 176, 30, scaled, 10);
    }

    public void drawBattery(float percent) {
        int scaled = (int) (72F * percent);
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i + 96, j + 20, 176, 20, scaled, 10);
    }

}
