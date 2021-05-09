package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.inventory.ScreenBase;
import de.maxhenkel.corelib.math.MathUtils;
import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiCar extends ScreenBase<ContainerCar> {

    private static final ResourceLocation CAR_GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_car.png");

    private static final int fontColor = 4210752;

    private PlayerInventory playerInv;
    private EntityCarInventoryBase car;

    public GuiCar(ContainerCar containerCar, PlayerInventory playerInv, ITextComponent title) {
        super(CAR_GUI_TEXTURE, containerCar, playerInv, title);
        this.playerInv = playerInv;
        this.car = containerCar.getCar();

        imageWidth = 176;
        imageHeight = 248;
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

        //Titles
        font.draw(matrixStack, car.getDisplayName().getVisualOrderText(), 7, 87, fontColor);
        font.draw(matrixStack, playerInv.getDisplayName().getVisualOrderText(), 8, this.imageHeight - 96 + 2, fontColor);

        font.draw(matrixStack, getFuelString().getVisualOrderText(), 7, 9, fontColor);
        font.draw(matrixStack, getDamageString().getVisualOrderText(), 7, 35, fontColor);
        font.draw(matrixStack, getBatteryString().getVisualOrderText(), 95, 9, fontColor);
        font.draw(matrixStack, getTempString().getVisualOrderText(), 95, 35, fontColor);
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

    public TextComponent getFuelString() {
        return new TranslationTextComponent("gui.car_fuel", String.valueOf(getFuelPercent()));
    }

    public TextComponent getDamageString() {
        return new TranslationTextComponent("gui.car_damage", String.valueOf(getDamagePercent()));
    }

    public TextComponent getBatteryString() {
        return new TranslationTextComponent("gui.car_battery", String.valueOf(getBatteryPercent()));
    }

    public TextComponent getTempString() {
        if (Main.CLIENT_CONFIG.tempInFarenheit.get()) {
            return new TranslationTextComponent("gui.car_temperature_farenheit", String.valueOf(getTemperatureFarenheit()));
        } else {
            return new TranslationTextComponent("gui.car_temperature_celsius", String.valueOf(getTemperatureCelsius()));
        }
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        drawFuel(matrixStack, getFuelPercent());
        drawDamage(matrixStack, 100F - getDamagePercent());
        drawBattery(matrixStack, car.getBatteryPercentage());
        drawTemp(matrixStack, getTemperaturePercent());
    }

    public void drawFuel(MatrixStack matrixStack, float percent) {
        percent = Math.min(100F, percent);
        //72x10
        int scaled = (int) (72F * percent / 100D);
        int i = this.leftPos;
        int j = this.topPos;
        blit(matrixStack, i + 8, j + 20, 176, 0, scaled, 10);
    }

    public void drawDamage(MatrixStack matrixStack, float percent) {
        percent = Math.min(100F, percent);
        int scaled = (int) (72F * percent / 100D);
        int i = this.leftPos;
        int j = this.topPos;
        blit(matrixStack, i + 8, j + 46, 176, 10, scaled, 10);
    }

    public void drawTemp(MatrixStack matrixStack, float percent) {
        percent = Math.min(100F, percent);
        int scaled = (int) (72F * percent);
        int i = this.leftPos;
        int j = this.topPos;
        blit(matrixStack, i + 96, j + 46, 176, 30, scaled, 10);
    }

    public void drawBattery(MatrixStack matrixStack, float percent) {
        percent = Math.min(100F, percent);
        int scaled = (int) (72F * percent);
        int i = this.leftPos;
        int j = this.topPos;
        blit(matrixStack, i + 96, j + 20, 176, 20, scaled, 10);
    }

}
