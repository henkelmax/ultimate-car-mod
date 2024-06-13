package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import de.maxhenkel.corelib.inventory.ScreenBase;
import de.maxhenkel.corelib.math.MathUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiCar extends ScreenBase<ContainerCar> {

    private static final ResourceLocation CAR_GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(Main.MODID, "textures/gui/gui_car.png");

    private static final int fontColor = 4210752;

    private Inventory playerInv;
    private EntityCarInventoryBase car;

    public GuiCar(ContainerCar containerCar, Inventory playerInv, Component title) {
        super(CAR_GUI_TEXTURE, containerCar, playerInv, title);
        this.playerInv = playerInv;
        this.car = containerCar.getCar();

        imageWidth = 176;
        imageHeight = 248;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        //Titles
        guiGraphics.drawString(font, car.getDisplayName().getVisualOrderText(), 7, 87, fontColor, false);
        guiGraphics.drawString(font, playerInv.getDisplayName().getVisualOrderText(), 8, this.imageHeight - 96 + 2, fontColor, false);

        guiGraphics.drawString(font, getFuelString().getVisualOrderText(), 7, 9, fontColor, false);
        guiGraphics.drawString(font, getDamageString().getVisualOrderText(), 7, 35, fontColor, false);
        guiGraphics.drawString(font, getBatteryString().getVisualOrderText(), 95, 9, fontColor, false);
        guiGraphics.drawString(font, getTempString().getVisualOrderText(), 95, 35, fontColor, false);
    }

    public float getFuelPercent() {
        float fuelPerc = ((float) car.getFuelAmount()) / ((float) car.getMaxFuel()) * 100F;
        return MathUtils.round(fuelPerc, 2);
    }

    public int getBatteryPercent() {
        return (int) (car.getBatteryPercentage() * 100F);
    }

    public float getTemperatureCelsius() {
        return MathUtils.round(car.getTemperature(), 2);
    }

    public float getTemperatureFarenheit() {
        return MathUtils.round((car.getTemperature() * 1.8F) + 32F, 2);
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
        return MathUtils.round(dmg, 2);
    }

    public Component getFuelString() {
        return Component.translatable("gui.car_fuel", String.valueOf(getFuelPercent()));
    }

    public Component getDamageString() {
        return Component.translatable("gui.car_damage", String.valueOf(getDamagePercent()));
    }

    public Component getBatteryString() {
        return Component.translatable("gui.car_battery", String.valueOf(getBatteryPercent()));
    }

    public Component getTempString() {
        if (Main.CLIENT_CONFIG.tempInFarenheit.get()) {
            return Component.translatable("gui.car_temperature_fahrenheit", String.valueOf(getTemperatureFarenheit()));
        } else {
            return Component.translatable("gui.car_temperature_celsius", String.valueOf(getTemperatureCelsius()));
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
        drawFuel(guiGraphics, getFuelPercent());
        drawDamage(guiGraphics, 100F - getDamagePercent());
        drawBattery(guiGraphics, car.getBatteryPercentage());
        drawTemp(guiGraphics, getTemperaturePercent());
    }

    public void drawFuel(GuiGraphics guiGraphics, float percent) {
        percent = Math.min(100F, percent);
        //72x10
        int scaled = (int) (72F * percent / 100D);
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(CAR_GUI_TEXTURE, i + 8, j + 20, 176, 0, scaled, 10);
    }

    public void drawDamage(GuiGraphics guiGraphics, float percent) {
        percent = Math.min(100F, percent);
        int scaled = (int) (72F * percent / 100D);
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(CAR_GUI_TEXTURE, i + 8, j + 46, 176, 10, scaled, 10);
    }

    public void drawTemp(GuiGraphics guiGraphics, float percent) {
        percent = Math.min(100F, percent);
        int scaled = (int) (72F * percent);
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(CAR_GUI_TEXTURE, i + 96, j + 46, 176, 30, scaled, 10);
    }

    public void drawBattery(GuiGraphics guiGraphics, float percent) {
        percent = Math.min(100F, percent);
        int scaled = (int) (72F * percent);
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(CAR_GUI_TEXTURE, i + 96, j + 20, 176, 20, scaled, 10);
    }

}
