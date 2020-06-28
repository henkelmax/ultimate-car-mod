package de.maxhenkel.car.gui;

import de.maxhenkel.car.Config;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CarSoundSlider extends AbstractSlider {

    public CarSoundSlider(int x, int y, int width) {
        super(x, y, width, 20, new StringTextComponent(""), Config.carVolume.get());
        func_230979_b_();
    }

    @Override
    protected void func_230979_b_() {
        TextComponent amount = field_230683_b_ <= 0D ? new TranslationTextComponent("options.off") : new StringTextComponent((int) ((float) field_230683_b_ * 100F) + "%");
        func_238482_a_(new TranslationTextComponent("soundCategory.car").func_240702_b_(": ").func_230529_a_(amount));
    }

    @Override
    protected void func_230972_a_() {
        Config.carVolume.set(field_230683_b_);
        Config.carVolume.save();
    }
}