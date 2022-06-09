package de.maxhenkel.car.gui;

import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CarOptionsSoundsScreen extends SoundOptionsScreen {

    public CarOptionsSoundsScreen(Screen screen, Options gameSettings) {
        super(screen, gameSettings);
    }

    protected void init() {
        super.init();
        int posX = 2;
        SoundSource[] categories = SoundSource.values();
        for (SoundSource category : categories) {
            if (category != SoundSource.MASTER) {
                ++posX;
            }
        }

        addRenderableWidget(new CarSoundSlider(width / 2 - 155 + posX % 2 * 160, height / 6 - 12 + 22 * (posX >> 1), 150));
    }

}
