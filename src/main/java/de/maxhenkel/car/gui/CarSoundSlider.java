package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CarSoundSlider extends AbstractSlider {

    public CarSoundSlider(int x, int y, int width) {
        super(x, y, width, 20, new StringTextComponent(""), Main.CLIENT_CONFIG.carVolume.get());
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        TextComponent amount = value <= 0D ? new TranslationTextComponent("options.off") : new StringTextComponent((int) ((float) value * 100F) + "%");
        setMessage(new TranslationTextComponent("soundCategory.car").append(": ").append(amount));
    }

    @Override
    protected void applyValue() {
        Main.CLIENT_CONFIG.carVolume.set(value);
        Main.CLIENT_CONFIG.carVolume.save();
    }

}