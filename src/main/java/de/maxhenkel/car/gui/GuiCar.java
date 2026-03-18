package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import de.maxhenkel.corelib.inventory.ScreenBase;
import de.maxhenkel.corelib.math.MathUtils;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class GuiCar extends ScreenBase<ContainerCar> {

    private static final Identifier CAR_GUI_TEXTURE = Identifier.fromNamespaceAndPath(CarMod.MODID, "textures/gui/gui_car.png");

    private Inventory playerInv;
    private EntityCarInventoryBase car;

    public GuiCar(ContainerCar containerCar, Inventory playerInv, Component title) {
        super(CAR_GUI_TEXTURE, containerCar, playerInv, title, 176, 248);
        this.playerInv = playerInv;
        this.car = containerCar.getCar();
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        super.extractLabels(guiGraphics, mouseX, mouseY);

        //Titles
        guiGraphics.text(font, car.getDisplayName().getVisualOrderText(), 7, 87, FONT_COLOR, false);
        guiGraphics.text(font, playerInv.getDisplayName().getVisualOrderText(), 8, this.imageHeight - 96 + 2, FONT_COLOR, false);

        guiGraphics.text(font, getFuelString().getVisualOrderText(), 7, 9, FONT_COLOR, false);
        guiGraphics.text(font, getDamageString().getVisualOrderText(), 7, 35, FONT_COLOR, false);
        guiGraphics.text(font, getBatteryString().getVisualOrderText(), 95, 9, FONT_COLOR, false);
        guiGraphics.text(font, getTempString().getVisualOrderText(), 95, 35, FONT_COLOR, false);
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
        if (CarMod.CLIENT_CONFIG.tempInFarenheit.get()) {
            return Component.translatable("gui.car_temperature_fahrenheit", String.valueOf(getTemperatureFarenheit()));
        } else {
            return Component.translatable("gui.car_temperature_celsius", String.valueOf(getTemperatureCelsius()));
        }
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);
        drawFuel(guiGraphics, getFuelPercent());
        drawDamage(guiGraphics, 100F - getDamagePercent());
        drawBattery(guiGraphics, car.getBatteryPercentage());
        drawTemp(guiGraphics, getTemperaturePercent());
    }

    public void drawFuel(GuiGraphicsExtractor guiGraphics, float percent) {
        percent = Math.min(100F, percent);
        //72x10
        int scaled = (int) (72F * percent / 100D);
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CAR_GUI_TEXTURE, i + 8, j + 20, 176, 0, scaled, 10, 256, 256);
    }

    public void drawDamage(GuiGraphicsExtractor guiGraphics, float percent) {
        percent = Math.min(100F, percent);
        int scaled = (int) (72F * percent / 100D);
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CAR_GUI_TEXTURE, i + 8, j + 46, 176, 10, scaled, 10, 256, 256);
    }

    public void drawTemp(GuiGraphicsExtractor guiGraphics, float percent) {
        percent = Math.min(100F, percent);
        int scaled = (int) (72F * percent);
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CAR_GUI_TEXTURE, i + 96, j + 46, 176, 30, scaled, 10, 256, 256);
    }

    public void drawBattery(GuiGraphicsExtractor guiGraphics, float percent) {
        percent = Math.min(100F, percent);
        int scaled = (int) (72F * percent);
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CAR_GUI_TEXTURE, i + 96, j + 20, 176, 20, scaled, 10, 256, 256);
    }

}
