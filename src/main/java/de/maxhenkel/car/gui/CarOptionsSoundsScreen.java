package de.maxhenkel.car.gui;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.screen.OptionsSoundsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CarOptionsSoundsScreen extends OptionsSoundsScreen {

    public CarOptionsSoundsScreen(Screen screen, GameSettings gameSettings) {
        super(screen, gameSettings);
    }

    protected void func_231160_c_() {
        super.func_231160_c_();
        int posX = 2;
        SoundCategory[] categories = SoundCategory.values();
        for (SoundCategory category : categories) {
            if (category != SoundCategory.MASTER) {
                ++posX;
            }
        }

        func_230480_a_(new CarSoundSlider(field_230708_k_ / 2 - 155 + posX % 2 * 160, field_230709_l_ / 6 - 12 + 24 * (posX >> 1), 150));
    }

}
