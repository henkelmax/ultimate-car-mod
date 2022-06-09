package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CarSoundSlider extends AbstractSliderButton {

    public CarSoundSlider(int x, int y, int width) {
        super(x, y, width, 20, Component.empty(), Main.CLIENT_CONFIG.carVolume.get());
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        Component amount = value <= 0D ? Component.translatable("options.off") : Component.literal((int) ((float) value * 100F) + "%");
        setMessage(Component.translatable("soundCategory.car").append(": ").append(amount));
    }

    @Override
    protected void applyValue() {
        Main.CLIENT_CONFIG.carVolume.set(value);
        Main.CLIENT_CONFIG.carVolume.save();
    }

}