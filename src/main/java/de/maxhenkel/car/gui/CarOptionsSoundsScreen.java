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

    protected void init() {
        super.init();
        int posX = 2;
        SoundCategory[] categories = SoundCategory.values();
        for (SoundCategory category : categories) {
            if (category != SoundCategory.MASTER) {
                ++posX;
            }
        }

        addButton(new CarSoundSlider(width / 2 - 155 + posX % 2 * 160, height / 6 - 12 + 24 * (posX >> 1), 150));
    }

}
