package de.maxhenkel.car.gui;

import de.maxhenkel.car.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CarSoundSlider extends AbstractSlider {

    public CarSoundSlider(Minecraft minecraft, int x, int y, int width) {
        super(minecraft.gameSettings, x, y, width, 20, Config.carVolume.get());
        this.updateMessage();
    }

    protected void updateMessage() {
        String amount = (float) this.value == (float) this.getYImage(false) ? I18n.format("options.off") : (int) ((float) this.value * 100.0F) + "%";
        setMessage(I18n.format("soundCategory.car") + ": " + amount);
    }

    protected void applyValue() {
        Config.carVolume.set(value);
        Config.carVolume.save();
    }
}