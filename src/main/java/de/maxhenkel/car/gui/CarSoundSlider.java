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
        func_230979_b_();
    }

    @Override
    protected void func_230979_b_() {
        TextComponent amount = sliderValue <= 0D ? new TranslationTextComponent("options.off") : new StringTextComponent((int) ((float) sliderValue * 100F) + "%");
        setMessage(new TranslationTextComponent("soundCategory.car").appendString(": ").append(amount));
    }

    @Override
    protected void func_230972_a_() {
        Main.CLIENT_CONFIG.carVolume.set(sliderValue);
        Main.CLIENT_CONFIG.carVolume.save();
    }

}